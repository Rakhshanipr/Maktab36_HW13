package com.example.hw13.model;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID mId;
    private String mTitle;
    private String mDescribe;
    private State mState;
    private Date mDate;
    private LocalTime mLocalTime;
    private User mUser;


    public Task() {

    }


    public Task(String title, String describe, State state, Date date, LocalTime time, User user) {
        mTitle = title;
        mDescribe = describe;
        mState = state;
        mDate = date;
        mLocalTime = time;
        mUser = user;
    }

    public LocalTime getLocalTime() {
        return mLocalTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.mLocalTime = localTime;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        mDescribe = describe;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }


    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getTitle() {
        return mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
    }

}
