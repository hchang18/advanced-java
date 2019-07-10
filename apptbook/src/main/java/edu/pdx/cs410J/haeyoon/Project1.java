package edu.pdx.cs410J.haeyoon;

//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  /**
   * Main program that parses the command line, create
   * <code>Appointment</code> and <code>AppointmentBook</code>,
   * and prints a description of the appointment to
   * standard out if -print flag is invoked.
   */
  public static void main(String[] args){

    String readMe = "Project 1 - Design an Appointment Book Application \n"
            + "Haeyoon Chang \n"
            + "In this program, xxx";
    /* Check if any arguments are passed in */
    if(args.length == 0){
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    /*
    If first argument is -README,
    print out project description and exit
    */
    if(args[0].equals("-README")){
      System.out.println(readMe);
      System.exit(0);
    }

    String owner = "";
    String description = "";
    String beginDate = "";
    String beginTime = "";
    String endDate = "";
    String endTime = "";

    /*
    If first argument is -print,
    check the second argument is -README ?
    print out proj desc : # of args should be 7
    */
    if(args[0].equals("-print")){

      if(args[1].equals("-README")){
        System.out.println(readMe);
        System.exit(0);
      }

      if(args.length < 7){
        System.err.println("Missing arguments");
        System.exit(1);
      } else if (args.length > 7){
        System.err.println("Extraneous arguments");
        System.exit(1);
      } else {
        // create Appointment and Appointment Book classes
        owner = args[1];
        description = args[2];
        beginDate = args[3];
        beginTime = args[4];
        endDate = args[5];
        endTime = args[6];
      }

    } else {

      if(args.length < 6){
        System.err.println("Missing arguments");
        System.exit(1);
      } else if (args.length > 6){
        System.err.println("Extraneous arguments");
        System.exit(1);
      } else {
        // create Appointment and Appointment Book classes
        owner = args[0];
        description = args[1];
        beginDate = args[2];
        beginTime = args[3];
        endDate = args[4];
        endTime = args[5];
      }

    }

    /* Validate that String owner is in good format */
    if(owner == null){
      throw new IllegalArgumentException("Owner cannot be null");
    }

    String ownerNoBlank = owner.replaceAll(" ", "");
    if(ownerNoBlank.equals("")){
      System.err.println("Owner cannot be empty");
      System.exit(1);
    }

    /* Validate that String description is in good format */
    if(description == null){
      throw new IllegalArgumentException("Description cannot be null");
    }

    String descriptionNoBlank = description.replaceAll(" ", "");
    if(descriptionNoBlank.equals("")){
      System.err.println("Description cannot be empty");
      System.exit(1);
    }

    /* Validate that Date is in good format */
    checkIfDateFormatIsValid(beginDate);
    checkIfDateFormatIsValid(endDate);

    /* validate that time is in good format */
    checkIfTimeFormatIsValid(beginTime);
    checkIfTimeFormatIsValid(endTime);


    /* create Appointment and Appointment Book with validated parameters */

    Appointment newAppointment = new Appointment(description, beginDate, beginTime, endDate, endTime);

    if(args[0].equals("-print")){
      String s = newAppointment.getDescription();
      System.out.println(s);
    }

    AppointmentBook newAppointmentBook = new AppointmentBook(owner);

  }

  private static void checkIfTimeFormatIsValid(String timeString) {
    //convert string into string array
    String[] timeStrArray = timeString.split(":");

    // check if all two parts of hh and mm exists
    if(timeStrArray.length != 2){
      System.err.println("HH mm : either missing one or too many");
      System.exit(1);
    }

    if(timeStrArray[0].length() != 2 || timeStrArray[1].length() != 2){
      System.err.print("Digit out of bound");
      System.exit(1);
    }

    // check hour and minute are valid input
    int hour = 0;
    int minute = 0;

    try{
      hour = Integer.parseInt(timeStrArray[0]);
      minute = Integer.parseInt(timeStrArray[1]);
    } catch(NumberFormatException e){
      System.err.println("This is not a number");
    }

    if(hour < 1 || hour > 23){
      System.err.println("Invalid hour");
      System.exit(1);
    }

    if(minute < 1 || minute > 59){
      System.err.println("Invalid minute");
      System.exit(1);
    }
  }

  private static void checkIfDateFormatIsValid(String dateString) {
    //convert string into string array
    String[] dateStrArray = dateString.split("/");

    // check that there are only three entries - month, day and year
    if(dateStrArray.length != 3){
      System.err.println("MM dd yyyy: either missing one or too many");
      System.exit(1);
    }

    // check month, day and year are within the bound
    if(dateStrArray[0].length() < 1 || dateStrArray[0].length() > 2
            || dateStrArray[1].length() < 1 || dateStrArray[1].length() > 2
            || dateStrArray[2].length() != 4){
      System.err.println("Digit out of bound");
      System.exit(1);
    }


    // check month, day and year are in valid range
    int month = 0;
    int day = 0;
    int year = 0;

    try{
      month = Integer.parseInt(dateStrArray[0]);
      day = Integer.parseInt(dateStrArray[1]);
      year = Integer.parseInt(dateStrArray[2]);
    } catch(NumberFormatException e){
      System.err.println("number is required");
    }

    if(month < 1 || month > 12){
      System.err.println("Invalid month");
      System.exit(1);
    }

    if(day < 1 || day > 31){
      System.err.println("Invalid day");
      System.exit(1);
    }

    if(year < 1 || year > 9999){
      System.err.println("Invalid year");
      System.exit(1);
    }
  }

}