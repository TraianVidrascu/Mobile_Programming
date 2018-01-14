package com.example.ntvid.timemanager.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ntvid on 07/11/2017.
 */

@Entity
public class Task implements Parcelable{
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String description;
    private String location;
    private String deadline;

    @Ignore
    public Task() {
    }

    public Task(String id, String name, String description, String location, String deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.deadline = deadline;
    }
    @Ignore
    protected Task(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        deadline = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeString(deadline);
    }
}
