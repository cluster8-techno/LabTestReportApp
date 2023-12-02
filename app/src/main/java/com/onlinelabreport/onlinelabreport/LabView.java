package com.onlinelabreport.onlinelabreport;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class LabView extends AppCompatActivity {
    private DBHelper dbHelper;
    private ListView bookingsListView;
    private FloatingActionButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_view);

        dbHelper = new DBHelper(this);
        bookingsListView = findViewById(R.id.listViewBookings);
        backBtn = findViewById(R.id.backHomeBtn2);

        displayBookings();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabView.this, LabAppointment.class);
                startActivity(intent);
            }
        });

        // Set up item click listener for deletion
        bookingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showConfirmationDialog(position);
            }
        });
    }

    private void displayBookings() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.COLUMN_ID,
                DBHelper.COLUMN_USER_NAME,
                DBHelper.COLUMN_USER_GENDER,
                DBHelper.COLUMN_TEST_TYPE,
                DBHelper.COLUMN_DATE,
                DBHelper.COLUMN_TIME
        };

        Cursor cursor = db.query(
                DBHelper.TABLE_BOOKINGS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<String> bookingsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String bookingInfo = "\nUser Name:  " + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_NAME)) +
                    "\nUser Gender:  " + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_GENDER)) +
                    "\nTest Type:  " + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TEST_TYPE)) +
                    "\nBooked Date:  " + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DATE)) +
                    "\nBooked Time:  " + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TIME)) + "\n\n";

            bookingsList.add(bookingInfo);
        }

        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookingsList);
        bookingsListView.setAdapter(adapter);
    }

    private void showConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to cancel this appointment?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBooking(position);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just close the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteBooking(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_BOOKINGS, null);
        cursor.moveToPosition(position);
        long idToDelete = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));

        int deletedRows = db.delete(DBHelper.TABLE_BOOKINGS, DBHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(idToDelete)});

        if (deletedRows > 0) {
            Intent intent = new Intent(LabView.this, AppointmentCancel.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error deleting booking", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
}

