package edu.pdx.cs410j.haeyoon.apptbook_app;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.pdx.cs410J.AbstractAppointment;


public class CreateAppointment extends Activity {

    private static final String FILE_NAME = "save.txt";
    private static Map<String, AppointmentBook> appointmentBooks =
            new HashMap<>();

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

        EditText ownerET = (EditText) findViewById(R.id.owner_name_edit_text);
        String owner = String.valueOf(ownerET.getText());

        EditText descET = (EditText) findViewById(R.id.description_edit_text);
        String description = String.valueOf(descET.getText());

        EditText beginDateET = (EditText) findViewById(R.id.begin_date_edit_text);
        String beginDate = String.valueOf(beginDateET.getText());

        EditText beginTimeET = (EditText) findViewById(R.id.begin_time_edit_text);
        String beginTime = String.valueOf(beginTimeET.getText());

        EditText beginMerET = (EditText) findViewById(R.id.begin_mer_edit_text);
        String beginMeridiem = String.valueOf(beginMerET.getText());


        EditText endDateET = (EditText) findViewById(R.id.end_date_edit_text);
        String endDate = String.valueOf(endDateET.getText());

        EditText endTimeET = (EditText) findViewById(R.id.end_time_edit_text);
        String endTime = String.valueOf(endTimeET.getText());

        EditText endMerET = (EditText) findViewById(R.id.end_mer_edit_text);
        String endMeridiem = String.valueOf(endMerET.getText());


        //////////////////// Validate input ////////////////////////////

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

        /////////////////// Read existing appointments and add new one //////////////////////

        File fop = new File(FILE_NAME);

        try {

            TextParser parser = new TextParser(fop);
            appointmentBooks = parser.parse();

            if (appointmentBooks.containsKey(owner)) {
            // when owner already has appointment book

                AppointmentBook book = appointmentBooks.get(owner);

                Appointment appointment = new Appointment(description, beginDate, beginTime,
                        beginMeridiem, endDate, endTime, endMeridiem);
                book.addAppointment(appointment);

            } else {
            // when owner do not have appointment book

                AppointmentBook book = new AppointmentBook(owner);
                appointmentBooks.put(owner, book);

                Appointment appointment = new Appointment(description, beginDate, beginTime,
                        beginMeridiem, endDate, endTime, endMeridiem);
                book.addAppointment(appointment);
            }

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

        } catch (ParseException ex) {
            ex.printStackTrace();

        }

        /////////////////// Save all appointments in text file ///////////////////////

        try{
            TextDumper dumper = new TextDumper(FILE_NAME);
            dumper.dump(appointmentBooks);

        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    private void validateDateAndTime(String Date, String Time, String Meridiem) {
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

    private void validate(String owner, String description,
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
