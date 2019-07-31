package edu.pdx.cs410J.haeyoon;

import java.io.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST. Project 4 has all the functionality
 * of the previous projects.
 *
 */

public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    /////////////// Instance Fields //////////

    private static String owner = null;
    private static String description = null;
    private static String beginDate = null;
    private static String endDate = null;
    private static String beginTime = null;
    private static String beginMeridiem = null;
    private static String endTime = null;
    private static String endMeridiem = null;

    private static String searchOwner = null;
    private static String searchBeginDate = null;
    private static String searchBeginTime = null;
    private static String searchBeginMeridiem = null;
    private static String searchEndDate = null;
    private static String searchEndTime = null;
    private static String searchEndMeridiem = null;

    private static String hostName = null;
    private static String portString = null;
    private static int port = -1;

    private static boolean readme = false;
    private static boolean printFlag = false;
    private static boolean hostFlag = false;
    private static boolean portFlag = false;
    private static boolean searchFlag = false;


    private String prettyFile = null;

    private AppointmentBook book = null;


    /////////////// Instance Methods //////////


    /**
     * The main class that parses the command line and communicates with the
     * Appointment Book server using REST. Project 4 has all the functionality
     * of the previous projects.
     */
    public static void main(String... args) {

        // Check if any arguments are passed in
        if (args.length == 0) {
            System.err.println("Missing command line arguments");
            //System.exit(1);
        }

        // Parse the command line
        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-host")) {
                hostFlag = true;
                if (++i >= args.length) {
                    usage("Missing hostname");
                }
                hostName = args[i];

            } else if (args[i].equals("-port")) {
                portFlag = true;
                if (++i >= args.length) {
                    usage("Missing port");
                }

                portString = args[i];
                try {
                    port = Integer.parseInt(portString);
                } catch (NumberFormatException ex) {
                    usage("Port \"" + portString + "\" must be an integer");
                    return;
                }

            } else if (args[i].equals("-print")) {
                printFlag = true;

            } else if (args[i].equals("-README")) {
                readme = true;
                break;

            } else if (args[i].equals("-search")){
                searchFlag = true;

                if (++i >= args.length) {
                    System.err.println("Missing arguments to use in search");
                } else {
                    if (searchOwner == null) searchOwner = args[i];
                }

                if (++i >= args.length) {
                    System.err.println("Missing arguments to use in search");
                } else {
                    if (searchBeginDate == null) searchBeginDate = args[i];
                }

                if (++i >= args.length) {
                    System.err.println("Missing arguments to use in search");
                } else {
                    if (searchBeginTime == null) searchBeginTime = args[i];
                }

                if (++i >= args.length) {
                    System.err.println("Missing arguments to use in search");
                } else {
                    if (searchBeginMeridiem == null) searchBeginMeridiem = args[i];
                }

                if (++i >= args.length) {
                    System.err.println("Missing arguments to use in search");
                } else {
                    if (searchEndDate == null) searchEndDate = args[i];
                }

                if (++i >= args.length) {
                    System.err.println("Missing arguments to use in search");
                } else {
                    if (searchEndTime == null) searchEndTime = args[i];
                }

                if (++i >= args.length) {
                    System.err.println("Missing arguments to use in search");
                } else {
                    if (searchEndMeridiem == null) searchEndMeridiem = args[i];
                }

                break;



            } else if (args[i].startsWith("-")) {
                usage("Unknown command line option");

            } else if (owner == null) {
                owner = args[i];

            } else if (description == null) {
                description = args[i];

            } else if (beginDate == null) {
                beginDate = args[i];

            } else if (beginTime == null) {
                beginTime = args[i];

            } else if (beginMeridiem == null) {
                beginMeridiem = args[i];

            } else if (endDate == null) {
                endDate = args[i];

            } else if (endTime == null) {
                endTime = args[i];

            } else if (endMeridiem == null) {
                endMeridiem = args[i];

            } else {
                usage("Extraneous command line argument(s): " + args[i]);

            }

        }

        if (readme == true) {
            README();
            //System.exit(0);
        }


        /*
         * Depending on the scenario, required arguments vary. In each scenario,
         * command line arguments validation should vary too.
         */

        if (searchFlag == true) {
            // Scenario 1: search option is on!

            try {
                if (searchOwner == null || searchOwner.trim().equals("")) {
                    throw new IllegalStateException("Missing argument(s): owner");
                }

            } catch (IllegalStateException ex) {
                System.err.println(ex.getMessage());

            }

            try {
                validateDateAndTime(searchBeginDate, searchBeginTime, searchBeginMeridiem);
                validateDateAndTime(searchEndDate, searchEndTime, searchEndMeridiem);

            } catch (IllegalStateException ex) {
                System.err.println(ex.getMessage());
            }

        } else {

            if (owner == null) {
                // Scenario 2: pretty print all owners' appointments

            } else if (owner != null && description == null) {
                // Scenario 3: pretty print all appointment in an appointment book

            } else {
                // Scenario 4: Add an appointment to the server

                try {
                    validate();

                } catch (IllegalStateException ex) {
                    usage(ex.getMessage());
                    //System.exit(1);

                }

                try {
                    validateDateAndTime(beginDate, beginTime, beginMeridiem);
                    validateDateAndTime(endDate, endTime, endMeridiem);

                } catch (IllegalStateException ex) {
                    usage(ex.getMessage());
                    //System.exit(1);
                }

            }
        }


        /*
         * Working with REST API
         *
         */

        AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);

        String beginTimeString = beginDate + " " + beginTime + " " + beginMeridiem;
        String endTimeString = endDate + " " + endTime + " " + endMeridiem;

        String message = null;

        if (searchFlag == true) {

            try {

                String srchBeginTimeString = searchBeginDate + " " + searchBeginTime + " " + searchBeginMeridiem;
                String srchEndTimeString = searchEndDate + " " + searchEndTime + " " + searchEndMeridiem;

                message = client.getAppointments(searchOwner, srchBeginTimeString, srchEndTimeString);

            } catch (IOException ex) {
                error("While contacting server: " + ex);
                return;
            }

            System.out.println(message);
            System.exit(0);

        } else {
            try {
                if (owner == null) {
                    // Print all owner - AppointmentBook pairs

                    //Map<String, String> dictionary = client.getAllDictionaryEntries();
                    //StringWriter sw = new StringWriter();
                    //Messages.formatDictionaryEntries(new PrintWriter(sw, true), dictionary);
                    //message = sw.toString();

                    message = client.getAllAppointmentEntries();

                } else if (description == null) {
                    // Print all appointment entries belong to the owner
                    message = client.getAppointments(owner);
                    //message = Messages.formatDictionaryEntry(owner, client.getAppointments(owner));

                } else {
                    // Create new appointment
                    message = client.addAppointment(owner, description, beginTimeString, endTimeString);
                }

            } catch (IOException ex) {
                error("While contacting server: " + ex);
                return;
            }

            System.out.println(message);
            System.exit(0);

        }


    }

    private static void validateDateAndTime(String Date, String Time, String Meridiem) {
        String pattern = "MM/dd/yyyy hh:mm a";
        String TimeString = Date + " " + Time + " " + Meridiem;

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);

        try{
            Date DateTime = formatter.parse(TimeString);

        } catch (ParseException e) {
            System.err.println("** Malformatted date and time: " + TimeString);
            //System.exit(1);
        }
    }

    private static void validate() {
        if (owner == null || owner.trim().equals("")){
            throw new IllegalStateException("Missing argument(s)");
        }

        if (description == null || description.trim().equals("")){
            throw new IllegalStateException("Missing argument(s)");
        }

        if (beginDate == null || beginDate.trim().equals("")){
            throw new IllegalStateException("Missing argument(s)");
        }

        if (endDate == null || endDate.trim().equals("")){
            throw new IllegalStateException("Missing argument(s)");
        }

        if (beginTime == null || beginTime.trim().equals("")){
            throw new IllegalStateException("Missing argument(s)");
        }

        if (endTime == null || endTime.trim().equals("")){
            throw new IllegalStateException("Missing argument(s)");
        }

        if (beginMeridiem == null || beginMeridiem.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }

        if (endMeridiem == null || endMeridiem.trim().equals("")) {
            throw new IllegalStateException("Missing argument(s)");
        }
    }


    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        //System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param s An error message to print
     */
    public static void usage(String s){
        System.err.println("\n** " + s + "\n");
        System.err.println("usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>");
        System.err.println("  args are (in this order):");
        System.err.println("    owner                  The person who owns the appt book");
        System.err.println("    description            A description of the appointment");
        System.err.println("    beginTime              When the appt begins");
        System.err.println("    endTime                When the appt ends");
        System.err.println("  options are (options may appear in any order):");
        System.err.println("    -host hostname         Host computer on which the server runs");
        System.err.println("    -port port             Port on which the server is listening");
        System.err.println("    -search                Appointments should be searched for");
        System.err.println("    -print                 Prints a description of the new appointment");
        System.err.println("    -README                Prints a README for this project and exits");
        System.err.println("");
        //System.exit(1);
    }

    private static void README(){
        System.out.println("The main class that parses the command line and communicates with the");
        System.out.println("Appointment Book server using REST. Project 4 has all the functionality");
        System.out.println("of the previous projects.");
        //System.exit(0);
    }
}
