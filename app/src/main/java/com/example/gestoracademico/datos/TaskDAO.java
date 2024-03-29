package com.example.gestoracademico.datos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gestoracademico.modelo.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    @Query("SELECT * FROM tasks ORDER BY prioridad DESC")
    List<Task> getAllOrderByPriority();

    @Query("SELECT * FROM tasks where fecha = :date")
    List<Task> getByDate(String date);

    @Query("SELECT * FROM tasks where fk_pdf != 0")
    List<Task> getTaskWithDocument();

    @Query("SELECT id from tasks ORDER BY id DESC LIMIT 1")
    int getLastId();

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Query("DELETE FROM tasks where fk_pdf = :fileID")
    void deleteByFileID(int fileID);

    @Query("UPDATE tasks SET nota= :note where id = :taskId")
    int updateTaskNote(String note, int taskId);
}
