package com.vinamine.mc.entity;

public class RegisterUser {
    private String phone;
    private String email;
    private String fullName;
    private String password;
    private String rePassword;

    public RegisterUser(String phone, String email, String fullName, String password, String rePassword) {
        this.phone = phone;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.rePassword = rePassword;
    }

    public RegisterUser() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public RegisterBody toBody() {
        RegisterBody registerBody = new RegisterBody();
        registerBody.setEmail(this.email);
        registerBody.setFullName(this.fullName);
        registerBody.setPassword(this.password);
        registerBody.setRepassword(this.rePassword);
        return registerBody;
    }
}
