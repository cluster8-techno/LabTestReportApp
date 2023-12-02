package com.onlinelabreport.onlinelabreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LabAppointment extends AppCompatActivity {

    Button bookBtn, viewBtn;
    FloatingActionButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_appointment);

        bookBtn = findViewById(R.id.bookingBtn);
        viewBtn = findViewById(R.id.viewBookBtn);
        backBtn = findViewById(R.id.backHomeBtn);

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabAppointment.this, LabBooking.class);
                startActivity(intent);
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabAppointment.this, LabView.class);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabAppointment.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}