package il.ac.huji.todolist;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * The activity for adding a new TodoItem to the list
 */
public class AddNewTodoItemActivity extends Activity {

	/**
	 * Called when the activity is first created.
	 * Parameters:
	 * savedInstanceState - If the activity is being re-initialized
	 * after previously being shut down then this Bundle contains
	 * the data it most recently supplied in onSaveInstanceState(Bundle).
	 * Otherwise it is null.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_new_todo_item_activity);
		setTitle(R.string.add_item_activity_title);

		// creates a listener for the 'OK' button
		findViewById(R.id.btnOK).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText edtNewItem = (EditText)findViewById(R.id.edtNewItem);
				DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
				
				// gets the data about the new item the user entered
				String title = edtNewItem.getText().toString();
				Date dueDate = 
						new Date(datePicker.getYear()-1900,datePicker.getMonth(), datePicker.getDayOfMonth());
						
				// checks if the title is not empty		
				if (title == null || title.equals("")) {
					setResult(RESULT_CANCELED);
					finish();
				} else { //if the title is ok returns the retrieved data to the calling function
					Intent resultIntent = new Intent();
					resultIntent.putExtra("title", title);
					resultIntent.putExtra("dueDate", dueDate);
					setResult(RESULT_OK, resultIntent);
					finish();
				}
			}
		});
		
		// creates a listener for the 'Cancel' button
		findViewById(R.id.btnCancel).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// returns 'RESULT_CANCELED' to the calling function
				setResult(RESULT_CANCELED);
				finish();	
			}
		});
	}

}
