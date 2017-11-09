package com.example.ntvid.timemanager.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ntvid on 07/11/2017.
 */

public class Task implements Serializable{
    private Long id;
    private String name;
    private String description;
    private String location;
    private String deadline;

    public Task() {
    }

    public Task(Long id, String name, String description, String location, String deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return name;
    }
}
