package com.example.taskmanagerproject.Repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanagerproject.DataBase.TaskDao;
import com.example.taskmanagerproject.DataBase.TaskDataBase;
import com.example.taskmanagerproject.Model.Task;

import java.util.List;
import java.util.UUID;

public class TaskDBRepository {
    public static TaskDBRepository sInstance;
    //private SQLiteDatabase mDatabase;
    private Context mContext;
    private TaskDao mDatabase;

    public static TaskDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new TaskDBRepository(context);
        return sInstance;
    }

    public static void setInstance(TaskDBRepository instance) {
        sInstance = instance;
    }

    private TaskDBRepository(Context context) {
        mContext = context.getApplicationContext();
        //TaskDBHelper taskDBHelper = new TaskDBHelper(mContext);
        //mDatabase = taskDBHelper.getWritableDatabase();

        TaskDataBase taskDataBase = Room.databaseBuilder(
                mContext,
                TaskDataBase.class,
                "task.db")
                .allowMainThreadQueries().build();

        mDatabase = taskDataBase.getTaskDataBase();
    }

    public Task getTask(UUID taskId) {
        return mDatabase.getTask(taskId);

    }

    public List<Task> getTasks(UUID userId, String taskState) {
        return mDatabase.getTasks(userId, taskState);

    }

    public List<Task> getTasks(UUID userId) {
        return mDatabase.getTasks(userId);

    }


    public void insertTask(Task task) {
        mDatabase.insertTask(task);

    }


    public void deleteTask(Task task) {
        mDatabase.deleteTask(task);
    }

    public void deleteTask(UUID userId) {
        mDatabase.deleteTask(userId);
    }

    public void updateTask(Task task) {
        mDatabase.updateTask(task);

    }


    public int getPositionTask(UUID taskId, String taskState, UUID userId) {

        List<Task> tasks = getTasks(userId, taskState);
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId().toString().equals(taskId.toString()))
                return i;
        }
        return -1;

    }

    public int numberOfTaskEveryUser(UUID userId) {
        List<Task> tasks = getTasks(userId);
        return tasks.size();
    }


}



