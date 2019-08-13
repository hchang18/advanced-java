package edu.pdx.cs410j.haeyoon.apptbook_app;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Appointment class for the CS410J appointment book Project
 */

public class Appointment extends AbstractAppointment implements Comparable<Appointment>{

  private String description;
  private String beginDate;
  private String beginTime;
  private String endDate;
  private String endTime;
  private String beginMeridiem;
  private String endMeridiem;

  private String fullBeginTime;
  private String fullEndTime;

  public Appointment(String description, String beginDate, String beginTime, String beginMeridiem, String endDate, String endTime, String endMeridiem){

      this.description = description;
      this.beginDate = beginDate;
      this.beginTime = beginTime;
      this.beginMeridiem = beginMeridiem;
      this.endDate = endDate;
      this.endTime = endTime;
      this.endMeridiem = endMeridiem;

      this.fullBeginTime = this.beginDate + " " + this.beginTime + " " + this.beginMeridiem;
      this.fullEndTime = this.endDate + " " + this.endTime + " " + this.endMeridiem;


      // if getEndTime() is sooner than getStartTime() print err message
      if(this.getEndTime().before(this.getBeginTime())) {
          System.err.println("** Appointment's end time is before its start time: " +
                  this.getBeginTimeString() + " " + this.getEndTimeString());
          System.exit(1);
      }

  }

  public Appointment(String description, String beginTime, String endTime) {

      this.description = description;
      this.fullBeginTime = beginTime;
      this.fullEndTime = endTime;

      // if getEndTime() is sooner than getStartTime() print error message
      if(this.getEndTime().before(this.getBeginTime())){
          System.err.println("** Appointment's end time is before its start time: " +
                  this.getBeginTimeString() + " " + this.getEndTimeString());
          System.exit(1);
      }
  }

  @Override
  /**
   * Returns a String describing the beginning date and the time of this
   * appointment
   */
  public String getBeginTimeString() {
      Date date = this.getBeginTime();
      String strBeginDateTime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
      return strBeginDateTime;
  }

  @Override
  /**
   * Returns a String describing the ending date and time of this appointment
   */
  public String getEndTimeString() {
      Date date = this.getEndTime();
      String strEndDateTime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
      return strEndDateTime;
  }

  @Override
  /**
   * Format date, time and meridiem strings into Date
   */
  public Date getBeginTime(){

      Date begin = null;
      DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      // pattern reflects the date and time format on the command line

      try {
        begin = dateFormat.parse(this.fullBeginTime);

      } catch (ParseException ex) {
        String s = "Bad date and time format: " + this.fullBeginTime;
        System.err.println("** " + s);
        System.exit(1);
      }

      return begin;
  }

  @Override
  public Date getEndTime(){

      Date end = null;
      DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      // pattern reflects the date and time format on the command line

      try {
        end = dateFormat.parse(this.fullEndTime);

      } catch (ParseException ex) {
        String s = "Bad date and time format: " + this.fullEndTime;
        System.err.println("** " + s);
        System.exit(1);
      }

      return end;
  }

  @Override
  /**
   * Returns a description of this appointment (for instance,
   * <code> "Kipper's birthday party"</code>
   */
  public String getDescription() {
      return this.description;
  }


  @Override
  /**
   * CompareTo() method that sort chronologically by appointments' beginning time,
   * if two appointments begin at the same time, sort by ending time,
   * if two appointments end at the same time, sort by description.
   */
  public int compareTo(Appointment appointment) {

      int compareBeginTime = this.getBeginTime().compareTo(appointment.getBeginTime());
      if (compareBeginTime == 0) {

          int compareEndTime = this.getEndTime().compareTo(appointment.getEndTime());
          if(compareEndTime == 0) {

              int compareDesc = this.getDescription().compareTo(appointment.getDescription());
              return compareDesc;

          } else {
              return compareEndTime;
          }

      } else {
          return compareBeginTime;
      }

  }
}
