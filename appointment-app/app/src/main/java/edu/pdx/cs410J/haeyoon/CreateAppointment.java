package edu.pdx.cs410J.haeyoon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import edu.pdx.cs410J.ParserException;

public class CreateAppointment extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appt_layout);
    }

    public void onClickSaveButton(View view) {

        /////////////////////// validate all the inputs are provided /////////////////////////

        EditText ownerET = findViewById(R.id.owner_edit_text);
        String owner = String.valueOf(ownerET.getText());

        try {
            validate(owner);
        } catch (IllegalStateException e){
            Toast.makeText(this, e.getMessage() + ": name", Toast.LENGTH_LONG).show();
            return;

        }

        EditText descriptionET = findViewById(R.id.description_edit_text);
        String description = String.valueOf(descriptionET.getText());

        try {
            validate(description);
        } catch (IllegalStateException e){
            Toast.makeText(this, e.getMessage() + ": description", Toast.LENGTH_LONG).show();
            return;
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
            return;

        } catch (ParseException e) {
            Toast.makeText(this, "Bad date and time: " + beginTime, Toast.LENGTH_LONG).show();
            return;
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
            return;

        } catch (ParseException e) {
            Toast.makeText(this, "Bad date and time: " + endTime, Toast.LENGTH_LONG).show();
            return;
        }

        //////////////////////// Validate if begin time comes before end time ////////////////////

        try {
            if (beginTimeDate.after(endTimeDate)) {

                Toast.makeText(this, "Appointment end time is before start time", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (NullPointerException e) {

            Toast.makeText(this, "Please provide Numbers", Toast.LENGTH_LONG).show();
            return;
        }



        /////////////////////// Read existing file (if any) and add new appointment ///////////////

        // create new appointment and new book

        Appointment newAppointment = new Appointment(description, beginTime, endTime);
        AppointmentBook book = new AppointmentBook(owner);
        book.addAppointment(newAppointment);

        // instantiate necessary fields

        FileOutputStream fos = null;
        FileInputStream fis = null;

        String fileName = owner + ".txt";
        String filePath = getApplication().getFilesDir().getPath() + "/" + fileName;
        File file = new File(filePath);

        TextView prettyPrint = findViewById(R.id.result_to_show_textview);

        // if appointment owner already has appointment

        if (file.exists()) {

            // load it (parser)

            try {
                fis = openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringTokenizer st;

                while(true) {

                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }

                    st = new StringTokenizer(line);

                    String ownerString = st.nextToken(", ");
                    String descString = null;
                    String beginDateString = null;
                    String beginTimeString = null;
                    String beginMerString = null;
                    String endDateString = null;
                    String endTimeString = null;
                    String endMerString = null;


                    for (int i = 0; st.hasMoreTokens(); i++) {

                        if (descString == null) {
                            descString = st.nextToken(",").trim();

                        } else if (beginDateString == null) {
                            beginDateString = st.nextToken(",").trim();

                        } else if (beginTimeString == null) {
                            beginTimeString = st.nextToken(",").trim();

                        } else if (beginMerString == null) {
                            beginMerString = st.nextToken(",").trim();

                        } else if (endDateString == null) {
                            endDateString = st.nextToken(",").trim();

                        } else if (endTimeString == null) {
                            endTimeString = st.nextToken(",").trim();

                        } else if (endMerString == null) {
                            endMerString = st.nextToken(",").trim();

                        } else {
                            System.err.println("Spurious command line: " + st.nextToken(","));

                        }

                    }

                    String fullBeginTimeString = beginDateString + " " + beginTimeString + " " +
                            beginMerString;
                    String fullEndTimeString = endDateString + " " + endTimeString + " " +
                            endMerString;

                    Appointment appointment = new Appointment(descString, fullBeginTimeString, fullEndTimeString);
                    book.addAppointment(appointment);

                }

            Toast.makeText(this, "load successful", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // save appointment to text file (dumper)

            try {

                Toast.makeText(this, "dump to text file started", Toast.LENGTH_LONG).show();

                fos = openFileOutput(fileName, MODE_PRIVATE);

                ArrayList<Appointment> apptList = new ArrayList<>(book.getAppointments());

                for (Appointment appointment: apptList) {

                    fos.write((book.getOwnerName().trim() + ", ").getBytes());
                    fos.write((appointment.getDescription().trim()+", ").getBytes());

                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy, hh:mm, a");
                    String beginTimeString = df.format(appointment.getBeginTime());
                    fos.write(beginTimeString.getBytes());

                    fos.write(", ".getBytes());

                    DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy, hh:mm, a");
                    String endTimeString = df1.format(appointment.getEndTime());
                    fos.write(endTimeString.getBytes());

                    fos.write("\n".getBytes());

                }

                Toast.makeText(this, "save into text successful", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // load it (pretty-print)

            StringBuilder sb = new StringBuilder();

            String name = "Owner: " + book.getOwnerName().trim() + "\n";
            sb.append(name);

            ArrayList<Appointment> appointmentList
                    = new ArrayList<>(book.getAppointments());

            for (Appointment appointment: appointmentList) {

                String desc = appointment.getDescription().trim() + " ";

                sb.append(desc);

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                String beginTimeString = df.format(appointment.getBeginTime());
                sb.append("from " + beginTimeString);

                System.out.print(" until ");

                String endTimeString = df.format(appointment.getEndTime());
                sb.append(" until " + endTimeString);

                sb.append(" for ");

                long diffInMillies = Math.abs(appointment.getEndTime().getTime() - appointment.getBeginTime().getTime());
                long duration = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
                sb.append(duration);

                sb.append(" minutes");

                sb.append("\n");

            }

            prettyPrint.setText(sb.toString());


        } else {

            // save appointment to text file (dumper)

            try {

                fos = openFileOutput(fileName, MODE_PRIVATE);

                fos.write((book.getOwnerName().trim() + ", ").getBytes());
                fos.write((newAppointment.getDescription().trim()+", ").getBytes());

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy, hh:mm, a");
                String beginTimeString = df.format(newAppointment.getBeginTime());
                fos.write(beginTimeString.getBytes());

                fos.write(", ".getBytes());

                DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy, hh:mm, a");
                String endTimeString = df1.format(newAppointment.getEndTime());
                fos.write(endTimeString.getBytes());

                fos.write("\n".getBytes());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // load it (pretty-print)

            StringBuilder sb = new StringBuilder();

            String name = "Owner: " + book.getOwnerName().trim() + "\n";
            sb.append(name);

            ArrayList<Appointment> appointmentList
                    = new ArrayList<>(book.getAppointments());

            for (Appointment appointment: appointmentList) {

                String desc = appointment.getDescription().trim() + " ";

                sb.append(desc);

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                String beginTimeString = df.format(appointment.getBeginTime());
                sb.append("from " + beginTimeString);

                System.out.print(" until ");

                String endTimeString = df.format(appointment.getEndTime());
                sb.append(" until " + endTimeString);

                sb.append(" for ");

                long diffInMillies = Math.abs(appointment.getEndTime().getTime() - appointment.getBeginTime().getTime());
                long duration = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
                sb.append(duration);

                sb.append(" minutes");

                sb.append("\n");

            }

            prettyPrint.setText(sb.toString());

        }



    }

    private void validate(String s) {

        if (s == null || s.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

    }

}
