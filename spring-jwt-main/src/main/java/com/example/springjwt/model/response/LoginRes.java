package com.example.springjwt.model.response;

public class LoginRes {
    private String email;
    private String token;
    private boolean status;

    public LoginRes(String email, String token,boolean status) {
        this.email = email;
        this.token = token;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public boolean getStatus() {
        return status;
    }

    public void setStatus(String email) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
