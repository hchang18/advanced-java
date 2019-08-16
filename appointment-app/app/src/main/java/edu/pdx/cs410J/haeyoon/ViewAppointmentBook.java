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

        if (file.exists()){
            // load it (pretty-print)

            try {
                fis = openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text = "";

                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }

                viewResult.setText(sb.toString());

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
