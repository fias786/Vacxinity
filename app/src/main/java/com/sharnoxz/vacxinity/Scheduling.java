package com.sharnoxz.vacxinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import com.sharnoxz.vacxinity.accounts.SharedPrefs;

public class Scheduling extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling);
        calendarView = findViewById(R.id.Calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int month = (i1+1)%12;
                int month2 = (i1+2)%12;
                String[] schedule = {""+i2+"."+month+"."+i,""+i2+"."+month2+"."+i};
                SharedPrefs.saveSharedSetting(Scheduling.this,"currentDate",schedule[0]);
                SharedPrefs.saveSharedSetting(Scheduling.this,"nextDate",schedule[1]);
                Intent intent = new Intent(Scheduling.this,Vaccination.class);
                intent.putExtra("Schedule",schedule);
                startActivity(intent);
                SharedPrefs.saveSharedSetting(Scheduling.this,"Scheduling","false");
                finish();
            }
        });
    }
}