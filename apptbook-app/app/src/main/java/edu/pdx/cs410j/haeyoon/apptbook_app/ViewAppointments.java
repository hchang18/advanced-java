package edu.pdx.cs410j.haeyoon.apptbook_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class ViewAppointments extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_apptbook_layout);
    }

    public void viewAppointmentBookClick(View view){

        EditText apptBookOwnerET = findViewById(R.id.view_appt_book_owner_name_edit_text);
        String owner = String.valueOf(apptBookOwnerET.getText());

        TextView listOfAppts = findViewById(R.id.view_appointments_book_list_of_appointments);

        String fileName = owner + ".txt";
        File file = new File(this.getFilesDir(), fileName);

        if (file.exists()) {

            // parse text file

            // pretty-print it to the

        } else {

            // print out that there are no

        }



    }


}
