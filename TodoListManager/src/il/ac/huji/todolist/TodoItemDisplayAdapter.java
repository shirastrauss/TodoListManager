package il.ac.huji.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/* This class implements a TodoItem Display Adaptor */
public class TodoItemDisplayAdapter extends SimpleCursorAdapter {

	/**
	 * constructor
	 * Parameters:
	 * activity - the current activity
	 * todoItems - The TodoItems to represent in the ListView.
	 */
	public TodoItemDisplayAdapter(
			TodoListManagerActivity activity,int layout,Cursor cursor,String[] from, int[] to) {
		super(activity, layout, cursor, from, to);
	}

	/**
	 * Gets a View that displays the data at the specified position in the data set.
	 * Parameters:
	 * position - The position of the item within the adapter's data set of the item whose view we want.
	 * convertView - The old view to reuse, if possible.
	 * parent - The parent that this view will eventually be attached to.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = super.getView(position,convertView,parent);

		Cursor cursor = (Cursor) getItem(position); 

		// sets the text in the Title TextView to contain the item's title
		TextView txtTitle = (TextView)view.findViewById(R.id.txtTodoTitle);
		txtTitle.setText(cursor.getString(TodoDAL.TITLE_COL));

		// sets the text and font color of the DueDate TextView
		TextView txtDueDate = (TextView)view.findViewById(R.id.txtTodoDueDate);

		if(cursor.isNull(TodoDAL.DUE_COL)){
			txtDueDate.setText("No due date");
		}
		else{
			long due = cursor.getLong(TodoDAL.DUE_COL);
			Date dueDate = new Date(due);
			// formats the due date of the item
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String dueDateString = dateFormat.format(dueDate);

			// sets the text in the txtDueDate TextView to contain the item's due date
			txtDueDate.setText(dueDateString);

			// gets the current date
			Date curDate = new Date(System.currentTimeMillis());

			// Checks whether the item is overdue (the item's due date < current date)
			// if it is	- sets its font color to RED
			// if not - sets its font color to BLACK
			if(dueDate.before(curDate)){
				txtTitle.setTextColor(Color.RED);
				txtDueDate.setTextColor(Color.RED);
			}
			else{
				txtTitle.setTextColor(Color.BLACK);
				txtDueDate.setTextColor(Color.BLACK);
			}
		}
		return view;
	}
}
