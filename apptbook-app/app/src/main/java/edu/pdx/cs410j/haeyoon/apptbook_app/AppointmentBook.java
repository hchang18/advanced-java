package edu.pdx.cs410j.haeyoon.apptbook_app;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * collection of {@link AbstractAppointment}s. List shows up in
     * sorted order (using Collections.sort() method).
     */
    public Collection<Appointment> getAppointments(){

        List listToSort = new ArrayList(this.apptCollection);
        Collections.sort(listToSort);
        Collection c = new ArrayList(listToSort);
        this.apptCollection = c;

        return this.apptCollection;
    }

    @Override
    /**
     * Adds an appointment to this appointment book
     */
    public void addAppointment(Appointment appt) {
        this.apptCollection.add(appt);
    }

}
