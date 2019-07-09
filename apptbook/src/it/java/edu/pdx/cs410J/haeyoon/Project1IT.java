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
    assertThat(result.getTextWrittenToStandardOut(), containsString("Here is project description"));
    assertThat(result.getExitCode(), equalTo(0));
  }

  @Test
  public void whenPrintIsFlaggedAndREADMEIsFlaggedPrintProjectDescAndExit(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "-README", owner, desc, beginDate);
    assertThat(result.getTextWrittenToStandardOut(), containsString("Here is project description"));
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
    String s = null;
    MainMethodResult result = invokeMain(Project1.class, s, desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Owner cannot be null"));
  }

  @Test(expected=IllegalArgumentException.class)
  public void whenOwnerIsEmptyThrowIllegalArgumentException(){
    MainMethodResult result = invokeMain(Project1.class, "", desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Owner cannot be empty"));
  }

  @Test(expected=IllegalArgumentException.class)
  public void whenOwnerIsBlankThrowIllegalArgumentsException(){
    MainMethodResult result = invokeMain(Project1.class, "      ", desc, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Owner cannot be blank"));
  }


   /**
   * Tests that Description is not null or empty or " ".
   */
  @Test(expected = IllegalArgumentException.class)
  public void whenDescriptionIsNullThrowIllegalArgumentException(){
    MainMethodResult result = invokeMain(Project1.class, owner, null, beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Description cannot be null"));
  }

  @Test(expected=IllegalArgumentException.class)
  public void whenDescriptionIsEmptyThrowIllegalArgumentException(){
    MainMethodResult result = invokeMain(Project1.class, owner, "", beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Description cannot be empty"));
  }

  @Test(expected=IllegalArgumentException.class)
  public void whenDescriptionIsBlankThrowIllegalArgumentsException(){
    MainMethodResult result = invokeMain(Project1.class, owner, "     ", beginDate, beginTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardError(), containsString("Description cannot be blank"));
  }


 /**
   * Tests that format of Dates is valid.
   */



 /**
   * Tests that format of TIme is valid.
   */

}