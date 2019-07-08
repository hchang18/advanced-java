package edu.pdx.cs410J.haeyoon;

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

    //Check if any arguments are passed in
    if(args.length == 0){
      System.err.println("Missing command line arguments");
      System.exit(1);
    }


    //If first argument is -README,
    //print out project description and exit
    if(args[0].equals("-README")){
      System.out.println("Here is project description.");
      System.exit(0);
    }


    //If first argument is -print,
    //check the second argument is -README ?
    //print out proj desc : # of args should be 7
    if(args[0].equals("-print")){

      if(args[1].equals("-README")){
        System.out.println("Here is project description.");
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
        Appointment newAppointment = new Appointment();
        AppointmentBook newAppointmentBook = new AppointmentBook();
        // print out appointment description
        String s = newAppointment.getDescription();
        System.out.println(s);
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
        Appointment newAppointment = new Appointment();
        AppointmentBook newAppointmentBook = new AppointmentBook();
        System.exit(0);
      }


    }

    //for (String arg: args){
    //  System.out.println(arg);
    //}

  }

}