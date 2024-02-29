package edu.whut.cs.javaee.spring.springbucks.wechat.llm;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookingService {

    public Booking getBookingDetails(String bookingNumber, String customerName) {
        ensureExists(bookingNumber, customerName);

        // Imitating retrieval from DB
        LocalDate bookingFrom = LocalDate.now().plusDays(1);
        LocalDate bookingTo = LocalDate.now().plusDays(3);
        Customer customer = new Customer(customerName);
        return new Booking(bookingNumber, bookingFrom, bookingTo, customer);
    }

    public void cancelBooking(String bookingNumber, String customerName, String customerSurname) {
        ensureExists(bookingNumber, customerName);

        // Imitating cancellation
        throw new BookingCannotBeCancelledException(bookingNumber);
    }

    private void ensureExists(String bookingNumber, String customerName) {
        // Imitating check
        if (!(bookingNumber.equals("1")
                && customerName.equals("spring-8090"))) {
            throw new BookingNotFoundException(bookingNumber);
        }
    }
}
