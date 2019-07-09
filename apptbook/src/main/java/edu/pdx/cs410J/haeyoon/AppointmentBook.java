package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;
import java.util.List;

/**
 * AppointmentBook class for the CS410J appointment book Project
 */

public class AppointmentBook extends AbstractAppointmentBook {

    private final String owner;
    private Collection<Appointment> appointmentsCollection;

    public AppointmentBook(String owner){
        this.owner = owner;
        //this.appointmentsCollection;
    }

    @Override
    public String getOwnerName() {
        return this.owner;
    }

    @Override
    public Collection getAppointments() {
        return null;
    }

    @Override
    public void addAppointment(AbstractAppointment abstractAppointment) {

    }
}
