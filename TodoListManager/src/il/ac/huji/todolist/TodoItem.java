package il.ac.huji.todolist;

import java.util.Date;

/* This class represents a todo item */
public class TodoItem {

	private String _title;
	private Date _dueDate;

	// constructor
	public TodoItem(String title, Date dueDate){
		_title=title;
		_dueDate = dueDate;
	}

	// returns the item's title
	public String get_title() {
		return _title;
	}

	// returns the item's due date
	public Date get_dueDate() {
		return _dueDate;
	}

}
