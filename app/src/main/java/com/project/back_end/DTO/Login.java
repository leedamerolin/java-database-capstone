package com.project.back_end.dto;

public class Login {

    private String identifier;
    private String password;

    // ================= GETTERS =================

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    // ================= SETTERS =================

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}