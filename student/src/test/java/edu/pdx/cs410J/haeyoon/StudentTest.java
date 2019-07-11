package edu.pdx.cs410J.haeyoon;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{
   private Student createStudentNamed(String name) {
    return new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
  }

  @Test
  public void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = createStudentNamed(name);
    assertThat(pat.getName(), equalTo(name));
  }

  // When name is not defined, throw null exceptions
  // @Test is imported from junit.Test
  @Test(expected = NullPointerException.class)
  public void whenNameIsNullThrowANullPointerException(){
    String name = null;
    createStudentNamed(name);
  }

  // When GPA is less than zero, issue a "range error"
  // There were no GPAOutOfBoundsException so it was created in main on the way
  @Test(expected = GPAOutOfBoundsException.class)
  public void whenGPAIsLessThanZeroThrowGPAOutOfBoundsException(){
    double gpa = -1.0;
    new Student("name", new ArrayList<>(), gpa, "");
  }


  // When GPA is not a valid double, exit with an error message saying that GPA must be decimal
  @Test(expected = GPAOutOfBoundsException.class)
  public void when
  // When there are not enough command line arguments, exit with an error message

  // When GPA is less than zero, issue a "range error"

  // When GPA is greater than 4.0, issue a "range error"

}
