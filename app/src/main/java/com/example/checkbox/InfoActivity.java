package com.example.checkbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.checkbox.util.PrefConfig;

public class InfoActivity extends AppCompatActivity {

    private TextView txtVse, txtObkljukano, txtIzbrisano, txtTotal, txtProgress;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        txtVse=findViewById(R.id.txtVse);
        txtObkljukano=findViewById(R.id.txtObkljukano);
        txtIzbrisano=findViewById(R.id.txtIzbrisano);
        txtTotal=findViewById(R.id.txtTotal);
        progressBar=findViewById(R.id.progress_bar);
        txtProgress=findViewById(R.id.progress_text);


        Toolbar toolbar = (Toolbar) findViewById(R.id.stats_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        int vsi = PrefConfig.getVse(this);
        txtVse.setText(vsi+" tasks");

        int obkljukani = PrefConfig.getObkljukane(this);
        txtObkljukano.setText(obkljukani+" tasks");

        int izbrisani = PrefConfig.getIzbrisano(this);
        txtIzbrisano.setText(izbrisani+" tasks");

        int total = PrefConfig.getTotal(this);
        txtTotal.setText(total + " tasks");

        if (obkljukani!=0 && izbrisani!=0){
        double povprecje = ((double)obkljukani/izbrisani)*100;
        progressBar.setProgress((int)povprecje);
        txtProgress.setText((int)povprecje+"%");
        }

    }
}