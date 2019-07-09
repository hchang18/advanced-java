package edu.pdx.cs410J.haeyoon;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link AppointmentBook} class.
 */
public class AppointmentBookTest {

  private AppointmentBook createAppointmentBookOwnedBy(String owner){
    return new AppointmentBook(owner);
  }

  @Test
  public void AppointmentBookOwnerNamedKipperIsNamedKipper(){}


}
