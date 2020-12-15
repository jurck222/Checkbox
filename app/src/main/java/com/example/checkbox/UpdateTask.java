package com.example.checkbox;

import android.os.AsyncTask;

import com.example.checkbox.Persistance.DAO;
import com.example.checkbox.checkbox.checkbox;

public class UpdateTask extends AsyncTask<checkbox,Void,Void> {
    private static final String TAG = "InsertTask";
    private DAO mDAO;
    public UpdateTask(DAO dao){
        mDAO=dao;
    }
    @Override
    protected Void doInBackground(checkbox... checkboxes) {
        mDAO.update(checkboxes);
        return null;
    }
}
