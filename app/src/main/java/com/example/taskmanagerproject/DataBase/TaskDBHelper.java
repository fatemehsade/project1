package com.example.taskmanagerproject.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.taskmanagerproject.DataBase.TaskDBSchema.User.UserColumns;
import com.example.taskmanagerproject.DataBase.TaskDBSchema.Task.TaskColumns;
import androidx.annotation.Nullable;

public class TaskDBHelper extends SQLiteOpenHelper {
    public TaskDBHelper(@Nullable Context context) {
        super(context, TaskDBSchema.NAME, null, TaskDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder taskQuery=new StringBuilder();
        TaskQuery(taskQuery);
        db.execSQL(taskQuery.toString());

        StringBuilder userQuery=new StringBuilder();
        UserQuery(userQuery);
        db.execSQL(userQuery.toString());

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private void UserQuery(StringBuilder userQuery) {
        userQuery.append("CREATE TABLE " + TaskDBSchema.User.NAME+ " (");
        userQuery.append(UserColumns.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        userQuery.append(UserColumns.USERID + " TEXT NOT NULL,");
        userQuery.append(UserColumns.USERNAME +" TEXT,");
        userQuery.append(UserColumns.DATEINPUT +" TEXT,");
        userQuery.append(UserColumns.CHOOSE +" INTEGER,");
        userQuery.append(UserColumns.Password+" TEXT");
        userQuery.append(");");
    }

    private void TaskQuery(StringBuilder taskQuery) {
        taskQuery.append("CREATE TABLE "+ TaskDBSchema.Task.NAME+" (");
        taskQuery.append(TaskColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        taskQuery.append(TaskColumns.TASKID +" TEXT NOT NULL,");
        taskQuery.append(TaskColumns.USERID +" TEXT NOT NULL,");
        taskQuery.append(TaskColumns.TITLE +" TEXT NOT NULL,");
        taskQuery.append(TaskColumns.DESCRIPTION +" TEXT,");
        taskQuery.append(TaskColumns.DATE +" TEXT,");
        taskQuery.append(TaskColumns.TIME +" TEXT,");
        taskQuery.append(TaskColumns.STATE +" TEXT,");
        taskQuery.append(" FOREIGN KEY(userId) REFERENCES taskTable(userId)");
        taskQuery.append(");");
    }

}
