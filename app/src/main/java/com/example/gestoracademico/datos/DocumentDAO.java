package com.example.gestoracademico.datos;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.example.gestoracademico.modelo.Document;
import com.example.gestoracademico.modelo.Task;

import java.util.List;

@Dao
public interface DocumentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Document document);

    @Delete
    void delete(Document document);

    @Query("SELECT * FROM document")
    List<Document> getAll();

}
