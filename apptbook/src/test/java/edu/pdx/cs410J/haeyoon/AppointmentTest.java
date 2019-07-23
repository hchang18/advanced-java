package edu.pdx.cs410J.haeyoon;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Appointment} class.
 */
public class AppointmentTest {

  private String desc = "Birth day party";
  private String beginDate = "12/11/2018";
  private String beginTime = "11:01";
  private String beginMeridiem = "am";
  private String endDate = "12/13/2018";
  private String endTime = "2:36";
  private String endMeridiem = "pm";


  @Test
  public void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment(desc, beginDate, beginMeridiem, beginTime, endDate, endTime, endMeridiem);
    assertThat(appointment.getBeginTimeString(), equalTo(beginDate + ", " + beginTime));
  }

  @Test
  public void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment("just testing", " ", " ", " ", " ", " ", " ");
    assertThat(appointment.getDescription(), containsString("just testing"));
  }

  @Test
  public void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    Appointment appointment = new Appointment(desc, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
    assertThat(appointment.getBeginTime(), is(nullValue()));
  }

  @Test
  public void whenDescriptionIsKipperDescriptionIsKipper(){
    Appointment appointment = new Appointment(desc, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
    assertThat(appointment.getDescription(), equalTo(desc));
  }

  // Appointment is created as expected

  // Appointment is added to AppointmentBook as expected

  @Test
  public void getBeginTimeStringPrintOutDateFormatShort(){
    Appointment appointment = new Appointment(desc, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
    assertThat(appointment.getBeginTimeString(), equalTo("12/11/18, 11:01 AM"));
  }


}
