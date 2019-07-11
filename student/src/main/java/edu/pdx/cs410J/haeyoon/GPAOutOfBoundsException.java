package edu.pdx.cs410J.haeyoon;

// Throwable exception is:  in the constructor,
// you must declare that you throw exception
// in the signature of student constructor
// I prefer to use "unchecked" exceptions
// - which is subclass of RuntimeException
public class GPAOutOfBoundsException extends RuntimeException {
    public GPAOutOfBoundsException(String message){
        super(message);
    }

}
