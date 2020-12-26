package com.example.taskmanagerproject.Model;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Entity(tableName = "userTable")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long mId;

    @ColumnInfo(name = "userName")
    private String mUserName;

    @ColumnInfo(name = "password")
    private String mPassWord;

    @ColumnInfo(name = "userId")
    private UUID mUserId;

    @ColumnInfo(name = "dateInput")
    private Date mDateInput;

    @ColumnInfo(name = "choose")
    private boolean mChoose;

    public User(String userName, String passWord, UUID userId, Date dateInput,boolean choose) {
        mUserName = userName;
        mPassWord = passWord;
        mUserId = userId;
        mDateInput=dateInput;
        mChoose=choose;

    }


    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
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
