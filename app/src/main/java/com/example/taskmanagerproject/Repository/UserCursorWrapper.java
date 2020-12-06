package com.example.taskmanagerproject.Repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerproject.DataBase.TaskDBSchema;
import com.example.taskmanagerproject.Model.User;
import com.example.taskmanagerproject.Utils.DateUtils;
import com.example.taskmanagerproject.DataBase.TaskDBSchema.User.UserColumns;
import java.util.Date;
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
        String userName = getString(getColumnIndex(UserColumns.USERNAME));
        String password = getString(getColumnIndex(UserColumns.Password));
        UUID userId = UUID.fromString(
                getString(getColumnIndex(UserColumns.USERID)));
        Date dateInput= DateUtils.getCurrentDate(
                getString(getColumnIndex(UserColumns.DATEINPUT)));
        boolean choose=getInt(getColumnIndex(UserColumns.CHOOSE))==0?false:true;
        return new User(userName, password, userId,dateInput,choose);
    }
}
