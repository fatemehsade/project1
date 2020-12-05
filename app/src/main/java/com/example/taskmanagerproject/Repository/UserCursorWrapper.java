package com.example.taskmanagerproject.Repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerproject.DataBase.TaskDBSchema;
import com.example.taskmanagerproject.Model.User;

import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser(){
        String userName = getString(getColumnIndex(TaskDBSchema.User.UserColumns.USERNAME));
        String password = getString(getColumnIndex(TaskDBSchema.User.UserColumns.Password));
        UUID userId = UUID.fromString(
                getString(getColumnIndex(TaskDBSchema.User.UserColumns.USERID)));
        return new User(userName, password, userId);
    }
}
