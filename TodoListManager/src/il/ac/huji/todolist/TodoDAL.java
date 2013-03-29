package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/*
 * This class performs all the database + Parse operations
 * of the TodoListManager application.
 */
public class TodoDAL {

	private static final int ERROR = -1;
	private static final boolean FAILURE = false; 
	private static final boolean SUCCESS = true; 

	static final int TITLE_COL = 1;
	static final int DUE_COL = 2;

	private SQLiteDatabase _db;
	private Cursor _cursor;

	// constructor
	public TodoDAL(Context context) {

		// db initializations
		TodoListDBHelper helper = new TodoListDBHelper(context);
		_db = helper.getWritableDatabase();

		_cursor = _db.query("todo", new String[] {"_id", "title", "due" },
				null, null, null, null, null);

		// parse initializations
		Resources r = context.getResources();
		String parseApplication = r.getString(R.string.parseApplication);
		String clientKey = r.getString(R.string.clientKey);

		Parse.initialize(context, parseApplication, clientKey);
		ParseUser.enableAutomaticUser();
	}

	// inserts a new todoItem.
	// returns true if succeeds, false otherwise.
	public boolean insert(ITodoItem todoItem) {

		String title = todoItem.getTitle();
		Date dueDate = todoItem.getDueDate();

		// inserts into db
		long val = dbInsert(title, dueDate);
		if(val==ERROR){
			return FAILURE;
		}

		// inserts into parse
		try{
			parseInsert(title, dueDate);	
		}
		catch (Exception e){
			return FAILURE;
		}

		return SUCCESS;
	}

	// inserts a new todoItem to the db
	private long dbInsert(String title, Date dueDate) {

		ContentValues values = new ContentValues();
		values.put("title", title);
		if(dueDate!=null){
			values.put("due", dueDate.getTime());
		}else{
			values.putNull("due");
		}
		long val=_db.insert("todo", null, values);
		_cursor.requery();
		return val;
	}
	
	// inserts a new todoItem to parse
	private void parseInsert(String title, Date dueDate) {
		
		ParseObject todo = new ParseObject("todo");
		todo.put("title", title);
		if(dueDate!=null){
			todo.put("due", dueDate.getTime());
		}else{
			todo.put("due",JSONObject.NULL);
		}
		todo.saveInBackground();
	}


	// updates the due date of the todoItem with the given title
	// to be the given date.
	// returns true if succeeds, false otherwise.
	public boolean update(ITodoItem todoItem) {

		String title = todoItem.getTitle();
		final Date dueDate = todoItem.getDueDate();

		// updates db
		long val = dbUpdate(title, dueDate);
		if(val==ERROR){
			return FAILURE;
		}
		
		// updates parse
		try{
			parseUpdate(title, dueDate);
		}
		catch (Exception e){
			return FAILURE;
		}
		
		return SUCCESS;
	}


	// updates in the db the due date of the todoItem with
	// the given title to be the given date
	private long dbUpdate(String title, Date dueDate) {
		
		ContentValues values = new ContentValues();
		if (dueDate!=null){ 
			values.put("due",dueDate.getTime());
		}else {
			values.putNull("due");
		}
		long val=_db.update("todo", values, "title=?", new String[] {title}); 
		_cursor.requery();
		return val;	
	}

	// updates in parse the due date of the todoItem with
	// the given title to be the given date
	private void parseUpdate(String title, final Date dueDate) {
		
		ParseQuery query = new ParseQuery("todo");
		query.whereEqualTo("title", title);
		query.findInBackground(new FindCallback() {
			@Override
			public void done(List<ParseObject> mathces, ParseException e) {
				if (e==null){
					for (ParseObject parseObject : mathces) {
						if (dueDate!=null){							
							parseObject.put("due",dueDate.getTime());
						}
						else {
							parseObject.put("due",JSONObject.NULL);
						}
						parseObject.saveInBackground();
					}
				}
			}
		});
		
	}

	// deletes the given todoItem.
	// returns true if succeeds, false otherwise.
	public boolean delete(ITodoItem todoItem) {

		String title = todoItem.getTitle();

		// deletes from db
		long val = dbDelete(title);
		if(val==ERROR){
			return FAILURE;
		}
		
		// deletes from parse
		try{
			parseDelete(title);	
		}
		catch (Exception e){
			return FAILURE;
		}
		
		return SUCCESS;
	}

	// deletes the todoItem with the given title from the db
	private long dbDelete(String title) {
		
		long val=_db.delete("todo", "title=?", new String [] {title});
		_cursor.requery();
		return val;
	}

	// deletes the todoItem with the given title from parse
	private void parseDelete(String title) {
		ParseQuery query = new ParseQuery("todo");
		query.whereEqualTo("title", title);
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> matches, ParseException e) {
				if(e==null){
					for (ParseObject parseObject : matches) {
						parseObject.deleteInBackground();	
					}
				}	
			}
		});
		
	}

	// returns all existing todoItems
	public List<ITodoItem> all() {

		List<ITodoItem> allTodoItems = new ArrayList<ITodoItem>();

		if (_cursor.moveToFirst()) {
			do {
				String title = _cursor.getString(TITLE_COL);
				Date dueDate = null;
				if(!_cursor.isNull(DUE_COL)){
					long due = _cursor.getLong(DUE_COL);
					dueDate = new Date(due);
				}
				allTodoItems.add(new TodoItem(title, dueDate));

			} while (_cursor.moveToNext());
		}
		return allTodoItems;
	}

	// returns the cursor
	public Cursor getCursor(){
		return _cursor;
	}
}
