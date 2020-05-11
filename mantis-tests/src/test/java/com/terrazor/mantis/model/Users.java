package com.terrazor.mantis.model;

public class Users {

    private String user;
    private String password;
    private String email;

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Users setUser(String user) {
        this.user = user;
        return this;
    }

    public Users setPassword(String password) {
        this.password = password;
        return this;
    }
    public Users setEmail(String email) {
        this.email = email;
        return this;
    }

}
