package com.example.lab5_1.Data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Location.class}, version = 3)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();

}
