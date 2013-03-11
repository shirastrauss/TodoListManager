package il.ac.huji.todolistmanager;

/* This class represents a todo item */
public class TodoItem {
	
	private String _title;
	
	// constructor
	public TodoItem(String title){
		_title=title;
	}

	// returns the item's title
	public String get_title() {
		return _title;
	}

}
