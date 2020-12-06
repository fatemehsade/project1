package com.example.taskmanagerproject.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerproject.Model.Task;

import java.util.List;
import java.util.UUID;
@Dao
public interface TaskDao {

    @Query("SELECT * FROM taskTable WHERE taskIs = :taskId")
    Task getTask(UUID taskId);

    @Query("SELECT * FROM taskTable WHERE userId = :userId AND state = :taskState")
    List<Task> getTasks(UUID userId, String taskState);

    @Query("SELECT * FROM taskTable WHERE userId = :userId")
    List<Task> getTasks(UUID userId);

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);
    @Query("DELETE FROM TASKTABLE WHERE userId = :userId ")
    void deleteTask(UUID userId);
    @Update
    void updateTask(Task task);
}
