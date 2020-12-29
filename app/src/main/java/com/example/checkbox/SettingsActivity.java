package com.example.checkbox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkbox.util.PrefConfig;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {
    private Switch settingsSwitch;
    private TextView opissettings1;
    private int stanje;
    private Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsSwitch = findViewById(R.id.settings_Switch);
        opissettings1 = findViewById(R.id.opissettings1);
        btnReset = findViewById(R.id.btnReset);

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

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlertDialog();
            }
        });

    }

    private void openAlertDialog(){
        AlertDialog dlg = new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Clicking on YES will reset all the tasks and progress you have now.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(SettingsActivity.this, "Everything has been reset", Toast.LENGTH_SHORT).show();
                        clearApplicationData();
                        dialog.dismiss();
                        System.exit(0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(SettingsActivity.this, "Good choice, keep working!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create();
        dlg.show();
    }


    public void clearApplicationData()
    {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }
        return dir.delete();
    }


    private void saveNightModeState(boolean b) {
        if (b){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);}
    }
}