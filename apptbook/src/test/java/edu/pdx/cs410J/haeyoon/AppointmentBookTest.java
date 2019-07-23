package edu.pdx.cs410J.haeyoon;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link AppointmentBook} class.
 */
public class AppointmentBookTest {

  @Test
  public void AppointmentCompareToWorksFineOrPrintErrorMsg(){
    AppointmentBook test = new AppointmentBook("Kipper");

    Appointment A1 = new Appointment("Kelly's birthday party", "12/1/2018", "4:00", "pm",
            "12/1/2018", "8:00", "pm");
    test.addAppointment(A1);

    Appointment A2 = new Appointment("Feed ducky", "12/1/2018", "4:00", "pm",
            "12/1/2018", "8:00", "pm");
    test.addAppointment(A2);

    Appointment A3 = new Appointment("Arnold's playdate", "12/2/2019", "1:00", "pm",
            "12/3/2019", "2:00", "am");
    test.addAppointment(A3);

    Appointment A4 = new Appointment("Homework", "12/2/2019", "1:00", "pm",
            "12/3/2019", "1:00", "am");
    test.addAppointment(A4);

    Collection c = test.getAppointments();

    for(Object o: c){
      System.out.println(o);
    }


    //List<Appointment> apptList = new ArrayList<>(test.getAppointments());
    //Collections.sort(apptList);
    //for(Appointment o: apptList){
    //  System.out.println(o);
    //}

  }



}
