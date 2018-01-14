package com.example.ntvid.taskmanager.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ntvid.taskmanager.dao.TaskDao;
import com.example.ntvid.taskmanager.model.Task;

/**
 * Created by ntvid on 07/01/2018.
 */

@Database(entities = {Task.class},version = 1,exportSchema = false)
public abstract class AppDatabase  extends RoomDatabase {
    private static AppDatabase INSTANCE;


    public abstract TaskDao taskDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "taskdatabase")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
