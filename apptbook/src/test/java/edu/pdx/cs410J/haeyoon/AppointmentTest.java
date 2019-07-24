package edu.pdx.cs410J.haeyoon;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Appointment} class.
 */
public class AppointmentTest {

  @Ignore
  @Test
  public void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment("Birthday Party", "12/11/2018", "11:01", "am",
            "12/11/2018", "12:02", "pm");
    assertThat(appointment.getBeginTimeString(), equalTo("12/11/18, 11:01 AM"));
  }

  @Ignore
  @Test
  public void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment("just testing", " ", " ", " ", " ", " ", " ");
    assertThat(appointment.getDescription(), containsString("just testing"));
  }

  @Ignore
  @Test
  public void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    Appointment appointment = new Appointment("Birthday Party", "12/11/2018", "11:01", "am",
            "12/11/2018", "12:02", "pm");
    assertThat(appointment.getBeginTime(), is(nullValue()));
  }

  @Ignore
  @Test
  public void whenDescriptionIsKipperDescriptionIsKipper(){
    Appointment appointment = new Appointment("Kipper", "12/11/2018", "11:01", "am",
            "12/11/2018", "12:02", "pm");
    assertThat(appointment.getDescription(), equalTo("Kipper"));
  }

  @Test
  public void getBeginTimeStringPrintOutDateFormatShort(){
    Appointment appointment = new Appointment("Birthday Party", "12/11/2018", "11:01", "am",
            "12/11/2018", "12:02", "pm");
    assertThat(appointment.getBeginTimeString(), equalTo("12/11/18, 11:01 AM"));
  }

  @Test
  public void getEndTimeStringPrintOutDateFormatShort() {
    Appointment appointment = new Appointment("Birthday Party", "12/11/2018", "11:01", "am",
            "12/11/2018", "12:02", "pm");
    assertThat(appointment.getEndTimeString(), equalTo("12/11/18, 12:02 PM"));
  }

}
