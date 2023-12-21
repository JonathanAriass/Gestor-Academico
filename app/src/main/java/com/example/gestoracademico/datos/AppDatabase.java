package com.example.gestoracademico.datos;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gestoracademico.modelo.Document;
import com.example.gestoracademico.modelo.Task;

@Database(version=1, entities = {Task.class, Document.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDAO getTaskDAO();

    public static final String DB_NOMBRE = "tasks.db";

    private static AppDatabase db;

    public static AppDatabase getDatabase(Context applicationContext) {
        if (db == null) {
            /*
                allowMainThreadQueries() implica que utilizaremos el hilo principal.
                Esto es un crimen (bloqueamos la interfaz y demás problemas).
                Lo trabajaremos la semana que viene con Kotlin.
             */
            db = Room.databaseBuilder(applicationContext, AppDatabase.class, DB_NOMBRE)
                    .allowMainThreadQueries()
                    .build();
        }
        return db;
    }

}
