package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.StringTokenizer;

import static edu.pdx.cs410J.haeyoon.Project3.usage;

/**
 * This class creates a <code> AppointmentBook</code> from the contents of an
 * text file.
 */

public class TextParser implements AppointmentBookParser{

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

    @Override
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
                Project3 proj = new Project3();
                proj.setOwner(st.nextToken(","));

                if (this.book.getOwnerName() == null) {
                    //System.out.println("book owner name is: " + proj2.getOwner());
                    this.book = new AppointmentBook(proj.getOwner());

                } else {
                    if (this.book.getOwnerName().equals(proj.getOwner())) {
                    } else {
                        System.err.println("This book belongs to: " + this.book.getOwnerName());
                        System.err.println("Appointment to be added is for " + proj.getOwner());
                        System.exit(1);
                    }
                }


                for (int i = 0; st.hasMoreTokens(); i++) {

                    if (proj.getDescription() == null) {
                        proj.setDescription(st.nextToken(",").trim());

                    } else if (proj.getBeginDate() == null) {
                        proj.setBeginDate(st.nextToken(",").trim());

                    } else if (proj.getBeginTime() == null) {
                        proj.setBeginTime(st.nextToken(",").trim());

                    } else if (proj.getBeginMeridiem() == null) {
                        proj.setBeginMeridiem(st.nextToken(",").trim());

                    } else if (proj.getEndDate() == null) {
                        proj.setEndDate(st.nextToken(",").trim());

                    } else if (proj.getEndTime() == null) {
                        proj.setEndTime(st.nextToken(",").trim());

                    } else if (proj.getEndMeridiem() == null) {
                        proj.setEndMeridiem(st.nextToken(",").trim());

                    } else {
                        usage("Spurious command line: " + st.nextToken(","));
                    }
                }

                try {
                    proj.validate();
                } catch (IllegalStateException ex) {
                    //System.err.println(ex.getMessage());
                    //Activate this line above if want to print out msg from validate()
                    System.err.println("** Malformatted text file: " + this.fip);
                    System.exit(1);
                }

                try {
                    proj.validateDateAndTime(proj.getBeginDate(), proj.getBeginTime(), proj.getBeginMeridiem());
                    proj.validateDateAndTime(proj.getEndDate(), proj.getEndTime(), proj.getEndMeridiem());
                } catch (IllegalStateException ex) {
                    System.err.println("** Malformatted text file in date and time fields: " + this.fip);
                    System.exit(1);
                }

                Appointment appointment = new Appointment(proj.getDescription(), proj.getBeginDate(), proj.getBeginTime(), proj.getBeginMeridiem(),
                        proj.getEndDate(), proj.getEndTime(), proj.getEndMeridiem());
                //System.out.println(appointment);

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
