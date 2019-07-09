package edu.pdx.cs410J.haeyoon;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Appointment} class.
 */
public class AppointmentTest {

  private Appointment createAppointmentDescribed(String desc){
    return new Appointment(desc, "12/13/2018",
            "1:02", "1/24/2017", "11:49");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = createAppointmentDescribed("Hello");
    appointment.getBeginTimeString();
  }

  @Test
  public void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment("just testing", " ", " ", " ", " ");
    assertThat(appointment.getDescription(), containsString("just testing"));
  }

  @Test
  public void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    Appointment appointment = createAppointmentDescribed("Hello");
    assertThat(appointment.getBeginTime(), is(nullValue()));
  }

  // description is not null
  @Test
  public void whenDescriptionIsKipperDescriptionIsKipper(){
    String desc = "Kipper";
    Appointment appointment = createAppointmentDescribed(desc);
    assertThat(appointment.getDescription(), equalTo(desc));
  }

  // Appointment is created as intended

  // Appointment is added to AppointmentBook

}
