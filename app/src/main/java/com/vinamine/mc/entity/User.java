package com.vinamine.mc.entity;

public class User {
    private String email;
    private String username;
    private String avatar;
    private String badge;
    private int role;

    public User() {
    }

    public User(String email, String username, String avatar, String badge, int role) {
        this.email = email;
        this.username = username;
        this.avatar = avatar;
        this.badge = badge;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
