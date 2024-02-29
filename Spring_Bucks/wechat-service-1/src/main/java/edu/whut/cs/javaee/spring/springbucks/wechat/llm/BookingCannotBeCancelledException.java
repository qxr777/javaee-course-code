package edu.whut.cs.javaee.spring.springbucks.wechat.llm;

public class BookingCannotBeCancelledException extends RuntimeException {

    public BookingCannotBeCancelledException(String bookingNumber) {
        super("Booking " + bookingNumber + " cannot be canceled");
    }
}
