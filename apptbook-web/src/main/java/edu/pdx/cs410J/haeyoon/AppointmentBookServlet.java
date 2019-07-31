package edu.pdx.cs410J.haeyoon;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import org.apache.tools.ant.taskdefs.condition.Http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple word/definition pairs.
 */
public class AppointmentBookServlet extends HttpServlet {
    static final String OWNER_PARAMETER = "owner";
    static final String DESCRIPTION_PARAMETER = "description";
    static final String BEGIN_TIME_PARAMETER = "beginTime";
    static final String END_TIME_PARAMETER = "endTime";

    private final Map<String, AppointmentBook> appointmentBooks = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the appointments in the
     * AppointmentBook for "owner" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        //response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        String owner = getParameter(OWNER_PARAMETER, request);
        String beginTime = getParameter(BEGIN_TIME_PARAMETER, request);
        String endTime = getParameter(END_TIME_PARAMETER, request);

        if (owner != null) {
            if (beginTime == null && endTime == null) {
                // print out all appointments in the appointment book
                writeAppointments(owner, response);

            } else if (beginTime != null && endTime != null) {
                // IMPLEMENT SEARCH FUNCTION
                findAppointments(owner, beginTime, endTime, response);

            } else if (beginTime == null && endTime != null){
                missingRequiredParameter(response, BEGIN_TIME_PARAMETER);

            } else {
                missingRequiredParameter(response, END_TIME_PARAMETER);
            }

        } else {
            // RETURN ALL THE APPOINTMENTS OF ALL OWNERS
            writeAllAppointments(response);
        }

        // CHECK ALL THE FUNCTIONS ACT AS IT IS
        // HTTP ERROR MESSAGE CLEAN UP

        response.setStatus(HttpServletResponse.SC_OK);

    }


    /**
     * Handles an HTTP POST request by storing the appointment entry for the
     * "owner", "description", "beginTime", "endTime" request parameters.
     * It writes the appointment book entry for the HTTP request. If the
     * appointment book does not exist, a new one should be created.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");

        // validate that there is no missing element

        String owner = getRequiredParameter(request, response, OWNER_PARAMETER);
        if (owner == null){
            missingRequiredParameter(response, OWNER_PARAMETER);
        }

        String description = getRequiredParameter(request, response, DESCRIPTION_PARAMETER);
        if (description == null){
            missingRequiredParameter(response, DESCRIPTION_PARAMETER);
        }

        String beginTime = getRequiredParameter(request, response, BEGIN_TIME_PARAMETER);
        if (beginTime == null) {
            missingRequiredParameter(response, BEGIN_TIME_PARAMETER);
        }

        String endTime = getRequiredParameter(request, response, END_TIME_PARAMETER);
        if (endTime == null) {
            missingRequiredParameter (response, END_TIME_PARAMETER);
        }

        // VALIDATE BEGIN AND END TIME IS IN CORRECT FORMAT
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        formatter.setLenient(false);
        PrintWriter pw = response.getWriter();

        Date beginTimeDate = null;
        Date endTimeDate = null;

        try {
             beginTimeDate = formatter.parse(beginTime);
        } catch (ParseException e) {
            String message = "** Bad date and time format: " + beginTime;
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
        }

        try {
            endTimeDate = formatter.parse(endTime);
        } catch (ParseException e) {
            String message = "** Bad date and time format: " + endTime;
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
        }

        // VALIDATE START TIME IS BEFORE END TIME
        if (endTimeDate.before(beginTimeDate)) {
            String message = "** Appointment's end time is before its start time: " +
                    beginTimeDate + " " + endTimeDate;
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
        }


        // CREATE AN APPOINTMENT

        if (this.appointmentBooks.containsKey(owner)) {
            // for returning owner
            AppointmentBook book = getAppointmentBook(owner);
            Appointment appointment = new Appointment(description, beginTime, endTime);
            book.addAppointment(appointment);

        } else {
            // for new owner
            AppointmentBook book = new AppointmentBook(owner);
            this.appointmentBooks.put(owner, book);

            Appointment appointment = new Appointment(description, beginTime, endTime);
            book.addAppointment(appointment);

        }

        // response.getWriter().println(appointment.toString());

        response.setStatus(HttpServletResponse.SC_OK);

    }



    /**
     * Handles an HTTP DELETE request by removing all appointment books' entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");

        this.appointmentBooks.clear();

        PrintWriter pw = response.getWriter();
        pw.println("All appointment books deleted.");
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }


    private String getRequiredParameter(HttpServletRequest request, HttpServletResponse response, String parameter)
            throws IOException {
        String value = getParameter(parameter, request);
        if (value == null) {
            missingRequiredParameter(response, parameter);
            return null;
        }
        return value;
    }


    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * <p>
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter(HttpServletResponse response, String parameterName)
            throws IOException {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }


    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     * <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    @VisibleForTesting
    public AppointmentBook getAppointmentBook(String owner) {
        return this.appointmentBooks.get(owner);
    }


    /**
     * Writes the definition of the given word to the HTTP response.
     * <p>
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */

    private void findAppointments(String owner, String beginTime, String endTime,
                                    HttpServletResponse response) throws IOException {


        PrintWriter pw = response.getWriter();

        // DOES THIS OWNER HAVE APPOINTMENT BOOK?
        if (this.appointmentBooks.containsKey(owner)){

            // Convert Search String to Date object
            String pattern = "MM/dd/yyyy hh:mm a";
            DateFormat df = new SimpleDateFormat(pattern);
            df.setLenient(false);

            Date searchBeginTime = null;
            Date searchEndTime = null;

            try {
                searchBeginTime = df.parse(beginTime);

            } catch (ParseException ex) {
                String s = "** Bad date and time format: " + beginTime;
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, s);
            }

            try {
                searchEndTime = df.parse(endTime);
            } catch (ParseException ex) {
                String s = "** Bad date and time format: " + endTime;
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, s);
            }

            // Check if search begin time is later than end time
            if(searchEndTime.before(searchBeginTime)) {
                String s = "** Appointment's end time is before its start time: "
                + searchBeginTime + " " + searchEndTime;
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, s);
            }

            // ITERATE OVER ALL APPOINTMENT BELONG TO THE OWNER AND
            // PRINT OUT ONLY ONES THAT FALLS WITHIN THE SEARCH TIME FRAME

            AppointmentBook book = getAppointmentBook(owner);

            pw.println("Owner: " + book.getOwnerName());

            ArrayList<Appointment> apptList = new ArrayList<>(book.getAppointments());
            for (Appointment appointment : apptList) {

                if(searchBeginTime.after(appointment.getBeginTime())
                        || searchEndTime.before(appointment.getEndTime())){

                } else {

                    pw.print(appointment.getDescription().trim() + " ");

                    pw.print("from ");

                    String beginTimeString = df.format(appointment.getBeginTime());
                    pw.print(beginTimeString);

                    pw.print(" until ");

                    String endTimeString = df.format(appointment.getEndTime());
                    pw.print(endTimeString);

                    pw.print(" for ");

                    long diffInMillies = Math.abs(appointment.getEndTime().getTime() - appointment.getBeginTime().getTime());
                    long duration = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    pw.print(duration);

                    pw.print(" minutes");

                    pw.print("\n");

                }

            }

            pw.flush();

            response.setStatus(HttpServletResponse.SC_OK);



        } else {
            pw.println("Owner has no appointment book: " + owner);

        }



    }


    /**
     * Writes the definition of the given word to the HTTP response.
     * <p>
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */

    private void writeAppointments(String owner, HttpServletResponse response)
            throws IOException {

        if (this.appointmentBooks.containsKey(owner)) {
            prettyPrinter(response, owner);

        } else {
            PrintWriter pw = response.getWriter();
            pw.println("Owner has no appointment book: " + owner);
        }

    }

    /**
     * Writes all of the dictionary entries to the HTTP response.
     * <p>
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    private void writeAllAppointments(HttpServletResponse response)
            throws IOException {

        List owners = new ArrayList(this.appointmentBooks.keySet());
        Iterator iterOwner = owners.iterator();

        while (iterOwner.hasNext()) {

            String owner = String.valueOf(iterOwner.next());
            prettyPrinter(response, owner);
        }
    }

    private void prettyPrinter(HttpServletResponse response, String owner)
            throws IOException {

        AppointmentBook book = getAppointmentBook(owner);

        PrintWriter pw = response.getWriter();
        pw.println("Owner: " + book.getOwnerName());

        ArrayList<Appointment> apptList = new ArrayList<>(book.getAppointments());
        for (Appointment appointment : apptList) {
            pw.print(appointment.getDescription().trim() + " ");

            pw.print("from ");

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            String beginTimeString = df.format(appointment.getBeginTime());
            pw.print(beginTimeString);

            pw.print(" until ");

            DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            String endTimeString = df1.format(appointment.getEndTime());
            pw.print(endTimeString);

            pw.print(" for ");

            long diffInMillies = Math.abs(appointment.getEndTime().getTime() - appointment.getBeginTime().getTime());
            long duration = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
            pw.print(duration);

            pw.print(" minutes");

            pw.print("\n");

        }

        pw.println("");

        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }
}