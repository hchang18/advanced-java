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

    if(args.length == 0){
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    Appointment appointment0 = new Appointment();
    AppointmentBook book0 = new AppointmentBook();


    String option = args[0];

    if(option.equals("-README")){
      System.out.println("Here is project description.");
      System.exit(0);
    }else if(option.equals("-print")){
      String s = appointment0.getDescription();
      System.out.print(s);
    }

    //for (String arg: args){
    //  System.out.println(arg);
    //}

  }

}