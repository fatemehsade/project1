package com.example.taskmanagerproject.Repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanagerproject.DataBase.TaskDataBase;
import com.example.taskmanagerproject.DataBase.UserDao;
import com.example.taskmanagerproject.Model.User;

import java.util.List;
import java.util.UUID;

public class UserDBRepository {
    public static UserDBRepository sInstance;
    private Context mContext;
    private UserDao mDatabase;

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

        TaskDataBase taskDataBase = Room.databaseBuilder(
                mContext,
                TaskDataBase.class,
                "task.db")
                .allowMainThreadQueries().build();

        mDatabase = taskDataBase.getUserDataBase();
    }

    public void insertUser(User user) {
        mDatabase.insertUser(user);

    }


    public void deleteUser(User user) {
        mDatabase.deleteUser(user);

    }

    public void updateUser(User user) {
        mDatabase.updateUser(user);

    }

    public User getUserWithId(UUID userId) {
        return mDatabase.getUserWithId(userId);


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
        return mDatabase.getUsers();
    }


    public boolean userExist(String userName) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userName))
                return true;

        }
        return false;
    }

}
