package com.example.ntvid.taskmanager.model;

/**
 * Created by ntvid on 14/01/2018.
 */

public class Notification {
    private String id;
    private String uid;
    private String message;

    public Notification() {

    }

    public Notification(String uid, String message) {
        this.uid = uid;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
