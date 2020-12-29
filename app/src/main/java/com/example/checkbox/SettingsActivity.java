package com.example.checkbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.checkbox.util.PrefConfig;

public class SettingsActivity extends AppCompatActivity {
    private Switch settingsSwitch;
    private TextView opissettings1;
    private int stanje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsSwitch = findViewById(R.id.settings_Switch);
        opissettings1 = findViewById(R.id.opissettings1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stanje = PrefConfig.getNmode(this);
        if (stanje==-1) {
            settingsSwitch.setChecked(false);
            opissettings1.setText("Night mode is currently OFF");
        }
        else {
            settingsSwitch.setChecked(true);
            opissettings1.setText("Night mode is currently ON");
        }


        settingsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    opissettings1.setText("Night mode is currently ON");
                    saveNightModeState(true);
                    recreate();
                }
                else {
                    opissettings1.setText("Night mode is currently OFF");
                    saveNightModeState(false);
                    recreate();
                }
                stanje*=-1;
                PrefConfig.setNmode(getApplicationContext(),stanje);
            }
        });
    }

    private void saveNightModeState(boolean b) {
        if (b){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);}
    }
}