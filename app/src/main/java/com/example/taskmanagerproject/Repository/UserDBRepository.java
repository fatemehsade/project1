package com.example.taskmanagerproject.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerproject.DataBase.TaskDBHelper;
import com.example.taskmanagerproject.DataBase.TaskDBSchema;
import com.example.taskmanagerproject.Model.User;
import com.example.taskmanagerproject.DataBase.TaskDBSchema.User.UserColumns;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository {
    public static UserDBRepository sInstance;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static UserDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserDBRepository(context);
        return sInstance;
    }

    public static void setInstance(UserDBRepository instance) {
        sInstance = instance;
    }

    private UserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskDBHelper taskDBHelper = new TaskDBHelper(mContext);
        mDatabase = taskDBHelper.getWritableDatabase();
    }

    public void insertUser(User user) {
        ContentValues value = getContentValues(user);
        mDatabase.insert(TaskDBSchema.User.NAME, null, value);

    }

    private ContentValues getContentValues(User user) {
        ContentValues value = new ContentValues();
        value.put(UserColumns.USERID, user.getUserId().toString());
        value.put(UserColumns.USERNAME, user.getUserName());
        value.put(UserColumns.Password, user.getPassWord());
        return value;
    }

    public void deleteUser(User user) {
        String whereClaus = UserColumns.USERID + " = ?";
        String[] whereArgs = new String[]{user.getUserId().toString()};
        mDatabase.delete(TaskDBSchema.User.NAME, whereClaus, whereArgs);

    }

    public void updateUser(User user) {
        ContentValues value = getContentValues(user);
        String whereClause = UserColumns.USERID + " = ?";
        String[] whereArgs = new String[]{user.getUserId().toString()};
        mDatabase.update(TaskDBSchema.User.NAME, value, whereClause, whereArgs);

    }

    public User getUserWithId(UUID userId) {
        String whereClaus = UserColumns.USERID + " = ?";
        String[] whereArgs = new String[]{userId.toString()};
        UserCursorWrapper userCursorWrapper = queryUserCursorWrapper(whereClaus, whereArgs);

        if (userCursorWrapper == null || userCursorWrapper.getCount() == 0)
            return null;

        try {
            userCursorWrapper.moveToFirst();
            return userCursorWrapper.getUser();
        } finally {
            userCursorWrapper.close();
        }


    }


    public User returnUserWithUserName(String userName) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userName))
                return users.get(i);

        }
        return null;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        UserCursorWrapper userCursorWrapper = queryUserCursorWrapper(null, null);

        if (userCursorWrapper == null || userCursorWrapper.getCount() == 0)
            return users;

        try {
            userCursorWrapper.moveToFirst();
            while (!userCursorWrapper.isAfterLast()) {
                users.add(userCursorWrapper.getUser());
                userCursorWrapper.moveToNext();
            }
        } finally {
            userCursorWrapper.close();
        }
        return users;
    }


    public boolean userExist(String userName) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userName))
                return true;

        }
        return false;
    }
    private UserCursorWrapper queryUserCursorWrapper(String whereClaus, String[] whereArgs) {
        Cursor cursor= mDatabase.query(
                TaskDBSchema.User.NAME,
                null,
                whereClaus,
                whereArgs,
                null,
                null,
                null);
        return new UserCursorWrapper(cursor);
    }
}
