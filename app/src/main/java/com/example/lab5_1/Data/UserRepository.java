package com.example.lab5_1.Data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

    private ArrayList<User> users = new ArrayList<>();
    private final MutableLiveData<List<User>> userLiveData = new MutableLiveData<>(users);

    @Inject
    public UserRepository() {
    }

    public void addUser(User user) {
        users.add(user);
        userLiveData.setValue(new ArrayList<>(users));
    }

    public void removeUser(User user) {
        users.remove(user);
        userLiveData.setValue(new ArrayList<>(users));
    }

    public LiveData<List<User>> getUsers() {
        return userLiveData;
    }
}
