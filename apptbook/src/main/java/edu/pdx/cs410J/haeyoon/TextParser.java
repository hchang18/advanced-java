package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.StringTokenizer;

import static edu.pdx.cs410J.haeyoon.Project2.usage;

/**
 * This class creates a <code> AppointmentBook</code> from the contents of an
 * text file.
 */

public class TextParser implements AppointmentBookParser{

    private AppointmentBook book;    // The appointment book we are creating
    private File apptFile;

    /**
     * Create an <code> TextParser </code> that creates a <code>AppointmentBook</code>
     * from a file of a given name
     */
    public TextParser(File s) throws IOException {
        this.book = new AppointmentBook(null);
        this.apptFile = s;
    }

    @Override
    /**
     * Parses the source and from it create a <code>AppointmentBook</code>
     */
    public AppointmentBook parse() throws ParserException {

        // parse the text file
        BufferedReader in = null;

        try {

            in = new BufferedReader(new FileReader(this.apptFile));
            StringTokenizer st;

            while (true) {

                String line = in.readLine();
                if (line == null) {
                    break;
                }

                st = new StringTokenizer(line);
                Project2 proj2 = new Project2();
                proj2.setOwner(st.nextToken(","));

                if (this.book.getOwnerName() == null) {
                    System.out.println("book owner name is: " + proj2.getOwner());
                    this.book = new AppointmentBook(proj2.getOwner());

                } else {
                    if (this.book.getOwnerName() == proj2.getOwner()) {
                    } else {
                        System.err.println("This book belongs to: " + this.book.getOwnerName());
                        System.err.println("Appointment to be added is for " + proj2.getOwner());
                        System.exit(1);
                    }
                }


                for (int i = 0; st.hasMoreTokens(); i++) {

                    if (proj2.getDescription() == null) {
                        proj2.setDescription(st.nextToken(","));

                    } else if (proj2.getBeginDate() == null) {
                        proj2.setBeginDate(st.nextToken(","));

                    } else if (proj2.getBeginTime() == null) {
                        proj2.setBeginTime(st.nextToken(","));

                    } else if (proj2.getEndDate() == null) {
                        proj2.setEndDate(st.nextToken(","));

                    } else if (proj2.getEndTime() == null) {
                        proj2.setEndTime(st.nextToken(","));

                    } else {
                        usage("Spurious command line: " + st.nextToken(","));
                    }
                }

                try {
                    proj2.validate();
                } catch (IllegalStateException ex) {
                    usage(ex.getMessage());
                }

                try {
                    proj2.validateDateAndTime(proj2.getBeginDate(), proj2.getBeginTime());
                    proj2.validateDateAndTime(proj2.getEndDate(), proj2.getEndTime());
                } catch (IllegalStateException ex) {
                    usage(ex.getMessage());
                }

                Appointment appointment = new Appointment(proj2.getDescription(), proj2.getBeginDate(), proj2.getBeginTime(), proj2.getEndDate(), proj2.getEndTime());
                System.out.println(appointment);

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
