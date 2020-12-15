package com.example.checkbox.Async;
import android.os.AsyncTask;
import com.example.checkbox.Persistance.DAO;
import com.example.checkbox.checkbox.checkbox;
public class InsertTask extends AsyncTask<checkbox,Void,Void> {
    private static final String TAG = "InsertTask";
    private DAO mDAO;
    public InsertTask(DAO dao){
        mDAO=dao;
    }
    @Override
    protected Void doInBackground(checkbox... checkboxes) {
        mDAO.insertCheckboxes(checkboxes);
        return null;
    }
}
