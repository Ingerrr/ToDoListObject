package com.example.inger.todolistobject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


public class TodoManager extends TodoList implements Serializable {
    ArrayList<TodoList> todoManager;
    ArrayList<String> titles;
    String textFileName;
    TodoList todoList;

    // Fields
    private static TodoManager ourInstance = new TodoManager();

    // Constructor
    public TodoManager() {
        todoManager = new ArrayList<>();
    }

    // Methods
    public static TodoManager getInstance() {
        return ourInstance;
    }

    public ArrayList<String> readLists(){
        titles = new ArrayList<>();
        for (TodoList todoList: todoManager){
            String title = TodoList.getTitle();
            titles.add(title);
        }

        return titles;
    }

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

        return list;

    }

    public void addList(TodoList list){
        todoManager.add(list);
    }

    public void deleteList(TodoList list){
        todoManager.remove(list);
    }

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



        //PrintStream out = new PrintStream(openFileOutput(textFileName));


    }
}
