package com.example.ntvid.taskmanager.api;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.ntvid.taskmanager.R;
import com.example.ntvid.taskmanager.model.Notification;
import com.example.ntvid.taskmanager.model.Task;
import com.example.ntvid.taskmanager.model.User;
import com.example.ntvid.taskmanager.model.Watch;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static List<Observer> observers;
    private List<Watch> wathces;
    private List<Task> tasks;
    private List<User> admins;
    private List<Notification> userNotifications;
    private static FirebaseService instance = null;
    private boolean isNotUpdated = true;
    private static NotificationManager notificationManager;
    private static Context context;
    private static int ids = 0;

    public static List<Observer> getObservers() {
        return observers;
    }

    private FirebaseService() {
        admins = new ArrayList<>();
        tasks = new ArrayList<>();
        wathces = new ArrayList<>();
        userNotifications = new ArrayList<>();
        firebase.setPersistenceEnabled(true);
        observers = new ArrayList<>();
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
                    addNotifactions(old.getId(), "Task " + old.getName() + " was modified!");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).getId().equals(task.getId())) {
                        tasks.remove(i);
                        addNotifactions(tasks.get(i).getId(), "Task " + task.getName() + " was deleted!");
                        removeWathcesByTask(tasks.get(i).getId());

                    }
                }
                //observers.notify();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference watchesData = getDatabase("watches");
        watchesData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Watch watch = dataSnapshot.getValue(Watch.class);
                wathces.add(watch);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Watch watch = dataSnapshot.getValue(Watch.class);
                for (int i = 0; i < wathces.size(); i++) {
                    if (wathces.get(i).getId().equals(watch.getId())) {
                        wathces.remove(i);

                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference notificationsData = getDatabase("notifications");
        notificationsData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Notification notification = dataSnapshot.getValue(Notification.class);
                if (notification.getUid().equals(auth.getCurrentUser().getUid())) {
                    //userNotifications.add(notification);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setContentTitle("Task notification")
                                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                                    .setContentText(notification.getMessage());

                    notificationManager.notify(ids++,mBuilder.build());
                    instance.deleteNotification(notification.getId());                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Notification notification = dataSnapshot.getValue(Notification.class);
                for (int i = 0; i < userNotifications.size(); i++) {
                    if (userNotifications.get(i).getId().equals(notification.getId())) {
                        userNotifications.remove(i);
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference adminsData = getDatabase("admins");
        adminsData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                admins.add(user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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

    private void init() {
        DatabaseReference db = getDatabase("tasks");
        String key = db.push().getKey();
        db.child(key).setValue(null);
        db = getDatabase("watches");
        key = db.push().getKey();
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
        while (isNotUpdated) {

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

    public Watch addWatch(Watch watch) {
        DatabaseReference database = getDatabase("watches");
        String id = database.push().getKey();
        watch.setId(id);
        database.child(id).setValue(watch);
        return watch;
    }

    public void deleteWatch(String id) {
        DatabaseReference database = getDatabase("watches");
        database.child(id).setValue(null);

    }

    public List<Watch> getAllWathces() {
        while (isNotUpdated) {

        }
        return wathces;
    }

    public Watch findByTaskAndUSer(String uid, String tid) {
        for (Watch watch : wathces) {
            if (watch.getUid().equals(uid) && watch.getTaskId().equals(tid)) {
                return watch;
            }
        }
        return null;
    }

    public void addUser(String key, String email, String role) {
        DatabaseReference db = getDatabase("users");
        User user = new User();
        user.setEmail(email);
        db.child(key).setValue(user);
    }

    public void addNotifactions(String tid, String message) {
        for (Watch watch : wathces) {
            if (watch.getTaskId().equals(tid)) {
                Notification notification = new Notification(watch.getUid(), message);
                addNotification(notification);
            }
        }
    }

    public Notification addNotification(Notification notification) {
        DatabaseReference database = getDatabase("notifications");
        String id = database.push().getKey();
        notification.setId(id);
        database.child(id).setValue(notification);
        return notification;
    }

    public void deleteNotification(String id) {
        DatabaseReference database = getDatabase("notifications");
        database.child(id).setValue(null);
    }

    public void removeWathcesByTask(String tid) {
        for (Watch watch : wathces) {
            if (watch.getTaskId().equals(tid)) {
                deleteWatch(watch.getId());
            }
        }
    }
    public void deleteNotificationByUserAndTasK(String uid,String tid){

    }
    public static FirebaseService initNotifications(NotificationManager manager, Context context){
        notificationManager = manager;
        FirebaseService.context = context;
        return FirebaseService.getInstance();
    }

    public boolean isAdmin(String email){
        for(User user: admins){
            if(user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
}
