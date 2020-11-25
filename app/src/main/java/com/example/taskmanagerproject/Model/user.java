package com.example.taskmanagerproject.Model;

import java.io.Serializable;
import java.util.UUID;

public class user implements Serializable {
    private String mUserName;
    private String mPassWord;
    private UUID mUserId;

    public user() {
        mUserId=UUID.randomUUID();
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public void setPassWord(String passWord) {
        mPassWord = passWord;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
    }
}
