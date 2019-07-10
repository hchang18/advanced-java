package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.AbstractAppointment;

/**
 * Appointment class for the CS410J appointment book Project
 */

public class Appointment extends AbstractAppointment {

  private String description;
  private String beginDate;
  private String beginTime;
  private String endDate;
  private String endTime;

  public Appointment(String description, String beginDate, String beginTime, String endDate, String endTime){

    this.description = description;
    this.beginDate = beginDate;
    this.beginTime = beginTime;
    this.endDate = endDate;
    this.endTime = endTime;

  }

  @Override
  public String getBeginTimeString() {
    return this.beginDate + " " + this.beginTime;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }


  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDescription() {
    return this.description;
  }
}
