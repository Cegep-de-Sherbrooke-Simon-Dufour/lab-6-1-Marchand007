package com.example.lab5_1.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * from User")
    public LiveData<List<User>> getAllUser();

    @Insert
    public void addUser(User user);

    @Delete
    public void deleteUser(User user);





}
