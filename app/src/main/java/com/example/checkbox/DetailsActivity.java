package com.example.checkbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.checkbox.Persistance.Repository;
import com.example.checkbox.checkbox.checkbox;

import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity implements
        View.OnClickListener
        {
    private static final String TAG = "DetailsActivity";
    //ui
    private EditText mEditTitle;
    private TextView dateText;
    CalendarView calendarView;
    private Button mAdd;
    private Button mCancle;
    //vars
    private String datum;
    private boolean mIsNewCheck;
    private checkbox mInitialCheckbox;
    private Repository mRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mEditTitle = findViewById(R.id.edittitleid);
        dateText = findViewById(R.id.texthello);
        calendarView = (CalendarView) findViewById(R.id.koledar);
        mAdd = findViewById(R.id.add);
        mCancle = findViewById(R.id.cancle);
        mRepository = new Repository(this);
        setListeners();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String mesec = fromNumberToMonth(month + 1);
                String date = (dayOfMonth + "." + mesec + " " + year);
                datum = date;
                dateText.setText(date);
            }
        });
        if (getIncomingIntent()) {
            setNewCheckProperties();
        } else {
            setCheckProperties();
        }
    }
    public void setListeners() {
        mAdd.setOnClickListener(this);
        mCancle.setOnClickListener(this);
    }
    public boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_checkbox")) {
            mInitialCheckbox = getIntent().getParcelableExtra("selected_checkbox");
            mIsNewCheck = false;
            return false;
        }
        mIsNewCheck = true;
        return true;
    }
    private void saveChanges() {
        if (mIsNewCheck) {
            saveNewCheckbox();
        } else {
            update();
        }
    }
    private void update(){
        mRepository.update(mInitialCheckbox);
    }
    private void saveNewCheckbox() {
        Log.d(TAG, "saveNewCheckbox: " + mInitialCheckbox.toString());
        mRepository.insert(mInitialCheckbox);
    }
    private void ButtonSave() {
        mInitialCheckbox.setNaslov(mEditTitle.getText().toString());
        mInitialCheckbox.setDatumKonca(datum);
        saveChanges();
    }
    private void setCheckProperties() {
        mEditTitle.setText(mInitialCheckbox.getNaslov());
        dateText.setText(mInitialCheckbox.getDatumKonca());
    }
    private void setNewCheckProperties() {

        mInitialCheckbox = new checkbox();
        mInitialCheckbox.setDatumKonca(datum);

    }
    private String fromNumberToMonth(int month) {
        switch (month) {
            case 1: {
                return "Jan";
            }
            case 2: {
                return "Feb";
            }
            case 3: {
                return "Mar";
            }
            case 4: {
                return "Apr";
            }
            case 5: {
                return "May";
            }
            case 6: {
                return "Jun";
            }
            case 7: {
                return "Jul";
            }
            case 8: {
                return "Aug";
            }
            case 9: {
                return "Sep";
            }
            case 10: {
                return "Oct";
            }
            case 11: {
                return "Nov";
            }
            case 12: {
                return "Dec";
            }
            default: {
                return "Error";
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cancle: {
                finish();
                break;
            }
            case R.id.add: {
                ButtonSave();
                finish();
                break;
            }
        }
    }
}