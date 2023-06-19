package com.example.lab5_1.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @ColumnInfo(name = "nom")
    private String nom;
    @PrimaryKey()
    @ColumnInfo(name = "email")
    @NonNull
    private String email;

    private String uri;

    public User(String nom, @NonNull String email, String uri) {
        this.nom = nom;
        this.email = email;
        this.uri = uri;
    }

    public String getUri() { return uri; }
    public String getNom() {
        return nom;
    }
    @NonNull
    public String getEmail() {
        return email;
    }
}
