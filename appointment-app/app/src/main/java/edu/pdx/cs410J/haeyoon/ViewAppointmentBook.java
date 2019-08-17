package edu.pdx.cs410J.haeyoon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class ViewAppointmentBook extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appt_book_layout);
    }


    public void onClickViewButton(View view) {

        ///////////////// validate all the inputs are provided ////////////

        EditText viewOwnerET = findViewById(R.id.view_owner_book_edit_text);
        String owner = String.valueOf(viewOwnerET.getText());


        try {
            validate(owner);
        } catch (IllegalStateException e){
            Toast.makeText(this, e.getMessage() + ": name", Toast.LENGTH_LONG).show();
            return;

        }

       /////////////////////// Read existing file (if any) and view the appointment book ///////////////

        TextView viewResult = findViewById(R.id.view_result_textview);

        FileInputStream fis = null;

        String fileName = owner + ".txt";
        String filePath = getApplication().getFilesDir().getPath() + "/" + fileName;
        File file = new File(filePath);

        AppointmentBook bookToView = new AppointmentBook(owner);

        if (file.exists()){

            // text file parser

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

                    String ownerString = st.nextToken(",").trim();
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
                            Toast.makeText(this, "Spurious command line: " + st.nextToken(","), Toast.LENGTH_LONG).show();

                        }

                    }

                    String fullBeginTimeString = beginDateString + " " + beginTimeString + " " +
                            beginMerString;
                    String fullEndTimeString = endDateString + " " + endTimeString + " " +
                            endMerString;

                    Appointment appointment = new Appointment(descString, fullBeginTimeString, fullEndTimeString);
                    bookToView.addAppointment(appointment);

                }

                //Toast.makeText(this, "load successful", Toast.LENGTH_LONG).show();

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

            // load it (pretty-print)

            StringBuilder sb = new StringBuilder();

            String name = "Owner: " + bookToView.getOwnerName().trim() + "\n";
            sb.append(name);

            ArrayList<Appointment> appointmentList
                    = new ArrayList<>(bookToView.getAppointments());

            for (Appointment appointment: appointmentList) {

                String desc = appointment.getDescription().trim() + " ";

                sb.append(desc);

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                String beginTimeString = df.format(appointment.getBeginTime());
                sb.append("from " + beginTimeString);

                String endTimeString = df.format(appointment.getEndTime());
                sb.append(" until " + endTimeString);

                sb.append(" for ");

                long diffInMillies = Math.abs(appointment.getEndTime().getTime() - appointment.getBeginTime().getTime());
                long duration = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
                sb.append(duration);

                sb.append(" minutes");

                sb.append("\n");

            }

            viewResult.setText(sb.toString());

        } else {
            // no appointment exists for this owner
            viewResult.setText("Owner has no appointment book: " + owner);

        }

    }

    private void validate(String s) {

        if (s == null || s.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

    }
}
