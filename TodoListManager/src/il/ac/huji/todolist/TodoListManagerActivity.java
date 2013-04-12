package il.ac.huji.todolist;

import java.util.Date;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;

/**
 * The main activity of the TodoListManager application
 */
public class TodoListManagerActivity extends Activity {

	// identifier for the call to the AddNewTodoItemActivity
	final static private int ADD_ITEM = 101;

	// an adapter for displaying the TodoItem list
	private TodoItemDisplayAdapter adapter;

	EditText edtNewItem; // a text box for inserting new items
	ListView lstTodoItems; // a list view that will contain the todo items

	private TodoDAL todoDal;

	/**
	 * Called when the activity is first created.
	 * Parameters:
	 * savedInstanceState - If the activity is being re-initialized
	 * after previously being shut down then this Bundle contains
	 * the data it most recently supplied in onSaveInstanceState(Bundle).
	 * Otherwise it is null.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);

		edtNewItem = (EditText) findViewById(R.id.edtNewItem);
		lstTodoItems = (ListView) findViewById(R.id.lstTodoItems);

		registerForContextMenu(lstTodoItems); 

		todoDal = new TodoDAL(this);

		// creates an Adapter for displaying the TodoItem list
		String[] from = { "title", "due" };
		int[] to = { R.id.txtTodoTitle, R.id.txtTodoDueDate };
		adapter = new TodoItemDisplayAdapter(this,R.layout.row, todoDal.getCursor(), from, to);

		lstTodoItems.setAdapter(adapter);
	}


	/**
	 * Initializes the contents of the Activity's standard options menu.
	 * Parameters:
	 * menu - The options menu in which you place your items.
	 * Returns:
	 * return true for the menu to be displayed; if return false it will not be shown.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		return true;
	}


	/**
	 * This function is called whenever an item in the options menu is selected.
	 * Parameters:
	 * item - The menu item that was selected.
	 * Returns:
	 * false to allow normal menu processing to proceed, true to consume it here.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {

		// checks which menu item was selected
		switch (item.getItemId()){
		case R.id.menuItemAdd: // Add
			// invokes the AddNewTodoItemActivity
			Intent intent = new Intent(this, AddNewTodoItemActivity.class);
			startActivityForResult(intent, ADD_ITEM);
			break;
		}
		return true;
	}


	/**
	 * Called when the context menu for this view is being built.
	 * parameters:
	 * menu - The context menu that is being built
	 * v - The view for which the context menu is being built
	 * menuInfo - Extra information about the item for which the
	 * context menu should be shown.
	 */
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		getMenuInflater().inflate(R.menu.cntx_menu, menu);

		AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;

		// sets the header of the context menu to contain 
		// the title of the selected todo item
		Cursor c = (Cursor)adapter.getItem(info.position);
		String title = c.getString(TodoDAL.TITLE_COL);
		menu.setHeaderTitle(title);

		// checks whether the current todo item is of type "Call"-

		// if not - removes the second menu item (the dial option
		// which should appear only in context menus of "Call" items)
		if(!title.startsWith("Call ")){
			menu.removeItem(R.id.menuItemCall);
		}
		else{ // if it is a "Call" item - sets the title of the second menu item to the item's title
			menu.findItem(R.id.menuItemCall).setTitle(title);
		}

	}

	/**
	 * This function is called whenever an item in a context menu is selected.
	 * Parameters:
	 * item - the context menu item that was selected.
	 */
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();

		Cursor c = (Cursor)adapter.getItem(info.position);
		String title = c.getString(TodoDAL.TITLE_COL);
		long due = c.getLong(TodoDAL.DUE_COL);

		TodoItem todoItem = new TodoItem(title, new Date(due));

		// checks which of the menu options was selected
		switch (item.getItemId()){
		case R.id.menuItemDelete: // Delete
			todoDal.delete(todoItem);	
			break;
		case R.id.menuItemCall: // Call
			// launches the dial activity with the item's phone number
			String telNum = "tel:" + title.substring(5);
			Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse(telNum));
			startActivity(dial);
			break;
		}

		return true;
	}


	/**
	 * Called when an activity that was launched exits, giving the requestCode which it was started with,
	 * the resultCode it returned, and any additional data from it.
	 * Parameters:
	 * requestCode - The integer request code originally supplied to startActivityForResult(), allowing to identify
	 * who this result came from.
	 * resultCode - The integer result code returned by the child activity through its setResult().
	 * data - An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// checks if OK was returned,
		// if so, creates a new TodoItem from the data returned in the intent
		// and adds the new item to the TodoItems list
		if(resultCode==RESULT_OK){

			String title = data.getExtras().getString("title");
			Date dueDate = (Date)data.getExtras().get("dueDate");

			TodoItem todoItem = new TodoItem(title, dueDate);

			todoDal.insert(todoItem);
		}
	}

}
