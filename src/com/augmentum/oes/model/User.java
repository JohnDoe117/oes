package com.augmentum.oes.model;

public class User {
    private int id;
    private String userName;
    private String Password;

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", Password=" + Password + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
