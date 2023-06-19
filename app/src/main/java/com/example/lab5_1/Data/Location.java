package com.example.lab5_1.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        primaryKeys = {
                "nom", "user_email"
        },
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "email",
                        childColumns = "user_email",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Location implements Serializable {

    @ColumnInfo(name = "nom")
    @NonNull
    private String nom;

    @ColumnInfo(name = "user_email")
    @NonNull
    private String userEmail;


    public Location(@NonNull String nom, @NonNull String userEmail) {
        this.nom = nom;
        this.userEmail = userEmail;
    }

    @NonNull
    public String getNom() {
        return nom;
    }


    public void setNom(@NonNull String nom) {
        this.nom = nom;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String email) {
        this.userEmail = email;
    }
}