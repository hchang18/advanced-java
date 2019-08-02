package edu.pdx.cs410J.haeyoon;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client
 */
public class AppointmentBookRestClient extends HttpRequestHelper {
  private static final String WEB_APP = "apptbook";
  private static final String SERVLET = "appointments";

  private final String url;


  /**
   * Creates a client to the appointment book REST service running on the given host and port
   *
   * @param hostName The name of the host
   * @param port     The port
   */
  public AppointmentBookRestClient(String hostName, int port) {
    this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
  }


  /**
   * Return the appointments that belong to specific owner
   */
  public String getAppointments(String owner) throws IOException {
    Response response = get(this.url, Map.of("owner", owner));
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();
    return content;
  }


  /**
   * Search for appointments that meets the search criteria
   */
  public String searchAppointments(String owner, String beginTime, String endTime)
    throws IOException {
    Map<String, String> params =
            Map.of(
                    "owner", owner,
                    "beginTime", beginTime,
                    "endTime", endTime
            );
    Response response = get(this.url, params);
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();
    return content;
  }


  /**
   * Return all the appointments that belong to all owner
   */
  public String getAllAppointmentEntries() throws IOException {
    Response response = get(this.url, Map.of());
    try {
      throwExceptionIfNotOkayHttpStatus(response);
    } catch (AppointmentBookRestException ex) {
      System.err.println ("** AppointmentBookRestException: ");
    }
    String content = response.getContent();
    return content;
  }


  /**
   * Create new appointment for certain owner
   */
  public String addAppointment(String owner, String description, String beginTime, String endTime)
          throws IOException {
    Map<String, String> params =
            Map.of(
                    "owner", owner,
                    "description", description,
                    "beginTime", beginTime,
                    "endTime", endTime
            );
    Response response = postToMyURL(params);
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();
    return content;
  }

  /**
   * Delete an appointment
   */
  public void removeAllDictionaryEntries() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }


  @VisibleForTesting
  Response postToMyURL(Map<String, String> apptEntries) throws IOException {
    return post(this.url, apptEntries);
  }


  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      throw new AppointmentBookRestException(code);
    }
    return response;
  }

  private class AppointmentBookRestException extends RuntimeException {
    public AppointmentBookRestException(int httpStatusCode) {
      super("Got an HTTP Status Code of " + httpStatusCode);
    }
  }
}
