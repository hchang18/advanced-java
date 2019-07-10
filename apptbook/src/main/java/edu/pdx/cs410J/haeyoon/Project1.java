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
    }

    /* Validate that String description is in good format */
    if(description == null){
      throw new IllegalArgumentException("Description cannot be null");
    }

    String descriptionNoBlank = description.replaceAll(" ", "");
    if(descriptionNoBlank.equals("")){
      System.err.println("Description cannot be empty");
    }

    /* Validate that Date is in good format */

    /* validate that time is in good format */

    /* create Appointment and Appointment Book with validated parameters */

    Appointment newAppointment = new Appointment(description, beginDate, beginTime, endDate, endTime);

    if(args[0].equals("-print")){
      String s = newAppointment.getDescription();
      System.out.println(s);
    }

    AppointmentBook newAppointmentBook = new AppointmentBook(owner);

  }

}