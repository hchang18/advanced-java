package edu.pdx.cs410j.haeyoon.apptbook_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createAppt = findViewById(R.id.create_appointment_button);
        Button searchAppt = findViewById(R.id.search_appointment_button);
        Button viewApptBook = findViewById(R.id.view_appointment_book_button);
        Button helpButton = findViewById(R.id.help_button);

    }

    public void onHelpClick(View view) {

        Intent getHelpScreenIntent = new Intent(this, Readme.class);

        startActivity(getHelpScreenIntent);

    }

    public void onCreateApptClick(View view) {

        Intent getCreateApptScreenIntent = new Intent(this, CreateAppointment.class);

        startActivity(getCreateApptScreenIntent);

    }

    public void onSearchApptClick(View view) {


    }

    public void onViewApptBookClick(View view) {

        Intent getViewApptBookScreenIntent = new Intent(this, ViewAppointments.class);

        startActivity(getViewApptBookScreenIntent);
    }
}
