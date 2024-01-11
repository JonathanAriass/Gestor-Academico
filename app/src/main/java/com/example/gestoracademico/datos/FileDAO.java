package com.example.gestoracademico.datos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gestoracademico.modelo.File;
import com.example.gestoracademico.modelo.Task;

import java.util.List;

@Dao
public interface FileDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(File file);

    @Delete
    void delete(File file);

    @Query("SELECT * FROM document")
    List<File> getAll();

    @Query("SELECT * FROM document where id = :fileID")
    File getById(String fileID);

    @Query("SELECT * FROM document where ruta = :filepath")
    File getByPath(String filepath);

    @Query("SELECT id from document ORDER BY id DESC LIMIT 1")
    int getLastId();

    @Query("DELETE FROM document where id = :fileID")
    void deleteByID(int fileID);

    @Query("Delete FROM document")
    void deleteAll();
}
