package com.example.lab5_1.Data;


import android.content.Context;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    public static UserDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context,UserDatabase.class,"MyContacts")
                .fallbackToDestructiveMigration()
                .build();
    }
}
