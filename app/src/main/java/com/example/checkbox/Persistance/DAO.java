package com.example.checkbox.Persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.checkbox.checkbox.checkbox;

import java.util.List;

@Dao
public interface DAO {
    @Insert
    long[] insertCheckboxes(checkbox... checkboxes);
    @Query("SELECT * FROM checkboxes")
    LiveData<List<checkbox>> getCheckboxes();

    @Delete
    int delete(checkbox... checkboxes);
    @Update
    int update(checkbox... checkboxes);
}
