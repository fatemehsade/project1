package com.example.taskmanagerproject.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.taskmanagerproject.Converters;
import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.Model.User;

@Database(entities = {Task.class, User.class},version = 1)
@TypeConverters({Converters.class})
public abstract class TaskDataBase extends RoomDatabase {

    public abstract TaskDao getTaskDataBase();
    public abstract UserDao getUserDataBase();
}
