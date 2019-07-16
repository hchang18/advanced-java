package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.AppointmentBookParser;

import java.io.*;
import java.util.StringTokenizer;

import static edu.pdx.cs410J.haeyoon.Project2.usage;


public class TextParser implements AppointmentBookParser{

    private final File file;

    public TextParser(String s) {
        // change string to file
        File file = new File(s);
        this.file = file;
    }

    @Override
    public AppointmentBook parse() {

        // parse the text file
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(this.file));

            String line = in.readLine();
            StringTokenizer st;
            Project2 proj2 = new Project2();

            while(line != null){
                st = new StringTokenizer(line);
                for (int i = 0; st.hasMoreTokens(); i++) {

                    if (proj2.getOwner() == null) {
                        proj2.setOwner(st.nextToken());

                    } else if (proj2.getDescription() == null) {
                        proj2.setDescription(st.nextToken());

                    } else if (proj2.getBeginDate() == null) {
                        proj2.setBeginDate(st.nextToken());

                    } else if (proj2.getBeginTime() == null) {
                        proj2.setBeginTime(st.nextToken());

                    } else if (proj2.getEndDate() == null) {
                        proj2.setEndDate(st.nextToken());

                    } else if (proj2.getEndTime() == null) {
                        proj2.setEndTime(st.nextToken());

                    } else {
                        usage("Spurious command line: " + st.nextToken());
                    }
                }

                proj2.validate();
                proj2.validateDateAndTime(proj2.getBeginDate(), proj2.getBeginTime());
                proj2.validateDateAndTime(proj2.getEndDate(), proj2.getEndTime());

                // Create Appointment
                Appointment newAppointment = new Appointment(proj2.getDescription(), proj2.getBeginDate(), proj2.getBeginTime(), proj2.getEndDate(), proj2.getEndTime());
                // Create Appointment Book
                AppointmentBook newAppointmentBook = new AppointmentBook(proj2.getOwner());
                // Add appointment to Appointment Book
                newAppointmentBook.addAppointment(newAppointment);

            }


        } catch (IOException ex){
            ex.printStackTrace();
            System.exit(1);
        }



        return appointmentBook;
    }

    /*
    private final File file;

    public TextParser(File file){
        this.file = file;
    }

    @Override
    public AppointmentBook parse (String s) {

        File file = new File(s);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        AppointmentBook ApptBook;

        while (true) {
            try {
                // Read a line from the file
                String line = br.readLine();

                if (line == null) {
                    ApptBook = new AppointmentBook(null);
                    break;
                } else {
                    Project2 proj2 = new Project2();

                    //


                }

            } catch (IOException ex) {
                System.err.println("** " + ex);
                System.exit(1);
            }

        }


        return ApptBook;
    }

     */
}
