package com.grandvista.booking.controller;

import java.util.List;
import com.grandvista.booking.client.PaymentClient;
import com.grandvista.booking.entity.PaymentRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grandvista.booking.entity.Booking;
import com.grandvista.booking.repository.BookingRepository;
import com.grandvista.booking.client.RoomClient;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	private final BookingRepository bookingRepository;
	private final PaymentClient paymentClient;
	private final RoomClient roomClient;
	
	public BookingController(BookingRepository bookingRepository,
            PaymentClient paymentClient,
            RoomClient roomClient) {
this.bookingRepository = bookingRepository;
this.paymentClient = paymentClient;
this.roomClient = roomClient;
}
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Booking addBooking(@RequestBody Booking booking) {

        Booking savedBooking = bookingRepository.save(booking);

        PaymentRequest paymentRequest = new PaymentRequest(
                savedBooking.getId(),
                savedBooking.getAmount(),
                "CARD",
                "SUCCESS"
        );

        paymentClient.createPayment(paymentRequest);

        roomClient.updateAvailability(1L, false);

        return savedBooking;
    }
    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id,
                                 @RequestBody Booking booking) {

        Booking existingBooking =
                bookingRepository.findById(id).orElse(null);

        if (existingBooking == null) {
            return null;
        }

        existingBooking.setCustomerName(booking.getCustomerName());
        existingBooking.setRoomNumber(booking.getRoomNumber());
        existingBooking.setCheckIn(booking.getCheckIn());
        existingBooking.setCheckOut(booking.getCheckOut());
        existingBooking.setAmount(booking.getAmount());
        existingBooking.setStatus(booking.getStatus());

        return bookingRepository.save(existingBooking);
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }
    @GetMapping("/test-room")
    public String testRoomConnection() {
        roomClient.updateAvailability(1L, false);
        return "Room Service connection successful!";
    }
    @GetMapping("/test-payment")
    public String testPaymentConnection() {
        PaymentRequest payment = new PaymentRequest(
            999L,
            1000.0,
            "CARD",
            "SUCCESS"
        );

        paymentClient.createPayment(payment);

        return "Payment Service connection successful!";
    }
}