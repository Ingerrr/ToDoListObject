package com.example.inger.todolistobject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SingleListActivity extends AppCompatActivity {

    // Create variables
    TodoList toDos;
    ListView listToDo;
    ArrayAdapter listAdapter;
    String textFileName;
    TodoManager toDoManager;
    ArrayList<String> listItems;
    String titleList = "";

    /*
    * Creates empty list if app is opened for the first time or reads existing list from file
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);

        // Get extras from intent
        Bundle extras = getIntent().getExtras();
        toDoManager = (TodoManager) extras.getSerializable("toDoManager");
        toDos = (TodoList) extras.getSerializable("currentList");
        titleList = toDos.getTitle();

        if (titleList.matches("")){
            // Activate creation of new list if file doesn't exist
            RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

            // Make list name invisible
            RelativeLayout listName = (RelativeLayout) mainLayout.findViewById(R.id.listName);
            listName.setVisibility(INVISIBLE);

            // Make EditText field for list name visible
            RelativeLayout setListName = (RelativeLayout) mainLayout.findViewById(R.id.setListName);
            setListName.setVisibility(VISIBLE);
        }
        else {
            // Read existing toDos from file
            toDos = toDoManager.readTodos(titleList);

            // Set title of the list
            ((TextView) findViewById(R.id.title)).setText(titleList);
        }

        // Create link to ListView on screen
        listToDo = (ListView) findViewById(R.id.list);

        // Set Adapter to ListView
        listItems = toDos.readItems();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listToDo.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        // Setup listener for clicks on ListView
        setupListener();
    }

    /*
    * Adds item to toDos after user input
     */
    public void addItem(View view) throws FileNotFoundException {

        // Check if a title for the list has been given
        if (titleList.matches("")){
            // Ask user to give a title first
            Toast.makeText(this, R.string.titleFirst, Toast.LENGTH_LONG).show();
        }
        else {
            // Read input from user
            String input = ((EditText) findViewById(R.id.input)).getText().toString();
            if(input.matches("")){
                return;
            }
            // Add input to toDos
            TodoItem todoItem = toDos.addItem(input);

            // read items from toDos
            listItems.add(todoItem.getItemName());

            // Update ListView
            listAdapter.notifyDataSetChanged();

            // Clear EditText
            ((EditText) findViewById(R.id.input)).setText("");

            // Store/update list in text file
            toDoManager.writeTodos(toDos, titleList,  SingleListActivity.this);

        }
    }

    /*
    * Setup a listener for clicks on ListView
     */
    public void setupListener() {
        listToDo.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    /*
                    * Deletes an item from the list when it is long-clicked
                     */
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        // Removes item from list
                        toDos.deleteItem(position);

                        // Update ListView
                        listAdapter.notifyDataSetChanged();

                        try {
                            toDoManager.writeTodos(toDos, textFileName,  SingleListActivity.this);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        return true;
                    }
                }
        );
        listToDo.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    /*
                    * Change color of listviewItem if it has been clicked, according to status
                     */
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        // Change status of item
                        TodoItem itemToChange = toDos.get(position);
                        itemToChange.Complete();

                        // Change color to gray to indicate completed
                        if (itemToChange.getIsCompleted() == false){
                            listToDo.getChildAt(position).setBackgroundColor(Color.WHITE);
                        }
                        else {
                            listToDo.getChildAt(position).setBackgroundColor(Color.GRAY);
                        }

                        // Update ListView
                        listAdapter.notifyDataSetChanged();

                    }
                }
        );
    }

    /*
    * Set name of list according to user input and store in textfiles.
     */
    public void setListName(View view) throws FileNotFoundException {

        // Get input from EditText
        titleList = ((EditText) findViewById(R.id.inputListName)).getText().toString();

        if(titleList.matches("")){
            return;
        }

        // Deactivate EditText field for title
        findViewById(R.id.setListName).setVisibility(INVISIBLE);

        // Display title
        findViewById(R.id.listName).setVisibility(VISIBLE);
        ((TextView) findViewById(R.id.title)).setText(titleList);

        // create TodoList
        toDos = new TodoList(titleList);
        // Add list to lists of lists
        toDoManager.addList(toDos);

        // Update Listoflists.txt
        toDoManager.writeTodos(toDoManager, "Listoflists",  SingleListActivity.this);

    }

    /*
    * Delete this specific list file and update Listoflists.txt
     */
    public void deleteList(View view) throws FileNotFoundException {

        // Ask for permission to delete the list in a popup window
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage(R.string.sureToDelete);
        dlgAlert.setTitle(R.string.delete);

        // Include an OK button in the popup window
        dlgAlert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

            /*
            * If OK is pressed, delete the list
             */
            @Override
            public void onClick(DialogInterface dialog, int id) {

                // Update listNames
                toDoManager.deleteList(toDos);
                try {
                    toDoManager.writeTodos(toDos, titleList,  SingleListActivity.this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // Update Listoflists.txt
                try {
                    toDoManager.writeTodos(toDoManager, "Listoflists",  SingleListActivity.this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // Delete file of current list
                deleteFile(titleList+".txt");

                // go back  to main activity
                backToMain();
            }
        });

        // Enable the user to close the popup window by clicking next to it
        dlgAlert.setCancelable(true);

        // Create popup window
        dlgAlert.create().show();
    }

    /*
    * Check if a title for the list is given, if so return to MainActivity
     */
    public void backToMain(View view) {

        // Exit SingleListActivity
        backToMain();
    }

    /*
    * Go back to the main menu
     */
    public void backToMain() {

        // Set activity number back to 0 to avoid getting stuck in SingleListActivity
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("activity", 0);
        editor.commit();

        // Start MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("todoManager", toDoManager);
        startActivity(intent);
        finish();
    }

    /*
    * Save current state for in case the app is killed
     */
    public void onSaveInstanceState(Bundle outState) {

        // Read text from EditText for listname
        String editTextTitle = ((EditText) findViewById(R.id.inputListName)).getText().toString();

        // Read text from EditText for items
        String editTextItem = ((EditText) findViewById(R.id.input)).getText().toString();

        // Save EditTexts
        super.onSaveInstanceState(outState);
        outState.putString("editTextTitle", editTextTitle);
        outState.putString("editTextItem", editTextItem);
    }

    public void onRestoreInstanceState(Bundle inState) {

        // Retrieve EditTexts
        super.onRestoreInstanceState(inState);
        String editTextTitle = inState.getString("editTextTitle");
        String editTextItem = inState.getString("editTextItem");

        // Restore EditTexts
        ((EditText) findViewById(R.id.inputListName)).setText(editTextTitle);
        ((EditText) findViewById(R.id.input)).setText(editTextItem);
    }

}
