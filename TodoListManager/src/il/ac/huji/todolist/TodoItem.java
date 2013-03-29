package il.ac.huji.todolist;

import java.util.Date;

/* 
 * This class represents a todo item.
 * It implements the ITodoItem interface
 */
public class TodoItem implements ITodoItem{

	private String _title;
	private Date _dueDate;

	// constructor
	public TodoItem(String title, Date dueDate){
		_title=title;
		_dueDate = dueDate;
	}

	// returns the item's title
	public String getTitle() {
		return _title;
	}

	// returns the item's due date
	public Date getDueDate() {
		return _dueDate;
	}

}
