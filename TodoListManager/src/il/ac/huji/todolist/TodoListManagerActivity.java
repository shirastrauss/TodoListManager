package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

/* The main activity of the TodoListManager aplication */
public class TodoListManagerActivity extends Activity {

	// an adapter for displaying the TodoItem list
	private TodoItemDisplayAdapter adapter; 
	
	EditText edtNewItem; // a text box for inserting new items
	ListView lstTodoItems; // a list view that will contain the todo items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        
        edtNewItem = (EditText) findViewById(R.id.edtNewItem);
        lstTodoItems = (ListView) findViewById(R.id.lstTodoItems);
        
		// creates an ArrayList for storing the TodoItems
        List<TodoItem> todoItems = new ArrayList<TodoItem>();
        
		// creates an Adapter for displaying the TodoItem list
        adapter = new  TodoItemDisplayAdapter(this, todoItems);
        lstTodoItems.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo_list_manager, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	  
		  // checks which menu item was selected (Add or Delete)
		  switch (item.getItemId()){
    	  case R.id.menuItemAdd:
			  // adds the new requested item to the list
    		  adapter.add(new TodoItem(edtNewItem.getText().toString()));
    		  break;
    	  case R.id.menuItemDelete:
			  // removes the selected item from the list
    		  adapter.remove((TodoItem) lstTodoItems.getSelectedItem());
    		  break;
    	  }
		return true;
    }

    
}
