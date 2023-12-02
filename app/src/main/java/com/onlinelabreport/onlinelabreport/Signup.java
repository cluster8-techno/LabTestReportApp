package com.onlinelabreport.onlinelabreport;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    EditText etEmail, etPassword, etRepassword;
    Button btnRegister,btnGoToLogin;
    DataBaseHelper dbhelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEmail = findViewById(R.id.editEmailAddress);
        etPassword = findViewById(R.id.password);
        etRepassword = findViewById(R.id.conformPassword);
        btnRegister = findViewById(R.id.signupButoon);
        btnGoToLogin = findViewById(R.id.goToLoginButoon);

        dbhelper = new DataBaseHelper(this);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, rePassword;

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                rePassword = etRepassword.getText().toString();

                // Check if email, password, or rePassword is empty
                if (email.equals("") || password.equals("") || rePassword.equals("")) {
                    Toast.makeText(Signup.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the entered email is valid
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(Signup.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Check if the password meets the criteria (e.g., at least 8 characters, containing letters and numbers)
                    if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                        Toast.makeText(Signup.this, "Password must be at least 8 characters and contain letters and numbers", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.equals(rePassword)) {
                        if (dbhelper.checkUserEmail(email)) {
                            Toast.makeText(Signup.this, "User already exists", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Proceed with registration
                        boolean registeredSuccess = dbhelper.insertData(email, password);
                        if (registeredSuccess){
                            Intent intent = new Intent(Signup.this, PasswordDone.class);
                            startActivity(intent);

                        }else
                            Toast.makeText(Signup.this, "User SignUp Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}