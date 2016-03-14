package com.example.inger.todolistobject;

import android.util.Log;

/**
 * Stores data about individual items from the todolist.
 */
public class TodoItem {

    // Fields
    private static String item;
    private Boolean isCompleted;

    // Constructor
    /*
    * Construct a todoItem with a given name and initial status of uncompleted.
     */
    public TodoItem(String itemArg){
        item = itemArg;

        // Set status initially to uncompleted
        isCompleted = false;
    }


    // Methods

    /*
    * Return the status of the item
     */
    public Boolean getIsCompleted(){
        return isCompleted;
    }

    /*
    * Return the name of the item
     */
    public static String getItemName(){
        return item;
    }

    /*
    * Change status of the todoItem, depending on the status before.
     */
    public void Complete(){
        if (isCompleted == false){
            isCompleted = true;
        }
        else{
            isCompleted = false;
        }

    }
}
