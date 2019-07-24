package edu.pdx.cs410J.haeyoon;

import com.sun.tools.javac.Main;
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


  ////////////////////////// Project 1 /////////////////////////////////////////

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
    MainMethodResult result = invokeMain(Project3.class, "Test4", "This is Test 4", "03/03/2019", "12:XX", "pm", "03/03/2019", "4:00", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Malformatted date and time"));
    assertThat(result.getExitCode(), equalTo(1));
  }


  /**
   * Test 5: End time is malformatted
   */
  @Test
  public void whenEndTimeIsMalformattedPrintErrorMessage(){
    MainMethodResult result = invokeMain(Project3.class, "Test5", "This is Test 5", "03/03/2019", "12:00", "pm", "03/03/20/9", "16:00", "pm");
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
    MainMethodResult result = invokeMain(Project3.class, "Test7", "This is Test 6", "03/03/2019", "12:00", "pm", "04/04/2019", "4:00", "pm", "fred");
    assertThat(result.getTextWrittenToStandardError(), containsString("Extraneous command line argument"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  /**
   * Test 8: Printing out an appointment
   */
  @Test
  public void whenPrintOutOptionIsInvokedPrintOutAppointment(){
    MainMethodResult result = invokeMain(Project3.class, "-print", "Test8", "This is Test8", "03/03/2019", "12:00", "pm", "04/04/2019", "1:00", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString("This is Test8 from 3/3/19, 12:00 PM until 4/4/19, 1:00 PM"));
  }

  /**
   * Test 9: Multi-word owner name
   */
  @Test
  public void whenOwnerNameIsMultiWordsPrintOutAllOfThem(){
    MainMethodResult result = invokeMain(Project3.class, "-print", "Test 9", "This is Test9", "03/03/2019", "12:00", "pm", "04/04/2019", "1:00", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString("This is Test9 from 3/3/19, 12:00 PM until 4/4/19, 1:00 PM"));
  }


  /**
   * Test 10: Missing end time
   */
  @Test
  public void whenMissingEndTimePrintErrMessage() {
    MainMethodResult result = invokeMain(Project3.class, "Test10", "No End time", "03/03/2019", "12:00", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing argument"));
    assertThat(result.getExitCode(), equalTo(1));
  }


  //////////////////////////// Project 2 /////////////////////////////////////////

  /**
   * Test 7: Starting a new appointment book file
   */
  @Test
  public void StartnewAppointmentBookFile() {
    MainMethodResult result = invokeMain(Project3.class, "-textFile", "haeyoon.txt", "-print", "Project2",
            "This is Test 7", "01/07/2019", "07:00", "am", "01/17/2019", "5:00", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString("This is Test 7 from 1/7/19, 7:00 AM until 1/17/19, 5:00 PM"));
  }

  /**
   * Test 8: Using an existing appointment book file
   */
  @Test
  public void UsingAnExistingAppointmentBookFile() {
    MainMethodResult result = invokeMain(Project3.class, "-textFile",
            "haeyoon.txt", "-print", "Project2", "This is Test 8",
            "01/08/2019", "08:00", "am", "01/08/2019", "6:00", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString("This is Test 8 from 1/8/19, 8:00 AM until 1/8/19, 6:00 PM"));

  }
  /**
   * Test 9: Different owner name
   */
  @Test
  public void DifferentOwnerNamePrintsErrMsg(){
    MainMethodResult result = invokeMain(Project3.class, "-textFile",
            "haeyoon.txt", "DIFFERENT", "This is Test 9",
            "01/09/2019", "09:00", "am", "02/04/2019", "4:00", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("The owner name given on the commandline:"));
  }


  /**
   * Test 10: Malformatted text file
   */
  @Test
  public void whenMalformattedTextFilePrintErrMsg() {
    MainMethodResult result = invokeMain(Project3.class, "-textFile", "bogus.txt", "Project2", "This is Test 10", "01/10/2019",
            "10:00", "am", "01/20/2019", "8:00", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Malformatted text file"));
    assertThat(result.getExitCode(), equalTo(1));

  }

  /**
   * Test 11: File with an invalid year
   */
  @Test
  public void FileWithInvalidYearPrintErrorMessage(){
    MainMethodResult result = invokeMain(Project3.class, "-textFile", "haeyoon-bad-year.txt", "Project2", "This is Test 10",
            "01/11/2019", "11:00", "am", "01/11/2019", "11:39", "am");
    assertThat(result.getTextWrittenToStandardError(), containsString("Malformatted date and time"));
    assertThat(result.getExitCode(), equalTo(1));
  }


  /**
   * Text xxx: End time precedes start time
   */
  @Test
  public void beginTimeShouldBeBeforeEndTimeOrPrintErrorMessage(){
    MainMethodResult result = invokeMain(Project3.class, "Kipper", "visit Arnold", "12/1/2018", "2:00", "pm", "12/1/2018", "1:00", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Appointment's end time is before its start time:"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  /**
   * Text xxx: Wrong meridiem prints out error message
   */
  @Test
  public void WrongMeridiemPrintsOutErrorMessage() {
    MainMethodResult result = invokeMain(Project3.class, "Kipper", "visit Arnold", "12/1/2018", "2:00", "xx", "12/1/2018", "1:00", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("Malformatted date and time"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  /**
   * Test xxx: PrettyPrinter prints out appointment in order
   */
  @Test
  public void PrettyPrinterPrintsOutAppointmentsInOrder() {
    MainMethodResult result1 = invokeMain(Project3.class, "-textFile", "test.txt", "Kipper", "Kelly's birthday party - 2018",
            "12/1/2018", "4:00", "pm", "12/1/2018", "8:00", "pm");
    MainMethodResult result2 = invokeMain(Project3.class, "-textFile", "test.txt", "Kipper", "2017 Kelly's birthday party",
            "12/1/2017", "4:00", "pm", "12/1/2017", "8:00", "pm");
    MainMethodResult result3 = invokeMain(Project3.class, "-textFile", "test.txt", "Kipper", "Feed ducky",
            "12/1/2018", "4:00", "pm", "12/1/2018", "8:00", "pm");
    MainMethodResult result4 = invokeMain(Project3.class, "-textFile", "test.txt", "Kipper", "Playdate with Arnold",
            "3/1/2018", "4:00", "pm", "3/2/2018", "8:00", "pm");
    MainMethodResult result5 = invokeMain(Project3.class, "-textFile", "test.txt", "Kipper", "Go to the potty",
            "3/1/2018", "10:00", "am", "4/1/2018", "8:00", "pm");
    MainMethodResult result6 = invokeMain(Project3.class, "-textFile", "test.txt", "Kipper", "Library new book coming",
            "12/1/2018", "4:00", "pm", "12/1/2018", "6:00", "pm");
    MainMethodResult result7 = invokeMain(Project3.class, "-textFile", "test.txt", "-pretty", "pretty.txt", "Kipper", "Almost there?!",
            "12/1/2019", "4:00", "am", "12/1/2019", "4:30", "pm");
    MainMethodResult result8 = invokeMain(Project3.class, "-textFile", "test.txt", "-pretty", "-", "Kipper", "I am done testing!",
            "12/1/2019", "3:00", "am", "12/1/2019", "2:30", "pm");

    assertThat(result8.getTextWrittenToStandardOut(), containsString("I am done testing!"));
  }

}