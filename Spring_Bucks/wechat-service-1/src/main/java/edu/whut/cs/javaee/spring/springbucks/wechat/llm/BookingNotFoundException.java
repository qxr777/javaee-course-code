package edu.whut.cs.javaee.spring.springbucks.wechat.llm;

public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(String bookingNumber) {
        super("Booking " + bookingNumber + " not found");
    }
}
