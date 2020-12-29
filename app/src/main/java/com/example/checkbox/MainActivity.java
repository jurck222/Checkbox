package com.example.checkbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.checkbox.Persistance.Repository;
import com.example.checkbox.adapter.CheckboxRecyclerAdapter;
import com.example.checkbox.checkbox.checkbox;
import com.example.checkbox.util.PrefConfig;
import com.example.checkbox.util.ReminderBroadcast;
import com.example.checkbox.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CheckboxRecyclerAdapter.OnCheckListener, View.OnClickListener {
    private static final String TAG = "MainActivity";
    //UI
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    //VARS
    private ArrayList<checkbox> mCheckboxes= new ArrayList<>();
    private CheckboxRecyclerAdapter mCheckboxRecyclerAdapter;
    private Repository mRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView= findViewById(R.id.recyclerview);
        findViewById(R.id.fab).setOnClickListener(this);
        mRepository=new Repository(this);
        initRecyclerView();
        //fakechecks();
        retrieveCheckboxes();
        setSupportActionBar((Toolbar)findViewById(R.id.checkbox_toolbar));
        setTitle("Checkboxes");


    }

    /*
    public void triggerNotif(long opominCas) {

        Intent intent2 = new Intent(MainActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent2,0);

        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);

        long spomniMe = opominCas;

        alarmManager.set(AlarmManager.RTC_WAKEUP, spomniMe,pendingIntent);
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

     */


    //init menu options
    public void openInformation() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    public void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_stats:
                openInformation();
                return true;
            case R.id.menu_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fakechecks(){
        for (int i = 0; i<1000; i++){
            checkbox checkbox =new checkbox();
            checkbox.setNaslov("naslov #: "+i);
            checkbox.setDatumKonca("15/Jan/2019");

            mCheckboxes.add(checkbox);
        }
        mCheckboxRecyclerAdapter.notifyDataSetChanged();
    }
    private void retrieveCheckboxes() {
        mRepository.retrieveCheckboxes().observe(this, new Observer<List<checkbox>>() {
            @Override
            public void onChanged(List<checkbox> checkboxes) {
                if(mCheckboxes.size()>0){
                    mCheckboxes.clear();
                }
                if (checkboxes!=null){
                    mCheckboxes.addAll(checkboxes);
                }
                mCheckboxRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator=new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mCheckboxRecyclerAdapter=new CheckboxRecyclerAdapter(mCheckboxes,this);
        mRecyclerView.setAdapter(mCheckboxRecyclerAdapter);
    }

    @Override
    public void onCheckClick(int position) {

        Intent intent=new Intent(this,DetailsActivity.class);
        intent.putExtra("selected_checkbox",mCheckboxes.get(position));
        startActivity(intent);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }
    private void deleteCheck(checkbox checkbox){
        if (checkbox.getCheckboxState()==1){
            int stevec3 = PrefConfig.getObkljukane(this);
            stevec3++;
            PrefConfig.setObkljukane(getApplicationContext(),stevec3);
        }

        mCheckboxes.remove(checkbox);
        mCheckboxRecyclerAdapter.notifyDataSetChanged();
        mRepository.delete(checkbox);

        //vecanje izbrisanih, manjsanje vseh trenutnih
        int stevec = PrefConfig.getVse(this);
        stevec--;
        int stevec2 = PrefConfig.getIzbrisano(this);
        stevec2++;

        PrefConfig.setVse(getApplicationContext(), stevec);
        PrefConfig.setIzbrisano(getApplicationContext(),stevec2);
    }
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteCheck(mCheckboxes.get(viewHolder.getAdapterPosition()));
        }
    };
}