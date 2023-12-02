package com.onlinelabreport.onlinelabreport;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PasswordDone extends AppCompatActivity {

    Button BtnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_done);

        BtnDone = findViewById(R.id.passwordDoneBtn);

        BtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordDone.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}