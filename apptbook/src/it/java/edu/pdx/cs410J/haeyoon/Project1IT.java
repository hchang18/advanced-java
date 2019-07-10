package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

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

  private String owner = "Pete the Cat";
  private String desc = "Birth day party";
  private String beginDate = "12/11/2018";
  private String beginTime = "12:01";
  private String endDate = "12/13/2018";
  private String endTime = "2:36";

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
    MainMethodResult result = invokeMain(Project1.class, "-README", owner, desc, beginDate);
    assertThat(result.getTextWrittenToStandardOut(), containsString("Project 1"));
    assertThat(result.getExitCode(), equalTo(0));
  }

  @Test
  public void whenPrintIsFlaggedAndREADMEIsFlaggedPrintProjectDescAndExit(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "-README", owner, desc, beginDate);
    assertThat(result.getTextWrittenToStandardOut(), containsString("Project 1"));
    assertThat(result.getExitCode(), equalTo(0));
  }


  /**
   * Tests that invoking the main method with arguments that contains -print
   */
  @Test
  public void whenPrintIsFlaggedAndREADMEIsNotFlaggedAndArgsAreLessThan7PrintMissingArgumentsToStandardError(){
    MainMethodResult result = invokeMain(Project1.class, "-print", owner, desc);
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing arguments"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenPrintIsFlaggedAndREADMEIsNotFlaggedAndArgsAreMoreThan7PrintExtraneousArgumentsToStandardError(){
    MainMethodResult result = invokeMain(Project1.class, "-print", owner, desc, beginDate, beginTime, endDate, endTime, "hello");
    assertThat(result.getTextWrittenToStandardError(), containsString("Extraneous arguments"));
    assertThat(result.getExitCode(), equalTo(1));
  }


  /**
   * Tests that invoking the main method with no options but arguments
   */
  @Test
  public void whenNoOptionIsFlaggedAndArgsAreLessThan6PrintMissingArgumentsToStandardError(){
    MainMethodResult result = invokeMain(Project1.class, owner , desc);
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing arguments"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenNoOptionIsFlaggedAndArgsAreMoreThan6PrintExtraneousArgumentsToStandardError(){
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime, "hello");
    assertThat(result.getTextWrittenToStandardError(), containsString("Extraneous arguments"));
    assertThat(result.getExitCode(), equalTo(1));
  }


  /**
   * Tests that Owner is not null or empty or " ".
   */
  @Test(expected = IllegalArgumentException.class)
  public void whenOwnerIsNullThrowIllegalArgumentException(){
    String ownerString = null;
    this.owner = ownerString;
    invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
  }

  @Test
  public void whenOwnerIsEmptyStringPrintErrorMessage(){
    String ownerString = "";
    this.owner = ownerString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Owner cannot be empty"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenOwnerIsBlankSpacesPrintErrorMessage(){
    String ownerString = "        ";
    this.owner = ownerString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Owner cannot be empty"));
    assertThat(result.getExitCode(), equalTo(1));
  }

   /**
   * Tests that description is not null or empty or " ".
   */
  @Test(expected = IllegalArgumentException.class)
  public void whenDescIsNullThrowIllegalArgumentException(){
    String descString = null;
    this.desc = descString;
    invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
  }

  @Test
  public void whenDescIsEmptyStringPrintErrorMessage(){
    String descString = "";
    this.desc = descString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("cannot be empty"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenDescIsBlankSpacesPrintErrorMessage(){
    String descString = "        ";
    this.desc = descString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("cannot be empty"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  /**
  * Tests that date is in good format.
  */
  @Test
  public void whenOneOfMMddyyyyisMissingPrintErrorMessage(){
    String dateString = "12/2/1111/1111";
    this.beginDate = dateString;
    this.endDate = dateString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("MM dd yyyy: either missing one or too many"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenMonthEntryIsNot1or2DigitsPrintErrorMessage(){
    String dateString = "123/11/2018";
    this.beginDate = dateString;
    this.endDate = dateString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Digit out of bound"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenMonthIsInvalidEntryPrintErrormessage(){
    String dateString = "15/23/2019";
    this.beginDate = dateString;
    this.endDate = dateString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid month"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenDayEntryIsNot1or2DigitsPrintErrorMessage(){
    String dateString = "12/1111/2018";
    this.beginDate = dateString;
    this.endDate = dateString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Digit out of bound"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenDayIsInvalidEntryPrintErrormessage(){
    String dateString = "12/41/2019";
    this.beginDate = dateString;
    this.endDate = dateString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid day"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenYearEntryIsNot4DigitsPrintErrorMessage(){
    String dateString = "05/12/201811";
    this.beginDate = dateString;
    this.endDate = dateString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Digit out of bound"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenYearIsInvalidEntryPrintErrormessage(){
    String dateString = "06/02/0000";
    this.beginDate = dateString;
    this.endDate = dateString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid year"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  /**
   * Test that time is in good format
   */
  @Test
  public void whenOneOfHHmmIsMissingPrintErrorMessage(){
    String timeString = "11:12:13";
    this.beginTime = timeString;
    this.endTime = timeString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("HH mm : either missing one or too many"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenHHEntryIsNot1or2DigitsPrintErrorMessage(){
    String timeString = ":11";
    this.beginTime = timeString;
    this.endTime = timeString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Digit out of bound"));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void whenHHIsInvalidEntryPrintErrormessage(){
    String timeString = "25:11";
    this.beginTime = timeString;
    this.endTime = timeString;
    MainMethodResult result = invokeMain(Project1.class, owner, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid hour"));
    assertThat(result.getExitCode(), equalTo(1));
  }

}