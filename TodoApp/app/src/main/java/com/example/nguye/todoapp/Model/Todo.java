package com.example.nguye.todoapp.Model;

/**
 * Created by nguye on 08/05/2018.
 */

public class Todo {
    private String mComplete;
    private String mId;
    private String mOwner;
    private String mTitle;
    private String mCreated;
    private String mUpdate;

    public Todo(String mComplete, String mId, String mOwner, String mTitle, String mCreated, String mUpdate) {
        this.mComplete = mComplete;
        this.mId = mId;
        this.mOwner = mOwner;
        this.mTitle = mTitle;
        this.mCreated = mCreated;
        this.mUpdate = mUpdate;
    }

    public String getmComplete() {
        return mComplete;
    }

    public void setmComplete(String mComplete) {
        this.mComplete = mComplete;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmOwner() {
        return mOwner;
    }

    public void setmOwner(String mOwner) {
        this.mOwner = mOwner;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCreated() {
        return mCreated;
    }

    public void setmCreated(String mCreated) {
        this.mCreated = mCreated;
    }

    public String getmUpdate() {
        return mUpdate;
    }

    public void setmUpdate(String mUpdate) {
        this.mUpdate = mUpdate;
    }
}
