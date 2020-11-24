package com.example.taskmanagerproject.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.taskmanagerproject.Utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private Date mTime;
    private String mState;
    private UUID mTaskId;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Task() {
        mTaskId = UUID.randomUUID();
        mDate= DateUtils.randomDate();
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public UUID getTaskId() {
        return mTaskId;
    }

    public void setTaskId(UUID taskId) {
        mTaskId = taskId;
    }
}
