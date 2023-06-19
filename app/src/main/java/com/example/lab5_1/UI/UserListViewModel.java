package com.example.lab5_1.UI;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab5_1.Data.*;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserListViewModel extends ViewModel {

    private UserRepository repository;

    @Inject
    public UserListViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public void addUser(String name, String email) {
        repository.addUser(new User(name, email));
    }

    public void removeUser(User user) { repository.removeUser(user); }

    public void removeUserWithEmail(String userEmail) { repository.removeUserWithEmail(userEmail); }

    public LiveData<List<User>> getUsers() {
        return repository.getUsers();
    }

    public LiveData<List<Location>> getLocationsFromAUser(String userEmail) {
        return repository.getLocationsFromAUser(userEmail);
    };

    public void addLocation(Location location) {
        repository.addLocation(location);
    };

    public void deleteLocation(Location location) {
        repository.deleteLocation(location);
    };

}
