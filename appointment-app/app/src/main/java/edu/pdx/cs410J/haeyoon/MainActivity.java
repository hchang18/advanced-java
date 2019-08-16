package edu.pdx.cs410J.haeyoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void CreateNewAppointment(View view) {

        Intent getCreateApptScreenIntent = new Intent (this, CreateAppointment.class);
        startActivity(getCreateApptScreenIntent);

    }

    public void GoToReadme(View view) {

        Intent getHelpScreenIntent = new Intent(this, Help.class);
        startActivity(getHelpScreenIntent);
    }

    public void SearchAppointment(View view) {

        Intent getSearchApptScreenIntent = new Intent(this, SearchAppointment.class);
        startActivity(getSearchApptScreenIntent);

    }

    public void ViewAppointmentBook(View view) {

        Intent getViewApptBookScreenIntent = new Intent(this, ViewAppointmentBook.class);
        startActivity(getViewApptBookScreenIntent);
    }
}
