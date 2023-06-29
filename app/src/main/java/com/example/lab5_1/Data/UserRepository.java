package com.example.lab5_1.Data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

    private ArrayList<User> users = new ArrayList<>();
    private final MutableLiveData<List<User>> userLiveData = new MutableLiveData<>(users);
    private final UserDao userDAO;
    private final UserDatabase myDatabase;

    @Inject
    public UserRepository(UserDatabase database) {
        userDAO = database.getUserDao();
        myDatabase = database;
    }

    public void addUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            myDatabase.beginTransaction();
            try {
                userDAO.addUser(user);
                myDatabase.setTransactionSuccessful();
            } catch (Exception E) {
                Log.e("INSERT ERROR", "addUser: Utilisateur existant ");
            } finally {
                myDatabase.endTransaction();
            }
        });


    }

    public void removeUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            userDAO.deleteUser(user);
        });
    }

    public LiveData<List<User>> getUsers() {
        return userDAO.getAllUser();
    }
}
