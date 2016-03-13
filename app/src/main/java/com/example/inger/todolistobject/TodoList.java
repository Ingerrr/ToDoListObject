package com.example.inger.todolistobject;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.inger.todolistobject.TodoItem.*;

/**
 * Created by Inger on 7-3-2016.
 */
public class TodoList implements Serializable {

    // Fields
    private static String title;
    private static ArrayList<TodoItem> todoList;
    ArrayList<String> titles;

    // Constructor
    public TodoList(String titleArg){
        title = titleArg;
        todoList = new ArrayList<TodoItem>();
    }

    public TodoList() {
    }

    // Methods
    public ArrayList getTodoList(){
        return todoList;
    }

    public static String getTitle(){
        return title;
    }

    public void setTitle(String titleArg){
        title = titleArg;
    }

    public void addItem(String item){
        Log.d("item in addItem", item);
        TodoItem todoItem = new TodoItem(item);
        todoList.add(todoItem);
        //return todoList;
    }

    public void deleteItem(int position){
        todoList.remove(position);
    }
    
    public ArrayList<String> readItems(){
        Log.d("been here","yes");
        titles = new ArrayList<>();
        for (TodoItem todoItem: todoList){
            String title = todoItem.getItemName();
            titles.add(title);
            Log.d("been in the loop", "yes");
        }
        return titles;
    }

}
