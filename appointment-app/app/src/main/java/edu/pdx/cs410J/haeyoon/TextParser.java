package edu.pdx.cs410J.haeyoon;

import android.app.Activity;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.StringTokenizer;


/**
 * This class creates a <code> AppointmentBook</code> from the contents of an
 * text file.
 */

public class TextParser extends Activity {

    private AppointmentBook book;    // The appointment book we are creating
    private File fip;

    /**
     * Create an <code> TextParser </code> that creates a <code>AppointmentBook</code>
     * from a file of a given name
     */
    public TextParser(File s) throws IOException {
        this.book = new AppointmentBook(null);
        this.fip = s;
    }

    /**
     * Parses the source and from it create a <code>AppointmentBook</code>
     */
    public AppointmentBook parse() throws ParserException {

        // parse the text file
        BufferedReader in = null;

        try {

            in = new BufferedReader(new FileReader(this.fip));
            StringTokenizer st;

            while (true) {

                String line = in.readLine();
                if (line == null) {
                    break;
                }

                st = new StringTokenizer(line);

                String owner = st.nextToken(", ");
                String description = null;
                String beginTime = null;
                String endTime = null;


                for (int i = 0; st.hasMoreTokens(); i++) {

                    if (description == null) {
                        description = st.nextToken(",").trim();

                    } else if (beginTime == null) {
                        beginTime = st.nextToken(",").trim();

                    } else if (endTime == null) {
                        endTime = st.nextToken(",").trim();

                    }

                }

                Appointment appointment = new Appointment(description, beginTime, endTime);

                this.book.addAppointment(appointment);
            }


        } catch (FileNotFoundException ex){
            ex.printStackTrace();

        } catch (IOException ex){
            ex.printStackTrace();
        }


        return this.book;
    }

}

