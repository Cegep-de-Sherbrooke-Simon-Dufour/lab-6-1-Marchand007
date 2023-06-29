package com.example.lab5_1.Data;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;
import java.util.Map;

@Dao
public interface UserDao {

    @Query("SELECT * from User")
    public LiveData<List<User>> getAllUser();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addUser(User user);

    @Delete
    public void deleteUser(User user);

    @Query("Delete FROM User WHERE User.email = :userEmail")
    public void deleteUserWithEmail(String userEmail);

    @Query("SELECT * FROM Location WHERE Location.user_email = :userEmail")
    public LiveData<List<Location>> getLocationsFromAUser(String userEmail);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addLocation(Location location);

    @Delete
    public void deleteLocation(Location location);



}
