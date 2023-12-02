package com.onlinelabreport.onlinelabreport;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class LabBooking extends AppCompatActivity {
    private EditText userNameEditText, userGenderEditText, userTestTypeEditText;
    private Button buttonSelectDate, buttonSelectTime, buttonBook;
    FloatingActionButton backBtn;
    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_booking);

        dbHelper = new DBHelper(this);

        userNameEditText = findViewById(R.id.editTextUserName);
        userGenderEditText = findViewById(R.id.editTextUserGender);
        userTestTypeEditText = findViewById(R.id.editTextUserTestType);
        buttonSelectDate = findViewById(R.id.buttonSelectDate);
        buttonSelectTime = findViewById(R.id.buttonSelectTime);
        buttonBook = findViewById(R.id.buttonBook);
        backBtn = findViewById(R.id.backBtn3);

        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        buttonSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookLab();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabBooking.this, LabAppointment.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedYear = year;
                        selectedMonth = month + 1; // Month is 0-based
                        selectedDay = dayOfMonth;
                        buttonSelectDate.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                    }
                },
                currentYear,
                currentMonth,
                currentDay
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;
                        buttonSelectTime.setText(selectedHour + ":" + selectedMinute);
                    }
                },
                currentHour,
                currentMinute,
                true
        );
        timePickerDialog.show();
    }

    private void bookLab() {
        String userName = userNameEditText.getText().toString();
        String userGender = userGenderEditText.getText().toString();
        String testType = userTestTypeEditText.getText().toString();


        if (!userName.isEmpty() && selectedYear != 0 && selectedMonth != 0 && selectedDay != 0
                && selectedHour != 0 && selectedMinute != 0 && userGender != null && !userGender.isEmpty()
                && testType != null && !testType.isEmpty()) {

            String date = selectedYear + "-" + selectedMonth + "-" + selectedDay;
            String time = selectedHour + ":" + selectedMinute;

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_USER_NAME, userName);
            values.put(DBHelper.COLUMN_USER_GENDER, userGender);
            values.put(DBHelper.COLUMN_TEST_TYPE, testType);
            values.put(DBHelper.COLUMN_DATE, date);
            values.put(DBHelper.COLUMN_TIME, time);

            long newRowId = db.insert(DBHelper.TABLE_BOOKINGS, null, values);

            if (newRowId != -1) {
                Intent intent = new Intent(LabBooking.this, AppointmentDone.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error booking lab", Toast.LENGTH_SHORT).show();
            }

            db.close();
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}


