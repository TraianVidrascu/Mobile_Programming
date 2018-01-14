package com.example.ntvid.taskmanager.api;

import android.util.Log;

import com.example.ntvid.taskmanager.model.Task;
import com.example.ntvid.taskmanager.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

/**
 * Created by ntvid on 07/01/2018.
 */

public class FirebaseService extends Observable {
    private FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    private static List<Observer> observers;
    private List<Task> tasks;
    private static FirebaseService instance = null;
    private boolean isNotUpdated = true;

    public static List<Observer> getObservers() {
        return observers;
    }

    private FirebaseService() {
        tasks = new ArrayList<>();
        firebase.setPersistenceEnabled(true);
        observers =  new ArrayList<>();
        DatabaseReference database = getDatabase("tasks");
        database.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                {
                    Task task = dataSnapshot.getValue(Task.class);
                    tasks.add(task);
                    for (Observer observer : observers) {
                        observer.update(FirebaseService.this, tasks);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                Task old = null;
                for (Task task1 : tasks) {
                    if (task1.getId().equals(task.getId())) {
                        old = task1;
                        break;
                    }
                }
                if (old != null) {
                    old.setDeadline(task.getDeadline());
                    old.setLocation(task.getLocation());
                    old.setDescription(task.getDescription());
                    old.setName(task.getName());
                    //observers.notify();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                for(int i=0;i<tasks.size();i++){
                    if(tasks.get(i).getId().equals(task.getId())){
                        tasks.remove(i);
                    }
                }
                observers.notify();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        init();
    }

    public static FirebaseService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new FirebaseService();
        return instance;
    }

    private void init(){
        DatabaseReference db = getDatabase("tasks");
        String key = db.push().getKey();
        db.child(key).setValue(null);
        isNotUpdated = false;
    }

    private DatabaseReference getDatabase() {
        return firebase.getReference();
    }

    private DatabaseReference getDatabase(String ref) {
        return firebase.getReference(ref);
    }

    public Task addTask(Task task) {
        DatabaseReference database = getDatabase("tasks");
        String id = database.push().getKey();
        task.setId(id);
        database.child(id).setValue(task);
        return task;
    }

    public Task updateTask(Task task) {
        DatabaseReference database = getDatabase("tasks");
        database.child(task.getId()).setValue(task);
        return task;
    }

    public void deleteTask(String id) {
        DatabaseReference database = getDatabase("tasks");
        database.child(id).setValue(null);
    }

    public List<Task> getAll() {
        while(isNotUpdated){

        }
        return tasks;
    }

    public Task findById(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public void addUser(String key,String email,String role){
        DatabaseReference db = getDatabase("users");
        User user = new User();
        user.setEmail(email);
        user.setRole(role);
        db.child(key).setValue(user);
    }


}
