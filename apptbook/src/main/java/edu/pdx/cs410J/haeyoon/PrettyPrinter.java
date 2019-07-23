package edu.pdx.cs410J.haeyoon;


import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * This class dumps the contents of Appointment Book
 * to a <code>text file</code> in "pretty" way.
 */
public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

    private AppointmentBook book;
    private File fop;
    private String fileName;

    /**
     * Create a new <code>Text file</code> that dumps contents of
     * an appointment book to a given file.
     */
    public PrettyPrinter(String s) {
        this.book = null;
        this.fop = null;
        this.fileName = s;

    }


    @Override
    public void dump(AppointmentBook book) throws IOException {

        this.book = book;

        // Wrap a PrinterWriter around System.err
        PrintWriter err = new PrintWriter(System.err, true);

        if (this.fileName.equals("-")) {

            System.out.print("Owner: " + this.book.getOwnerName().trim());
            System.out.print("\n\n");

            ArrayList<Appointment> apptList = new ArrayList<>(book.getAppointments());

            for (Appointment appointment : apptList) {

                System.out.print(appointment.getDescription().trim() + " ");

                System.out.print("from ");

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                String beginTimeString = df.format(appointment.getBeginTime());
                System.out.print(beginTimeString);

                System.out.print(" until ");

                DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                String endTimeString = df1.format(appointment.getEndTime());
                System.out.print(endTimeString);

                System.out.print(" for ");

                long diffInMillies = Math.abs(appointment.getEndTime().getTime() - appointment.getBeginTime().getTime());
                long duration = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
                System.out.print(duration);

                System.out.print(" minutes");

                System.out.print("\n");
            }

        } else {

            try {

                Writer writer = new FileWriter(this.fileName);

                // Write owner name of appointment book at the top
                writer.write("Owner: ");
                writer.write(this.book.getOwnerName().trim());
                writer.write("\n\n");

                // Write appointments in appointment book to the file
                ArrayList<Appointment> apptList = new ArrayList<>(book.getAppointments());

                for (Appointment appointment : apptList) {

                    writer.write(appointment.getDescription().trim() + " ");

                    writer.write("from ");

                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    String beginTimeString = df.format(appointment.getBeginTime());
                    writer.write(beginTimeString);

                    writer.write(" until ");

                    DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    String endTimeString = df1.format(appointment.getEndTime());
                    writer.write(endTimeString);

                    writer.write(" for ");

                    long diffInMillies = Math.abs(appointment.getEndTime().getTime() - appointment.getBeginTime().getTime());
                    long duration = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    writer.write(String.valueOf(duration));

                    writer.write(" minutes");

                    writer.write("\n");

                }

                writer.write("\n");

                // All done
                writer.flush();
                writer.close();

            } catch (IOException ex) {
                err.println("** " + ex);
            }
        }
    }
}
