package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  /**
   * Tests that invoking the main method with arguments that contains -README
   * prints out the project description and exit
   */
  @Test
  public void whenREADMEIsFlaggedPrintProjectDescAndExit(){
    MainMethodResult result = invokeMain(Project1.class, "-README", "aa", "bb");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Here is project description"));
    assertThat(result.getExitCode(), equalTo(0));
  }

  /**
   * Test that invoking the main method with arguments that contains -print
   * create Appointment with description and
   * prints out the appointment description
   */
  @Test
  public void whenPrintIsFlaggedCreateAppointmentAndPrintAppointmentDesc(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "aa", "bb");
    assertThat(result.getTextWrittenToStandardOut(), equalTo("This method is not implemented yet"));

  }



  // number of args should be 6

  // when there is -print in args, the number of arguments should be 7

  // Appointment is added to the AppointmentBook (maybe in AppointmentTest?)


}