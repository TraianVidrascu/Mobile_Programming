package com.example.ntvid.taskmanager.model;

/**
 * Created by ntvid on 14/01/2018.
 */

public class Watch {
    private String id;
    private String uid;
    private String taskId;

    public Watch() {
    }

    public Watch(String uid, String taskId) {
        this.uid = uid;
        this.taskId = taskId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
