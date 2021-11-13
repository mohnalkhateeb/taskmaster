package com.example.taskmaster;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TaskDao TaskDao();

    private static AppDataBase appDatabase; // declaration for the instance

    public AppDataBase() { // constructor implementation
    }

    public static synchronized AppDataBase getInstance(Context context) { // method returns the instance

        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context,
                    AppDataBase.class, "AppDataBase").allowMainThreadQueries().build();
        }

        return appDatabase;
    }

}
