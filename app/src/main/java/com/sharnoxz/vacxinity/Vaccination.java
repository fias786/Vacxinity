package com.sharnoxz.vacxinity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharnoxz.vacxinity.accounts.SharedPrefs;

public class Vaccination extends AppCompatActivity {

    TextView currentDate , nextDate;
    LinearLayout editScheduledDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        currentDate = findViewById(R.id.Date);
        nextDate = findViewById(R.id.nextDate);
        editScheduledDate = findViewById(R.id.EditDate);
        currentDate.setText(SharedPrefs.readSharedSetting(this,"currentDate","10.4.2021"));
        nextDate.setText(SharedPrefs.readSharedSetting(this,"nextDate","10.4.2021"));
        editScheduledDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vaccination.this,Scheduling.class);
                startActivity(intent);
                finish();
            }
        });
    }
}