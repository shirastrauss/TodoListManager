package il.ac.huji.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/* This class implements a TodoItem Display Adaptor */
public class TodoItemDisplayAdapter extends ArrayAdapter<TodoItem> {

	/**
	 * constructor
	 * Parameters:
	 * activity - the current activity
	 * todoItems - The TodoItems to represent in the ListView.
	 */
	public TodoItemDisplayAdapter(
			TodoListManagerActivity activity, List<TodoItem> todoItems) {
		super(activity, android.R.layout.simple_list_item_1, todoItems);
	}

	/**
	 * Gets a View that displays the data at the specified position in the data set.
	 * Parameters:
	 * position - The position of the item within the adapter's data set of the item whose view we want.
	 * convertView - The old view to reuse, if possible.
	 * parent - The parent that this view will eventually be attached to.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		TodoItem todoItem = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.row, null);
		TextView txtTitle = (TextView)view.findViewById(R.id.txtTodoTitle);

		// sets the text in the txtTitle TextView to contain the item's title
		txtTitle.setText(todoItem.get_title());

		TextView txtDueDate = (TextView)view.findViewById(R.id.txtTodoDueDate);

		if(todoItem.get_dueDate()==null){
			txtDueDate.setText("No due date");
		}
		else{
			// formats the due date of the item
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String date = dateFormat.format(todoItem.get_dueDate());

			// sets the text in the txtDueDate TextView to contain the item's due date
			txtDueDate.setText(date);

			// gets the current date
			Date curDate = new Date(System.currentTimeMillis());
			
			// Checks whether the item is overdue (the item's due date < current date)
			// and if it is	sets its font color to RED
			if(todoItem.get_dueDate().before(curDate)){
				txtTitle.setTextColor(Color.RED);
				txtDueDate.setTextColor(Color.RED);
			}
		}
		return view;
	}
}
