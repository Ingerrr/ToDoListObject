package com.example.inger.todolistobject;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


public class TodoManager extends TodoList implements Serializable {

    // Fields
    private static TodoManager ourInstance = new TodoManager();
    ArrayList<TodoList> todoManager;
    ArrayList<String> titles;
    String textFileName;
    TodoList todoList;

    // Constructor
    /*
    * Construct to do manager that lists todoLists
     */
    public TodoManager() {
        // Create empty list of todolists
        todoManager = new ArrayList<TodoList>();
    }

    // Methods

    /*
    * Return instance of todomanager
     */
    public static TodoManager getInstance() {
        return ourInstance;
    }

    /*
    * Read titles of lists in the manager
     */
    public ArrayList<String> readLists(){
        // Create empty array to store titles in
        titles = new ArrayList<>();

        // iterate over lists and achieve title of list
        for (TodoList todoList: todoManager){
            String title = TodoList.getTitle();
            titles.add(title);
        }

        // Return titles
        return titles;
    }

    /*
    * Read todos from given file
     */
    public TodoList readTodos(String title){

        // convert title into text file name
        textFileName = title.replace(" ", "") + ".txt";

        // Make new TodoList to store items in
        TodoList list = new TodoList(title);

        // Attempts to input list from file if it exists
        try {
            // Read existing to do lists from file
            FileInputStream file = new FileInputStream(textFileName);
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                list.addItem(line);
                ///titles.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Return list
        return list;

    }

    /*
    * Add new todolist to manager
     */
    public void addList(TodoList list){
        todoManager.add(list);
    }

    /*
    * Remove list with given title from manager
     */
    public void deleteList(TodoList list){
        todoManager.remove(list);
    }

    /*
    * Write specific todolist to a file
     */
    public void writeTodos(TodoList toDos, String title, Context context) throws FileNotFoundException {

        // convert title into text file name
        textFileName = title.replace(" ", "") + ".txt";

        // Create link to file
        try {
            FileInputStream file = context.openFileInput(textFileName);
            PrintStream out = new PrintStream(String.valueOf(file));

            ArrayList<String> toDosText = toDos.getTodoList();

            // Write toDos to file one by one
            for (int i = 0; i < toDosText.size(); i++) {
                out.println(toDosText.get(i));
            }

            // Close file
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
