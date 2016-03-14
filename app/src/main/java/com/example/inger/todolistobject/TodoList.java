package com.example.inger.todolistobject;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Lists several todoItems and stores the name of the list and the names of the items
 */
public class TodoList implements Serializable {

    // Fields
    private static String title;
    private ArrayList<TodoItem> todoList;
    private ArrayList<String> titles;

    // Constructor
    /*
    * Create todolist with a given title
     */
    public TodoList(String titleArg){
        // set title
        title = titleArg;

        // create list of todoItems
        todoList = new ArrayList<TodoItem>();
    }

    /*
    * Create initial todoList if no name has been given yet
     */
    public TodoList() {
        // create list of todoItems
        todoList = new ArrayList<TodoItem>();
    }

    // Methods
    /*
    * Return todolist
     */
    public ArrayList getTodoList(){
        // return todolist
        return todoList;
    }

    /*
    * Return title of the list
     */
    public static String getTitle(){
        // return title
        return title;
    }

    /*
    * Change title of the list according to input
     */
    public void setTitle(String titleArg){
        // set title
        title = titleArg;
    }

    /*
    * Add todoItem to the list and return this item
     */
    public TodoItem addItem(String item){

        // Create new todoItem
        TodoItem todoItem = new TodoItem(item);

        // Add item to the list
        todoList.add(todoItem);

        // return item
        return todoItem;
}
    /*
    * Delete the item on a given position from the list
     */
    public void deleteItem(int position){
        // remove the item from list
        todoList.remove(position);
    }

    /*
    * Read all the titles of the items on the list
     */
    public ArrayList<String> readItems(){

        // create empty list of titles
        titles = new ArrayList<>();

        // iterate over items in todoList and return title
        for (TodoItem todoItem: todoList){
            String title = todoItem.getItemName();
            titles.add(title);
        }

        // return titles
        return titles;
    }

    /*
    * Return item at given position
     */
    public TodoItem get(int position) {
        return todoList.get(position);
    }
}
