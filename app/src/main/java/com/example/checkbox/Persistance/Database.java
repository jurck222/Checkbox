package com.example.checkbox.Persistance;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.checkbox.checkbox.checkbox;

@androidx.room.Database(entities = {checkbox.class}, version =1)
public abstract class Database extends RoomDatabase {
    public static final String DATABASE_NAME="checkbox";
    private static Database instance;
    static Database getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract DAO getDao();
}

