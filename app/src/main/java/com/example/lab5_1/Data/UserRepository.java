package com.example.lab5_1.Data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {
    private final UserDao userDAO;

    @Inject
    public UserRepository(UserDatabase database) {
        userDAO = database.getUserDao();
    }

    public void addUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
                userDAO.addUser(user);
        });
    }

    public void removeUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            userDAO.deleteUser(user);
        });
    }
    public void removeUserWithEmail(String userEmail) {
        Executors.newSingleThreadExecutor().execute(() -> {
            userDAO.deleteUserWithEmail(userEmail);
        });
    }

    public LiveData<List<User>> getUsers() {
        return userDAO.getAllUser();
    }

    public LiveData<List<Location>> getLocationsFromAUser(String userEmail) {
       return userDAO.getLocationsFromAUser(userEmail);
    };

    public void addLocation(Location location) {
        Executors.newSingleThreadExecutor().execute(() -> {
        userDAO.addLocation(location);
        });
    };

    public void deleteLocation(Location location) {

            Executors.newSingleThreadExecutor().execute(() -> {
        userDAO.deleteLocation(location);
            });
    };
}