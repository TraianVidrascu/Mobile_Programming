package com.example.ntvid.timemanager.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ntvid.timemanager.model.Task;

import java.util.List;

/**
 * Created by ntvid on 17/12/2017.
 */
@Dao
public interface TaskDao {

    @Query("Select * from Task")
    List<Task> getAll();

    @Query("Select * from Task where id = :taskId ")
    Task findById(String taskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTask(Task task);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Task task);

    @Delete
    void delete(Task byId);
}
