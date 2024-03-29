package edu.pdx.cs410j.haeyoon.apptbook_app;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.StringTokenizer;


/**
 * This class creates a <code> AppointmentBook</code> from the contents of an
 * text file.
 */

public class TextParser {

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
                String beginDate = null;
                String beginTime = null;
                String beginMeridiem = null;
                String endDate = null;
                String endTime = null;
                String endMeridiem = null;


                for (int i = 0; st.hasMoreTokens(); i++) {

                    if (description == null) {
                        description = st.nextToken(",").trim();

                    } else if (beginDate == null) {
                        beginDate = st.nextToken(",").trim();

                    } else if (beginTime == null) {
                        beginTime = st.nextToken(", ").trim();

                    } else if (beginMeridiem == null) {
                        beginMeridiem = st.nextToken(", ").trim();

                    } else if (endDate == null) {
                        endDate = st.nextToken(", ").trim();

                    } else if (endTime == null) {
                        endTime = st.nextToken(", ").trim();

                    } else if (endMeridiem == null) {
                        endMeridiem = st.nextToken(", ").trim();
                    }

                }

                Appointment appointment = new Appointment(description, beginDate, beginTime, beginMeridiem,
                        endDate, endTime, endMeridiem);

                this.book.addAppointment(appointment);
            }


        } catch (FileNotFoundException ex){

        } catch (IOException ex){
            ex.printStackTrace();
            System.exit(1);
        }


        return this.book;
    }

}
