package com.onlinelabreport.onlinelabreport;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {
    Button reportviewBtn, btnAppointment, btnGallery, btnNews;
    FloatingActionButton btnNotification, btnOtherDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reportviewBtn = findViewById(R.id.reportBtn);
        btnAppointment = findViewById(R.id.labBookBtn);
        btnGallery = findViewById(R.id.galleryBtn);
        btnNews = findViewById(R.id.newsBtn);
        btnNews = findViewById(R.id.newsBtn);
        btnNotification = findViewById(R.id.notificationBtn);
        btnOtherDetails = findViewById(R.id.otherDetailsBtn);

        reportviewBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(HomeActivity.this, ReportView.class);
                startActivity(intent);
             }
        });

        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LabAppointment.class);
                startActivity(intent);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, VideoGallery.class);
                startActivity(intent);
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, News.class);
                startActivity(intent);
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Notification.class);
                startActivity(intent);
            }
        });

        btnOtherDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OtherDetails.class);
                startActivity(intent);
            }
        });

    }
}