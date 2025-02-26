package com.example.checkbox.Persistance;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.checkbox.checkbox.checkbox;

@androidx.room.Database(entities = {checkbox.class}, version =3)
public abstract class Database extends RoomDatabase {
    public static final String DATABASE_NAME="checkbox";
    private static Database instance;
    static Migration migration=new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'checkboxes' ADD COLUMN 'CState' INTEGER NOT NULL DEFAULT 0");
        }
    };
    static Database getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database.class,
                    DATABASE_NAME
            ).addMigrations(migration)
                    .build();
        }
        return instance;
    }

    public abstract DAO getDao();
}

