package com.example.gestoracademico.datos;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gestoracademico.modelo.File;
import com.example.gestoracademico.modelo.Task;

@Database(version=1, entities = {Task.class, File.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDAO getTaskDAO();
    public abstract FileDAO getFileDAO();

    public static final String DB_NOMBRE = "tasks.db";

    private static AppDatabase db;

    public static AppDatabase getDatabase(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext, AppDatabase.class, DB_NOMBRE)
                    .allowMainThreadQueries()
                    .build();
        }
        return db;
    }

}
