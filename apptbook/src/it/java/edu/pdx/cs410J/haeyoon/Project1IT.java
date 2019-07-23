package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project3} main class.
 */
public class Project1IT extends InvokeMainTestCase {

  /**
   * Invoke the main method of {@link Project3} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain(Project3.class, args);
  }

  /**
   * Test 1: No Arguments
   */
  @Test
  public void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  /**
   * Test 2: Your README
   */
  @Test
  public void whenREADMEIsFlaggedPrintProjectDescAndExit() {
    MainMethodResult result = invokeMain(Project3.class, "-README");
    assertThat(result.getTextWrittenToStandardOut(), containsString("This Program optionally"));
    assertThat(result.getExitCode(), equalTo(0));
  }

  /**
   * Test 3: Missing description
   */
  @Test
  public void whenArgsMissingDescriptionPrintMissingArgumentsToStandardError(){
    MainMethodResult result = invokeMain(Project3.class, "Test3", "03/03/2019", "12:00", "03/03/2019", "16:00");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing argument(s)"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  /**
   * Test 4: Begin time is malformatted
   */
  @Test
  public void whenBeginTimeIsMalformattedPrintErrorMessage(){
    MainMethodResult result = invokeMain(Project3.class, "Test4", "This is Test 4", "03/03/2019", "12:XX", "03/03/2019", "16:00");
    assertThat(result.getTextWrittenToStandardError(), containsString("Malformatted date and time"));
    assertThat(result.getExitCode(), equalTo(1));
  }


  /**
   * Test 5: End time is malformatted
   */
  @Test
  public void whenEndTimeIsMalformattedPrintErrorMessage(){
    MainMethodResult result = invokeMain(Project3.class, "Test5", "This is Test 5", "03/03/2019", "12:00", "03/03/20/9", "16:00");
    assertThat(result.getTextWrittenToStandardError(), containsString("Malformatted date and time"));
    assertThat(result.getExitCode(), equalTo(1));
  }


  /**
   * Test 6: Unknown command line option
   */
  @Test
  public void whenUnknownCommandLineOptionIsGivenPrintErrorMessage(){
    MainMethodResult result = invokeMain(Project3.class, "-fred", "Test6", "This is Test 6", "03/03/2019", "12:00", "04/04/2019", "16:00");
    assertThat(result.getTextWrittenToStandardError(), containsString("Unknown command line option"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  /**
   * Test 7: Unknown command line argument
   */
  @Test
  public void whenUnknownCommandLineArgumentIsPassedPrintErrorMessage(){
    MainMethodResult result = invokeMain(Project3.class, "Test7", "This is Test 6", "03/03/2019", "12:00", "04/04/2019", "16:00", "fred");
    assertThat(result.getTextWrittenToStandardError(), containsString("Extraneous command line argument"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  /**
   * Test 8: Printing out an appointment
   */
  @Test
  public void whenPrintOutOptionIsInvokedPrintOutAppointment(){
    MainMethodResult result = invokeMain(Project3.class, "-print", "Test8", "This is Test8", "03/03/2019", "12:00", "04/04/2019", "16:00");
    assertThat(result.getTextWrittenToStandardOut(), containsString("This is Test8 from 03/03/2019, 12:00 until 04/04/2019, 16:00"));
  }

  /**
   * Test 9: Multi-word owner name
   */
  @Test
  public void whenOwnerNameIsMultiWordsPrintOutAllOfThem(){
    MainMethodResult result = invokeMain(Project3.class,  "-print", "Test 9", "This is Test 9", "03/03/2019", "12:00", "09/04/2019", "16:00");
    assertThat(result.getTextWrittenToStandardOut(), containsString("This is Test 9 from 03/03/2019, 12:00 until 09/04/2019, 16:00"));
  }


  /**
   * Test 10: Missing end time
   */
  @Test
  public void whenMissingEndTimePrintErrMessage() {
    MainMethodResult result = invokeMain(Project3.class, "Test10", "No End time", "03/03/2019", "12:00");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing argument"));
    assertThat(result.getExitCode(), equalTo(1));
  }


  @Test
  public void beginTimeShouldBeBeforeEndTimeOrPrintErrorMessage(){
    MainMethodResult result = invokeMain(Project3.class, "Kipper", "visit Arnold", "12/1/2018", "2:00", "pm", "12/1/2018", "1:00", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Appointment's end time is before its start time:"));
    assertThat(result.getExitCode(), equalTo(1));

  }

}