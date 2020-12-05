package com.example.taskmanagerproject.Repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerproject.DataBase.TaskDBSchema;
import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.Utils.DateUtils;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask(){
        String title = getString(getColumnIndex(TaskDBSchema.Task.TaskColumns.TITLE));
        String description = getString(getColumnIndex(TaskDBSchema.Task.TaskColumns.DESCRIPTION));
        String state = getString(getColumnIndex(TaskDBSchema.Task.TaskColumns.STATE));
        UUID userId = UUID.fromString(getString(
                getColumnIndex(TaskDBSchema.Task.TaskColumns.USERID)));
        UUID taskId = UUID.fromString(getString(
                getColumnIndex(TaskDBSchema.Task.TaskColumns.TASKID)));
        Date date = DateUtils.getCurrentDate(
                getString(getColumnIndex(TaskDBSchema.Task.TaskColumns.DATE)));
        Date time = DateUtils.getCurrentTime(
                getString(getColumnIndex(TaskDBSchema.Task.TaskColumns.TIME)));

        return new Task(title, description, date, time, state, taskId, userId);

    }
}
