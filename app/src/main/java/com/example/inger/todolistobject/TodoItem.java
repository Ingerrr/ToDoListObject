package com.example.inger.todolistobject;

import android.util.Log;

/**
 * Created by Inger on 7-3-2016.
 */
public class TodoItem {

    // Fields
    private static String item;
    private Boolean isCompleted;

    // Constructor
    public TodoItem(String itemArg){
        item = itemArg;
        Log.d("item in todoitem",item);
        isCompleted = false;
    }
    // Methods
    public Boolean getIsCompleted(){
        return isCompleted;
    }

    public static String getItemName(){
        return item;
    }

    public void Complete(){
        isCompleted = true;
    }
}
