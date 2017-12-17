package com.example.ntvid.timemanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ntvid.timemanager.dao.TaskDao;
import com.example.ntvid.timemanager.model.Task;

/**
 * Created by ntvid on 17/12/2017.
 */

@Database(entities = {Task.class},version = 1)
public abstract class AppDatabase  extends RoomDatabase{
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
