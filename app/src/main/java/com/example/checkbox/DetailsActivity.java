package com.example.checkbox;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.checkbox.Persistance.Repository;
import com.example.checkbox.checkbox.checkbox;
import com.example.checkbox.util.PrefConfig;
import com.example.checkbox.util.ReminderBroadcast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DetailsActivity extends AppCompatActivity implements
        View.OnClickListener
        {
    private static final String TAG = "DetailsActivity";
    //ui
    private EditText mEditTitle;
    private TextView izberiCas,izpisiCas;
    CalendarView calendarView;
    private Button mAdd;
    private Button mCancle;
    //vars


    int ura,min;
    //notification stuff
    private MainActivity mActivity;
    long izpisujem;
    String mCas;
    String datumRacun;
    Date datum123;
    int stetjeNotifications;

    private String datum;
    private boolean mIsNewCheck;
    private checkbox mInitialCheckbox;
    private Repository mRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mActivity = new MainActivity();
        mEditTitle = findViewById(R.id.edittitleid);
        stetjeNotifications = 0;
        izberiCas= findViewById(R.id.cas);
        izpisiCas=findViewById(R.id.izbranicas);
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

                izpisujem = view.getDate();
                datumRacun = ""+year+"/"+(month+1)+"/"+dayOfMonth+" ";
                //Toast.makeText(DetailsActivity.this, String.valueOf(izpisujem), Toast.LENGTH_SHORT).show();

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
        izberiCas.setOnClickListener(this);
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
        mInitialCheckbox.setCas(mCas);
        saveChanges();
    }
    private void setCheckProperties() {
        mEditTitle.setText(mInitialCheckbox.getNaslov());

    }
    private void setNewCheckProperties() {

        mInitialCheckbox = new checkbox();
        mInitialCheckbox.setDatumKonca(datum);
        mInitialCheckbox.setCas(mCas);

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



    //notifications
    public void triggerNotif(long opominCas) {
        Random r = new Random();
        int idNotif = r.nextInt(10000 - 0 + 1);
        Intent intent2 = new Intent(DetailsActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(DetailsActivity.this,idNotif,intent2,0);

        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);

        long spomniMe = opominCas;

        alarmManager.set(AlarmManager.RTC_WAKEUP, spomniMe-30000,pendingIntent);
    }

    public void notif(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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
                int stevec = PrefConfig.getVse(this);
                stevec++;
                PrefConfig.setVse(getApplicationContext(), stevec);

                int stevec2 = PrefConfig.getTotal(this);
                stevec2++;
                PrefConfig.setTotal(getApplicationContext(),stevec2);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date datum123 = null;
                try {
                    datum123 = sdf.parse(datumRacun);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long milis = datum123.getTime();
                //Toast.makeText(this, String.valueOf(datum123), Toast.LENGTH_SHORT).show();

                notif();
                triggerNotif(milis);
                ButtonSave();
                finish();
                break;
            }
            case R.id.cas:{
                Calendar calendar=Calendar.getInstance();
                ura=calendar.get(Calendar.HOUR_OF_DAY);
                min=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(DetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Calendar dobimpodatke = Calendar.getInstance();
                        ura=hourOfDay;
                        min=minute;
                        long cascas = view.getHour() * 60 * 60 * 1000;
                        long cascas2 = view.getMinute() * 60 * 1000;
                        long skupno = cascas + cascas2;
                        if (minute > 9){
                        datumRacun+=hourOfDay+":"+minute+":00";}
                        else {datumRacun+=hourOfDay+":0"+minute+":00";}
                        //izpisujem+=skupno;
                        //Toast.makeText(DetailsActivity.this, datumRacun+" tole je pa ko ga system gettam " + String.valueOf(System.currentTimeMillis()), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(mActivity, String.valueOf(dobimpodatke.getTimeInMillis()), Toast.LENGTH_SHORT).show();
                        if(min<10){
                            mCas=ura+":0"+min;
                        }
                        else{
                            mCas= ura+":"+min;
                        }

                        izpisiCas.setText(mCas);
                    }
                },ura,min,false);
                timePickerDialog.show();

            }
        }
    }
}