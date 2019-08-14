package edu.pdx.cs410J.haeyoon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateAppointment extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appt_layout);
    }

    public void onClickCreateApptButton(View view) {

        /////////////////////// validate all the inputs are provided /////////////////////////

        EditText ownerET = findViewById(R.id.owner_edit_text);
        String owner = String.valueOf(ownerET.getText());

        try {
            validate(owner);
        } catch (IllegalStateException e){
            Toast.makeText(this, e.getMessage() + ": name", Toast.LENGTH_LONG).show();

        }

        EditText descriptionET = findViewById(R.id.description_edit_text);
        String description = String.valueOf(descriptionET.getText());

        try {
            validate(description);
        } catch (IllegalStateException e){
            Toast.makeText(this, e.getMessage() + ": description", Toast.LENGTH_LONG).show();
        }

        EditText beginTimeET = findViewById(R.id.begin_time_edit_text);
        String beginTime = String.valueOf(beginTimeET.getText());
        Date beginTimeDate = null;

        try {
            validate(beginTime);

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            formatter.setLenient(false);
            beginTimeDate = formatter.parse(beginTime);


        } catch (IllegalStateException e){
            Toast.makeText(this, e.getMessage() + ": begin time", Toast.LENGTH_LONG).show();

        } catch (ParseException e) {
            Toast.makeText(this, "Bad date and time: " + beginTime, Toast.LENGTH_LONG).show();

        }

        EditText endTimeET = findViewById(R.id.end_time_edit_text);
        String endTime = String.valueOf(endTimeET.getText());
        Date endTimeDate = null;

        try {
            validate(beginTime);

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            formatter.setLenient(false);
            endTimeDate = formatter.parse(endTime);


        } catch (IllegalStateException e){
            Toast.makeText(this, e.getMessage() + ": end time", Toast.LENGTH_LONG).show();

        } catch (ParseException e) {
            Toast.makeText(this, "Bad date and time: " + endTime, Toast.LENGTH_LONG).show();

        }

        //////////////////////// validate if begin time comes before end time ////////////////////

        try {
            if (beginTimeDate.after(endTimeDate)) {

                Toast.makeText(this, "Appointment end time is before start time", Toast.LENGTH_LONG).show();

            }
        } catch (NullPointerException e) {

            Toast.makeText(this, "Please provide Numbers", Toast.LENGTH_LONG).show();
        }

    }

    private void validate(String s) {

        if (s == null || s.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

    }
}
