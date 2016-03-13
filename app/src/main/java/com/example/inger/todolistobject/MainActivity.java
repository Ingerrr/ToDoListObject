package com.example.inger.todolistobject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Create variables
    ArrayList<String> listNames;
    TodoManager toDoManager;
    ListView lists;
    ArrayAdapter<String> listAdapter;
    TodoList clickedList;

    /*
    * Creates empty list if app is opened for the first time or reads existing list from file
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        toDoManager = (TodoManager) extras.getSerializable("todoManager");

        // Create link to ListView on screen
        lists = (ListView)findViewById(R.id.list);

        // Create empty list of to do lists
        toDoManager = toDoManager.getInstance();
        listNames = toDoManager.readLists();
        //listNames = new ArrayList<String>;

        // Set Adapter to ListView
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNames);
        lists.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        // Setup listener for clicks on ListView
        setupListener();

        // Check what was the last activity before app was killed
        /*SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        int activityNumber = prefs.getInt("activity", 0);
        if (activityNumber == 1){
            // If last activity was not the MainActivity, go to the SingleListActivity with last opened list
            TodoList lastList = prefs.getSeriazible("currentList", );

            nextActivity(lastList);
        }*/
    }

    /*
    * Setup a listener for clicks on ListView
     */
    public void setupListener() {
        lists.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    /*
                    * Goes to the list that is long clicked
                     */
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        // Get title from list clicked on as a string
                        clickedList = (TodoList) lists.getItemAtPosition(position);

                        // Go to SingleListActivity with that specific list
                        nextActivity(clickedList);
                    }
                });
    }

    /*
    * Opens new activity in which the user can make a new list and save it
     */
    public void addList(View view) {

        // Create empty toDos
        clickedList = new TodoList("");
        nextActivity(clickedList);
    }

    /*
    * Opens a new activity with the list that is clicked on or an empty one
     */
    public void nextActivity(TodoList currentList){
        Intent intent = new Intent(this, SingleListActivity.class);

        // attaches the clicked list name so that the belonging list can be opened from SingleListActivity
        ///intent.putExtra("listNames", listNames);
        intent.putExtra("currentList", currentList);
        intent.putExtra("toDoManager", toDoManager);

        // Start MainActivity
        startActivity(intent);
        finish();
    }
}