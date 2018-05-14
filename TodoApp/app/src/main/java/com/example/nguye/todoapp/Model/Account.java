package com.example.nguye.todoapp.Model;

/**
 * Created by nguye on 10/05/2018.
 */

public class Account {
    private String accessToken;
    private String createTime;
    private String id;
    private String email;
    private String name;
    private String success;

    public Account(String accessToken, String createTime, String id, String email, String name, String success) {
        this.accessToken = accessToken;
        this.createTime = createTime;
        this.id = id;
        this.email = email;
        this.name = name;
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
