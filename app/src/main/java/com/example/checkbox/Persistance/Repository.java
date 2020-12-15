package com.example.checkbox.Persistance;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.checkbox.Async.DeleteTask;
import com.example.checkbox.Async.InsertTask;
import com.example.checkbox.UpdateTask;
import com.example.checkbox.checkbox.checkbox;

import java.util.List;

public class Repository {
    private Database mDatabase;
    public Repository(Context context){
        mDatabase = Database.getInstance(context);
    }
    public void insert(checkbox checkbox){
        new InsertTask(mDatabase.getDao()).execute(checkbox);
    }
    public void update(checkbox checkbox){
        new UpdateTask(mDatabase.getDao()).execute(checkbox);
    }
    public void delete(checkbox checkbox){
        new DeleteTask(mDatabase.getDao()).execute(checkbox);
    }
   public LiveData<List<checkbox>> retrieveCheckboxes(){
       return mDatabase.getDao().getCheckboxes();
   }
}
