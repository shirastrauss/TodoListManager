package il.ac.huji.todolist;

import java.util.Date;

/*
 * An interface for the todo items
 */
public interface ITodoItem {
	
	// returns the item's title
	String getTitle();
	
	// returns the item's due date
	Date getDueDate();
}
