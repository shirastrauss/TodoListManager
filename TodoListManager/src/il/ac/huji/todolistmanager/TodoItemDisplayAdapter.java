package il.ac.huji.todolistmanager;

import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/* This class implements a TodoItem Display Adaptor */
public class TodoItemDisplayAdapter extends ArrayAdapter<TodoItem> {

	// constructor
	public TodoItemDisplayAdapter(
			TodoListManagerActivity activity, List<TodoItem> todoItems) {
		super(activity, android.R.layout.simple_list_item_1, todoItems);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TodoItem todoItem = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.row, null);
		TextView txtTitle = (TextView)view.findViewById(R.id.txtTitle);
		
		// sets the text to contain the item's title
		txtTitle.setText(todoItem.get_title());
		
		// sets alternating colors for the items in the ListView:
		// color of items in odd positions = blue 
		// color of items in even positions = red
		if(position%2 == 1){
			txtTitle.setTextColor(Color.BLUE);
		}
		else{
			txtTitle.setTextColor(Color.RED);
		}
		return view;
	}
}
