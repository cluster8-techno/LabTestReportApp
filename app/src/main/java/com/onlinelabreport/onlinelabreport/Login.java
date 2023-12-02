package com.onlinelabreport.onlinelabreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    DataBaseHelper dBHelper;
    Button btlogin, btGoToSignup;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       dBHelper = new DataBaseHelper(this);
       etEmail = findViewById(R.id.editEmail);
       etPassword = findViewById(R.id.TextPassword);
       btlogin = findViewById(R.id.loginbtn);
       btGoToSignup = findViewById(R.id.goToRegisterButoon);

       btGoToSignup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Login.this, Signup.class);
               startActivity(intent);
           }
       });

       btlogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String email = etEmail.getText().toString();
               String password = etPassword.getText().toString();

               // Check if email or password is empty
               if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                   Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                   return;
               }

               // Check if the entered email is valid
               if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                   Toast.makeText(Login.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                   return;
               }

               boolean isLoggedIn = dBHelper.checkUser(email, password);
               if (isLoggedIn) {
                   Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(Login.this, HomeActivity.class);
                   startActivity(intent);


               } else {
                   Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
               }

           }
       });

    }
}