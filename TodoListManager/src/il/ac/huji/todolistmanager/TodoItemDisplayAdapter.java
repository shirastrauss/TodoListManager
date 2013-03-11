package il.ac.huji.todolistmanager;

import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TodoItemDisplayAdapter extends ArrayAdapter<TodoItem> {

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
		txtTitle.setText(todoItem.get_title());
		if(position%2 == 1){
			txtTitle.setTextColor(Color.BLUE);
		}
		else{
			txtTitle.setTextColor(Color.RED);
		}
		return view;
	}
}
