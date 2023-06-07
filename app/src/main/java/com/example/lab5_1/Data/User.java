package com.example.lab5_1.Data;

import java.io.Serializable;

public class User implements Serializable {

    private String _nom;
    private String _email;

    public User(String nom, String email) {
        this._nom = nom;
        this._email = email;
    }

    public String getNom() {
        return _nom;
    }

    public String getEmail() {
        return _email;
    }
}
