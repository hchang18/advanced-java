package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;
import java.util.ArrayList;

/**
 * AppointmentBook class for the CS410J appointment book Project
 */

public class AppointmentBook extends AbstractAppointmentBook {

    private final String owner;
    private Collection<Appointment> apptCollection = new ArrayList<Appointment>();

    public AppointmentBook(String owner){
        this.owner = owner;
        //this.appointmentsCollection;
    }

    @Override
    public String getOwnerName(){
        return this.owner;
    }

    @Override
    public Collection getAppointments(){
        return null;
    }

    @Override
    public void addAppointment(AbstractAppointment abstractAppointment) {

    }
}
