package edu.pdx.cs410j.haeyoon.apptbook_app;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.pdx.cs410J.ParserException;


public class CreateAppointment extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appt_layout);
    }


    public void onReturnHomeFromCreateAppt(View view) {

        finish();
    }

    /**
     * In this click,
     * 1_create appointment (after validating input)
     * 2_0_0_create appointment book
     * 2_0_1_save it in text file
     * or
     * 2_1_0_read in existing appointment from the appointment book
     * 2_1_1_add new appointment to the appointment book
     * 2_1_2_save all of them in text file
     *
     * @param view
     */
    public void createAppointmentClick(View view) {

        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTime = null;
        String beginMeridiem = null;
        String endDate = null;
        String endTime = null;
        String endMeridiem = null;


        EditText ownerET = findViewById(R.id.owner_name_edit_text);
        owner = String.valueOf(ownerET.getText());

        EditText descET = findViewById(R.id.description_edit_text);
        description = String.valueOf(descET.getText());

        EditText beginDateET = findViewById(R.id.begin_date_edit_text);
        beginDate = String.valueOf(beginDateET.getText());

        EditText beginTimeET = findViewById(R.id.begin_time_edit_text);
        beginTime = String.valueOf(beginTimeET.getText());

        EditText beginMerET = findViewById(R.id.begin_mer_edit_text);
        beginMeridiem = String.valueOf(beginMerET.getText());


        EditText endDateET = findViewById(R.id.end_date_edit_text);
        endDate = String.valueOf(endDateET.getText());

        EditText endTimeET = findViewById(R.id.end_time_edit_text);
        endTime = String.valueOf(endTimeET.getText());

        EditText endMerET = findViewById(R.id.end_mer_edit_text);
        endMeridiem = String.valueOf(endMerET.getText());


        //////////////////// Validate input ////////////////////////////

        if (owner == "") {
            Toast.makeText(this, "Provide name", Toast.LENGTH_LONG).show();
        }

        /*
        try {
            validate(owner, description, beginDate, beginTime, beginMeridiem,
                    endDate, endTime, endMeridiem);

        } catch (IllegalStateException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            validateDateAndTime(beginDate, beginTime, beginMeridiem);
            validateDateAndTime(endDate, endTime, endMeridiem);

        } catch (IllegalStateException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        */
        ////////////////// Check start time is before end time /////////////

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        formatter.setLenient(false);

        Date beginTimeDate = null;
        Date endTimeDate = null;


        /////////////////// Read existing appointments and add new one //////////////////////

        try {

            String fileName = owner + ".txt";
            File file = new File(this.getFilesDir(), fileName);

            Appointment newAppointment = new Appointment(description, beginDate, beginTime, beginMeridiem,
                    endDate, endTime, endMeridiem);

            if (file.exists()) {

                // read in existing appointment from text file
                TextParser textParser = new TextParser(file);
                AppointmentBook book = textParser.parse();

                // add new appointment to the appointment book
                book.addAppointment(newAppointment);

                // print existing + new appointments to ownerName.txt file
                TextDumper textDumper = new TextDumper(fileName);
                textDumper.dump(book);


            } else {

                // add new appointment to the new appointment book
                AppointmentBook book = new AppointmentBook(owner);
                book.addAppointment(newAppointment);

                // print new appointment to ownerName.txt file
                TextDumper textDumper = new TextDumper(fileName);
                textDumper.dump(book);

            }

            Toast.makeText(this, "Saved to" + getFilesDir() + "/", Toast.LENGTH_LONG).show();

            ownerET.getText().clear();
            descET.getText().clear();
            beginDateET.getText().clear();
            beginTimeET.getText().clear();
            beginMerET.getText().clear();
            endDateET.getText().clear();
            endTimeET.getText().clear();
            endMerET.getText().clear();

        } catch (FileNotFoundException ex){
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (ParserException ex) {
            ex.printStackTrace();

        }


    }

    public void validateDateAndTime(String Date, String Time, String Meridiem) {
        String pattern = "MM/dd/yyyy hh:mm a";
        String TimeString = Date + " " + Time + " " + Meridiem;

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);

        try{
            Date DateTime = formatter.parse(TimeString);

        } catch (ParseException e) {
            Toast.makeText(this, "Malformatted date and time: " + TimeString,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void validate(String owner, String description,
                                 String beginDate, String beginTime, String beginMeridiem,
                                 String endDate, String endTime, String endMeridiem) {

        if (owner == null || owner.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

        if (description == null || description.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

        if (beginDate == null || beginDate.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

        if (endDate == null || endDate.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

        if (beginTime == null || beginTime.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

        if (endTime == null || endTime.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

        if (beginMeridiem == null || beginMeridiem.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

        if (endMeridiem == null || endMeridiem.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }
    }

}
