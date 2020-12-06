package com.example.taskmanagerproject.Model;

import android.provider.ContactsContract;
import android.widget.CheckBox;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class User implements Serializable {
    private String mUserName;
    private String mPassWord;
    private UUID mUserId;
    private Date mDateInput;
    private boolean mChoose;

    public User(String userName, String passWord, UUID userId, Date dateInput,boolean choose) {
        mUserName = userName;
        mPassWord = passWord;
        mUserId = userId;
        mDateInput=dateInput;
        mChoose=choose;

    }



    public Date getDateInput() {
        return mDateInput;
    }

    public void setDateInput(Date dateInput) {
        mDateInput = dateInput;
    }

    public boolean isChoose() {
        return mChoose;
    }

    public void setChoose(boolean choose) {
        mChoose = choose;
    }

    public User() {

        mUserId=UUID.randomUUID();
        mDateInput=new Date();
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
