package com.example.taskmanagerproject.Model;

import android.os.Build;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskmanagerproject.Utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Entity(tableName = "taskTable")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "time")
    private Date mTime;

    @ColumnInfo(name = "taskState")
    private String mState;

    @ColumnInfo(name = "taskId")
    private UUID mTaskId;

    @ColumnInfo(name = "userId")
    private UUID mUserId;

    @ColumnInfo(name = "imageAddress")
    private String mImageAddress;

    public Task() {
        mTaskId = UUID.randomUUID();
        mDate= DateUtils.randomDate();
    }

    public Task(String title, String description, Date date, Date time, String state, UUID taskId, UUID userId) {
        mTitle = title;
        mDescription = description;
        mDate = date;
        mTime = time;
        mState = state;
        mTaskId = taskId;
        mUserId = userId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
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

    public String getImageAddress() {
        return mImageAddress;
    }

    public void setImageAddress(String imageAddress) {
        mImageAddress = imageAddress;
    }
}
