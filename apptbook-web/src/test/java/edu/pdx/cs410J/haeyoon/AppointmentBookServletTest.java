package edu.pdx.cs410J.haeyoon;

import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AppointmentBookServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AppointmentBookServletTest {

  @Ignore
  @Test
  public void initiallyServletContainsNoDictionaryEntries() throws ServletException, IOException {
    AppointmentBookServlet servlet = new AppointmentBookServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  @Ignore
  @Test
  public void addAppointmentToNewAppointmentBook() throws ServletException, IOException {
    AppointmentBookServlet servlet = new AppointmentBookServlet();

    String owner = "TEST WORD";
    String description = "TEST DEFINITION";
    String beginTime = "1/1/2019 1:00 am";
    String endTime = "1/1/2019 2:00 am";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("owner")).thenReturn(owner);
    when(request.getParameter("description")).thenReturn(description);
    when(request.getParameter("beginTIme")).thenReturn(beginTime);
    when(request.getParameter("endTime")).thenReturn(endTime);

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);
    verify(response).setStatus(HttpServletResponse.SC_OK);

    AppointmentBook book = servlet.getAppointmentBook(owner);
    assertThat(book, is(notNullValue()));


    assertThat(book.getAppointments(), hasSize(1));
    assertThat(book.getOwnerName(), equalTo(owner));

    Appointment appointment = book.getAppointments().iterator().next();
    assertThat(appointment, is(notNullValue()));
    assertThat(appointment.getDescription(), equalTo(description));
    assertThat(appointment.getBeginTimeString(), equalTo(beginTime));
    assertThat(appointment.getEndTimeString(), equalTo(endTime));

    verify(pw).println(appointment.toString());

  }

  @Ignore
  @Test
  public void lookingUpUnknownWordReturnNotFound() throws IOException, ServletException {
    AppointmentBookServlet servlet = new AppointmentBookServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("word")).thenReturn("unknownWord");

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);

  }

}
