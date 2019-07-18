package edu.pdx.cs410J.haeyoon;

//import com.google.common.annotations.VisibleForTesting;

import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This is for project 2 that creates appointment and add the
 * appointment to the appointment book. There are two ways that
 * user can add appointment. First, the user can specifies owner,
 * description, begin time, and end time in the command line to
 * create new appointment. Second, the user can enter text file name,
 * and the program will parse the text file and insert appointment(s)
 * to the appointment book accordingly.
 *
 * Style of the code below follows Submit.java example by Prof. David Whitlock
 *
 * @author Haeyoon Chang
 * @since July 2019
 */

public class Project2 {

    //private static final PrintWriter out = new PrintWriter(System.out, true);
    //private static final PrintWriter err = new PrintWriter(System.err, true);


    /////////////// Instance Fields //////////

    /**
     * The name of person who owns the appointment book
     */
    private String owner = null;

    /**
     * The description of the appointment
     */
    private String description = null;

    /**
     * The date when appointment begins (24-hour time)
     */
    private String beginDate = null;

    /**
     * The date when appointment ends (24-hour time)
     */
    private String endDate = null;

    /**
     * The time when appointment begins (24-hour time)
     */
    private String beginTime = null;

    /**
     * The time when appointment ends (24-hour time)
     */
    private String endTime = null;

    /**
     * Should the program print out README?
     */
    private boolean readme = false;

    /**
     * Should the program print out the last appointment added?
     */
    private boolean printFlag = false;

    /**
     * Should the program read/write to textfile?
     */
    private boolean textFileFlag = false;

    /**
     * The name of the text file to parse
     */
    private String textFileName = null;

    /**
     * The name of the text file in file format
     */
    private File file = null;

    /**
     * Appointment belong to project class
     */
    private AppointmentBook book = null;

    /////////////// Constructors //////////

    /**
     * Create a new <code>Project 2</code> program
     */
    public Project2(){

    }

    /////////////// Instance Methods //////////

    /**
     * Sets whether or not the program should print out README
     */
    private void setReadme(boolean readme){
        this.readme = readme;
    }

    /**
     * Set whether or not the program should print out the latest added appointment
     */
    private void setPrint(boolean print) {
        this.printFlag = print;
    }

    /**
     * Set whether or not the program should read/write to file
     */
    private void setTextFileFlag(boolean textFileFlag){
        this.textFileFlag = textFileFlag;
    }

    /**
     * set the name of the text file to parse
     */
    private void setTextfile(String tf){
        this.textFileName = tf;
    }

    /**
     * Sets the name of person who owns the appointment book
     */
    public void setOwner(String ownerName){
        this.owner = ownerName;
    }

    /**
     * Sets the description of the appointment
     */
    public void setDescription(String desc) {
        this.description = desc;
    }

    /**
     * Sets the date when appointment begins
     */
    public void setBeginDate(String beginDateString) {
        this.beginDate = beginDateString;
    }

    /**
     * Sets the date when appointment ends
     */
    public void setEndDate(String endDateString){
        this.endDate = endDateString;
    }

    /**
     * Sets the time when appointment begins (24-hour time)
     */
    public void setBeginTime(String beginTimeString) {
        this.beginTime = beginTimeString;
    }

    /**
     * Sets the time when appointment ends (24-hour time)
     */
    public void setEndTime(String endTimeString){
        this.endTime = endTimeString;
    }

    /**
     * Get the name of the textfile to read/write the appointment book
     */
    public String getTextFileName(){
        return this.textFileName;
    }

    /**
     * Get the name of the person who owns the appointment book
     */
    public String getOwner(){
        return this.owner;
    }

    /**
     * Get the description of the appointment
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Get the date when appointment begins
     */
    public String getBeginDate() {
        return this.beginDate;
    }

    /**
     * Get the date when appointment ends
     */
    public String getEndDate(){
        return this.endDate;
    }

     /**
     * Get the time when appointment begins
     */
    public String getBeginTime() {
        return this.beginTime;
    }

    /**
     * Get the time when appointment ends
     */
    public String getEndTime(){
        return this.endTime;
    }


    /**
     * Validate the arguments passed into the project
     *
     * @throws IllegalStateException if any state is incorrect or missing
     */
    public void validate(){

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
    }

    /**
     * Validate the Date and Time Format using DateFormat package
     * @param Date
     * @param Time
     */
    public void validateDateAndTime(String Date, String Time) {

        String pattern = "MM/dd/yyyy HH:mm";
        String TimeString = Date + " " + Time;

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);

        try {
            Date DateTime = formatter.parse(TimeString);

        } catch(ParseException e){
            System.err.println("** Malformatted date and time: " + TimeString);
            System.exit(1);
        }

    }



    /////////////// Main Program //////////

    /**
     * Prints usage information about this program
     */

    public static void usage(String s){
        System.err.println("\n** " + s + "\n");
        System.err.println("usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>");
        System.err.println("  args are (in this order):");
        System.err.println("    owner                  The person who owns the appt book");
        System.err.println("    description            A description of the appointment");
        System.err.println("    beginTime              When the appt begins (24-hour time)");
        System.err.println("    endTime                When the appt ends (24-hour time)");
        System.err.println("  options are (options may appear in any order):");
        System.err.println("    -textFile file         Where to read/write the appointment book");
        System.err.println("    -print                 Prints a description of the new appointment");
        System.err.println("    -README                Prints a README for this project and exits");
        System.err.println("");
        System.err.println("Dates and times should be in the format: mm/dd/yyyy hh:mm");
        System.err.println("Elements in each line of the text file should be in the following format: ");
        System.err.println("  owner, description, begin date (mm/dd/yyyy), begin time (hh:mm), end date (mm/dd/yyyy), end time (hh:mm)");
        System.exit(1);
    }

    private static void README(){
        System.out.println("This Program optionally reads an appointment book from the contents of a text file");
        System.out.println("create a new appointment as specified on the command line, ");
        System.out.println("add the appointment to the appointment book");
        System.out.println("and then optionally writes the appointment book back to the text file.");
        System.exit(0);
    }

     /**
     * Optionally reads an AppointmentBook from the contents of a text file,
     * create a new Appointment as specified on the command line,
     * add the Appointment to the AppointmentBook,
     * and then optionally writes the AppointmentBook back to the text file.
     */
    public static void main(String[] args){ Project2 project2 = new Project2();

        // Check if any arguments are passed in
        if(args.length == 0){
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        // Parse the command line
        for (int i = 0; i < args.length; i++) {

            // Check for options first
            if (args[i].equals("-textFile")) {
                project2.setTextFileFlag(true);

                if (++i >= args.length) {
                    usage("No text file specified");
                }

                // check if the file name ends with .txt
                if (args[i].endsWith(".txt")) {
                    project2.setTextfile(args[i]);

                } else {
                    System.err.println("This is not a text file: " + args[i]);
                    System.exit(1);
                }

                // check if the file exists in the directory
                try {

                    File file = new File(project2.getTextFileName());

                    if (!file.exists()) {
                        System.out.println("** Text file " + project2.getTextFileName() + " does not exist");
                    }

                } catch (NullPointerException ex) {

                }


            } else if (args[i].equals("-print")) {
                project2.setPrint(true);

            } else if (args[i].equals("-README")) {
                project2.setReadme(true);
                break;

            } else if (args[i].startsWith("-")){
                usage("Unknown command line option");

            } else if (project2.owner == null){
                project2.setOwner(args[i]);

            } else if (project2.description == null){
                project2.setDescription(args[i]);

            } else if (project2.beginDate == null){
                project2.setBeginDate(args[i]);

            } else if (project2.beginTime == null){
                project2.setBeginTime(args[i]);

            } else if (project2.endDate == null){
                project2.setEndDate(args[i]);

            } else if (project2.endTime == null){
                project2.setEndTime(args[i]);

            } else {
                usage("Extraneous command line argument(s): " + args[i]);
            }
        }

        if (project2.readme == true){
            README();
            System.exit(0);
        }

        // Make sure that user entered enough information
        try{
            project2.validate();

        } catch(IllegalStateException ex){
            usage(ex.getMessage());
            System.exit(1);
        }

        // Check if the date and time are in good format
        try {
            project2.validateDateAndTime(project2.getBeginDate(), project2.getBeginTime());
            project2.validateDateAndTime(project2.getEndDate(), project2.getEndTime());

        } catch(IllegalStateException ex){
            usage(ex.getMessage());
            System.exit(1);
        }

        /*
         * Create Appointment and Appointment Book with validated parameters
         * from command line arguments.
         */
        Appointment CLAppointment = new Appointment(project2.description, project2.beginDate, project2.beginTime, project2.endDate, project2.endTime);

        if(project2.printFlag){
            System.out.println(CLAppointment);
        }

        /*
        * When there is text file provided,
        * parse it, create appointments and create appointment book
        * update the appointment list with the most recently command line added appointment
        * and dump it into the text file
        */

        if(project2.textFileFlag == true) {

            project2.file = new File(project2.textFileName);

            try {
                TextParser parser = new TextParser(project2.file);
                project2.book = parser.parse();

            } catch (FileNotFoundException ex){
                System.err.println("** Could not find file: " + ex.getMessage());
                System.exit(1);

            } catch (IOException ex){
                System.err.println("** IOException during parsing: " + ex.getMessage());
                System.exit(1);

            } catch (ParserException ex){
                System.err.println("** Exception while parsing " + project2.textFileName + ": " + ex);
                System.exit(1);

            }

            // check the owner name from command line and the owner name from
            // appointment book generated by the text file are the same
            // if so, add command line appointment into the book.
            if(project2.book.getOwnerName() == null) {
                project2.book = new AppointmentBook(project2.owner);
                project2.book.addAppointment(CLAppointment);

            } else {

                if (project2.book.getOwnerName().equals(project2.owner)) {
                    project2.book.addAppointment(CLAppointment);

                } else {
                    System.err.println("The owner name given on the commandline: " + project2.owner);
                    System.err.println("and the owner name found on the text file: " + project2.book.getOwnerName());
                }
            }
            /*
             * Dump an appointment book into text file
             */
            try {
                TextDumper dumper = new TextDumper(project2.textFileName);
                dumper.dump(project2.book);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {

            project2.book = new AppointmentBook(project2.owner);
            project2.book.addAppointment(CLAppointment);

        }

        /* Print out appointment list from a appointment book

        System.out.println("Print out appointment list from an appointment book");
        ArrayList<Appointment> apptList = new ArrayList<Appointment>(project2.book.getAppointments());
        for (Appointment appt: apptList){
            System.out.println(appt);
        }
        */

    }

}

