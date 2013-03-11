package il.ac.huji.todolistmanager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {

	private TodoItemDisplayAdapter adapter;
	
	EditText edtNewItem;
	ListView lstTodoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        
        edtNewItem = (EditText) findViewById(R.id.edtNewItem);
        System.out.println(R.id.lstTodoItems);
        lstTodoItems = (ListView) findViewById(R.id.lstTodoItems);
        
        List<TodoItem> todoItems = new ArrayList<TodoItem>();
        
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
    	  switch (item.getItemId()){
    	  case R.id.menuItemAdd:
    		  adapter.add(new TodoItem(edtNewItem.getText().toString()));
    		  break;
    	  case R.id.menuItemDelete:
    		  adapter.remove((TodoItem) lstTodoItems.getSelectedItem());
    		  break;
    	  }
		return true;
    }

    
}
