package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This class dumps the contents of Appointment Book
 * to a <code>text file</code>.
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook>{

    private AppointmentBook book;    // The appointment book passed into
    private File fop;

    /**
     * Create a new <code>Text file</code> that dumps contents of
     * an appointment book to a given file.
     */
    public TextDumper(File file) throws IOException {
        this.book = null;
        this.fop = file;
    }

    @Override
    public void dump(AppointmentBook book) throws IOException {

        this.book = book;

        // Wrap a PrinterWriter around System.err
        PrintWriter err = new PrintWriter(System.err, true);

        try{

            Writer writer = new FileWriter(this.fop);

            // Write appointments in appointment book to the file
            ArrayList<Appointment> apptList = new ArrayList<>(book.getAppointments());

            for(Appointment appointment: apptList){

                writer.write(this.book.getOwnerName().trim() + ", ");

                writer.write(appointment.getDescription().trim() + ", ");

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                String beginTimeString = df.format(appointment.getBeginTime());
                writer.write(beginTimeString);

                writer.write(", ");

                String endTimeString = df.format(appointment.getEndTime());
                writer.write(endTimeString);

                writer.write("\n");

            }

            // All done
            writer.flush();
            writer.close();

        } catch (IOException ex){
            err.println("** " + ex);
        }

    }
}

