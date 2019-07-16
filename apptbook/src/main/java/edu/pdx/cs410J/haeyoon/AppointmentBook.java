package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;
import java.util.ArrayList;

/**
 * AppointmentBook class for the CS410J appointment book Project
 */

public class AppointmentBook extends AbstractAppointmentBook<Appointment>{

    private final String owner;
    private Collection<Appointment> apptCollection = new ArrayList<>();

    public AppointmentBook(String owner){
        this.owner = owner;
    }

    @Override
    /**
    * Returns the name of the owner of this appointment book.
    */
    public String getOwnerName(){
        return this.owner;
    }

    @Override
    /**
    * Returns all of the appointments in this appointment book as a
    * collection of {@link AbstractAppointment}s.
    */
    public Collection getAppointments(){
        return null;
    }

    @Override
    /**
    * Adds an appointment to this appointment book
    */
    public void addAppointment(Appointment appt) {
        apptCollection.add(appt);
    }
}
