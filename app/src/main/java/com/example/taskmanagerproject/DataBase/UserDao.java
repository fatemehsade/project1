package com.example.taskmanagerproject.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerproject.Model.User;

import java.util.List;
import java.util.UUID;
@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM userTable WHERE userId = :userId")
    User getUserWithId(UUID userId);

    @Query("SELECT * FROM userTable")
    List<User> getUsers();
}
