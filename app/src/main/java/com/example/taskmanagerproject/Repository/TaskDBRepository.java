package com.example.taskmanagerproject.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.taskmanagerproject.DataBase.TaskDBHelper;
import com.example.taskmanagerproject.DataBase.TaskDBSchema;
import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.DataBase.TaskDBSchema.Task.TaskColumns;
import com.example.taskmanagerproject.Utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository {
    public static TaskDBRepository sInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;

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
        TaskDBHelper taskDBHelper = new TaskDBHelper(mContext);
        mDatabase = taskDBHelper.getWritableDatabase();
    }

    public Task getTask(UUID taskId) {
        String whereClause = TaskColumns.TASKID + " = ?";
        String[] whereArgs = new String[]{taskId.toString()};
        TaskCursorWrapper taskCursorWrapper = queryTaskCursorWrapper(whereClause, whereArgs);
        if (taskCursorWrapper == null || taskCursorWrapper.getCount() == 0)
            return null;
        try {
            taskCursorWrapper.moveToFirst();
            return new TaskCursorWrapper(taskCursorWrapper).getTask();
        } finally {
            taskCursorWrapper.close();
        }

    }

    public List<Task> getTasks(UUID userId, String taskState) {
        List<Task> tasks = new ArrayList<>();
        String whereClaus = TaskColumns.USERID + " = ? AND " + TaskColumns.STATE + " = ?";
        String[] whereArgs = new String[]{userId.toString(), taskState};
        TaskCursorWrapper taskCursorWrapper = queryTaskCursorWrapper(whereClaus, whereArgs);
        if (taskCursorWrapper == null || taskCursorWrapper.getCount() == 0)
            return tasks;

        try {
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()) {

                Task task = taskCursorWrapper.getTask();
                taskCursorWrapper.moveToNext();
                tasks.add(task);

            }
        } finally {
            taskCursorWrapper.close();
        }
        return tasks;

    }

    private TaskCursorWrapper queryTaskCursorWrapper(String whereClaus, String[] whereArgs) {
        Cursor cursor= mDatabase.query(
                TaskDBSchema.Task.NAME,
                null,
                whereClaus,
                whereArgs,
                null,
                null,
                null);
        return new TaskCursorWrapper(cursor);
    }


    public void insertTask(Task task) {
        ContentValues value = getContentValues(task);
        mDatabase.insert(TaskDBSchema.Task.NAME, null, value);

    }


    public void deleteTask(Task task) {
        String whereClause = TaskColumns.TASKID + " =?";
        String[] whereArgs = new String[]{task.getTaskId().toString()};
        mDatabase.delete(TaskDBSchema.Task.NAME, whereClause, whereArgs);


    }

    public void updateTask(Task task) {
        ContentValues value = getContentValues(task);
        String whereClause = TaskColumns.TASKID + " =?";
        String[] whereArgs = new String[]{task.getTaskId().toString()};

        mDatabase.update(
                TaskDBSchema.Task.NAME,
                value,
                whereClause,
                whereArgs);

    }

    private ContentValues getContentValues(Task task) {
        ContentValues value = new ContentValues();
        value.put(TaskColumns.TITLE, task.getTitle());
        value.put(TaskColumns.DESCRIPTION, task.getDescription());
        value.put(TaskColumns.STATE, task.getState());
        value.put(TaskColumns.DATE, DateUtils.getCurrentDate(task.getDate()));
        value.put(TaskColumns.TIME, DateUtils.getCurrentTime(task.getTime()));
        value.put(TaskColumns.TASKID, task.getTaskId().toString());
        value.put(TaskColumns.USERID, task.getUserId().toString());
        return value;
    }


    public int getPositionTask(UUID taskId, String taskState, UUID userId) {

        List<Task> tasks = getTasks(userId, taskState);
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId().toString().equals(taskId.toString()))
                return i;
        }
        return -1;

    }



}



