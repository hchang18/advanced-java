package edu.pdx.cs410J.haeyoon;

import edu.pdx.cs410J.lang.Human;

import java.util.List;
                                                                                    
/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {                                                

  private final double gpa;
  private final Gender gender;
  private final List<String> classes;
  /**                                                                               
   * Creates a new <code>Student</code>                                             
   *                                                                                
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male" or "female", case insensitive)             
   */                                                                               
  public Student(String name, List<String> classes, double gpa, Gender gender) {
    super(name);

    if (name == null){
      throw new NullPointerException("Name cannot be null");
    }

    if (gpa < 0.0){
      throw new GPAOutOfBoundsException("GPA cannot be less than zero.");
    } else if(gpa > 4.0){
      throw new GPAOutOfBoundsException("GPA cannot be more than 4.0");
    }

    this.gpa = gpa;
    this.gender = gender;
    this.classes = List.copyOf(classes);
  }

  /**                                                                               
   * All students say "This class is too much work"
   */
  @Override
  public String says() {                                                            
    throw new UnsupportedOperationException("Not implemented yet");
  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */                                                                               
  public String toString() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    System.err.println("Missing command line arguments");
    System.exit(1);
  }
}