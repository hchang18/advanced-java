package edu.pdx.cs410J.haeyoon;

//import com.google.common.annotations.VisibleForTesting;

import java.io.PrintWriter;
import java.text.*;
import java.util.*;


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

    private static final PrintWriter out = new PrintWriter(System.out, true);
    private static final PrintWriter err = new PrintWriter(System.err, true);

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
     * The name of the text file to parse
     */
    private String textFileName = null;

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
     * Validate the state of this project
     *
     * @throws IllegalStateException if any state is incorrect or missing
     */
    public void validate(){

        if (owner == null){
            throw new IllegalStateException("Missing owner name");
        }

        if (description == null){
            throw new IllegalStateException("Missing description");
        }

        if (beginDate == null){
            throw new IllegalStateException("Missing begin date");
        }

        if (endDate == null){
            throw new IllegalStateException("Missing end date");
        }

        if (beginTime == null){
            throw new IllegalStateException("Missing begin time");
        }

        if (endTime == null){
            throw new IllegalStateException("Missing end time");
        }
    }


    /////////////// Main Program //////////

    /**
     * Prints usage information about this program
     */

    private static void usage(String s){
        err.println("\n** " + s + "\n");
        err.println("usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>");
        err.println("  args are (in this order):");
        err.println("    owner                  The person who owns the appt book");
        err.println("    description            A description of the appointment");
        err.println("    beginTime              When the appt begins (24-hour time)");
        err.println("    endTime                When the appt ends (24-hour time)");
        err.println("  options are (options may appear in any order):");
        err.println("    -textFile file         Where to read/write the appointment book");
        err.println("    -print                 Prints a description of the new appointment");
        err.println("    -README                Prints a README for this project and exits");
        err.println("    -comment comment   Info for the Grader");
        err.println("");
        err.println("Dates and times should be in the format: mm/dd/yyyy hh:mm");
        System.exit(1);
    }

    private static void README(){
        out.println("This Program optionally reads an appointment book from the contents of a text file");
        out.println("create a new appointment as specified on the command line, ");
        out.println("add the appointment to the appointment book");
        out.println("and then optionally writes the appointment book back to the text file.");
        System.exit(0);
    }

     /**
     * Optionally reads an AppointmentBook from the contents of a text file,
     * create a new Appointment as specified on the command line,
     * add the Appointment to the AppointmentBook,
     * and then optionally writes the AppointmentBook back to the text file.
     */
    public static void main(String[] args){
        Project2 project2 = new Project2();

        // Parse the command line
        for (int i = 0; i < args.length; i++){
            // Check for options first
            if(args[i].equals("-textFile")){
                if(++i >= args.length){
                    usage("No text file specified");
                }

                project2.setTextfile(args[i]);

                // check if the file name ends with .txt

            } else if(args[i].equals("-print")){
                project2.setPrint(true);

            } else if(args[i].equals("-README")){
                project2.setReadme(true);
                break;

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
            } else {}
        }

        if (project2.readme == true){
            README();
        }

        // Make sure that user entered enough information
        try{
            project2.validate();

        } catch(IllegalStateException ex){
            usage(ex.getMessage());
            return;
        }

        // Check if the date and time are in good format

        String pattern = "MM/dd/yyyy HH:mm";
        String beginTimeString = project2.getBeginDate() + " " + project2.getBeginTime();
        String endTimeString = project2.getEndDate() + " " + project2.getEndTime();

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);

        try {
            Date beginDateTime = formatter.parse(beginTimeString);

        } catch(ParseException e){
            err.println("** Malformatted begin date and time: " + beginTimeString);
            System.exit(1);
        }

        try {
            Date endDateTime = formatter.parse(endTimeString);

        } catch(ParseException e){
            err.println("** Malformatted end date and time: " + endTimeString);
            System.exit(1);
        }

    }


}
