package rs.siriusxi.hbca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.siriusxi.hbca.domain.Booking;
import rs.siriusxi.hbca.domain.BookingStatus;
import rs.siriusxi.hbca.domain.RoomType;
import rs.siriusxi.hbca.repository.BookingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelBookingService {

    private final BookingRepository bookingRepository;

    public List<HotelBookingDetails> getBookings() {
        return bookingRepository.findAll().stream().map(this::toHotelBookingDetails).toList();
    }

    /**
     * Finds booking by number and customer name
     */
    private Booking findBooking(String bookingNumber, String firstName, String lastName) {
        return bookingRepository.findByBookingNumberAndCustomerFirstNameIgnoreCaseAndCustomerLastNameIgnoreCase(
                bookingNumber, firstName, lastName)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    @Tool(name = "cancelBooking",
            description = "Cancel booking for a customer")
    @Transactional
    public void cancelBooking(String bookingNumber, String firstName, String lastName) {
        var booking = findBooking(bookingNumber, firstName, lastName);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Transactional
    public void roomTypeChangeRequest(String bookingNumber, String firstName, String lastName, String roomType) {
        RoomType updatedRoomType = RoomType.valueOf(roomType);
        var booking = findBooking(bookingNumber, firstName, lastName);
        booking.setRoomType(updatedRoomType);
        bookingRepository.save(booking);
    }

    private HotelBookingDetails toHotelBookingDetails(Booking booking) {
        // Maps booking to hotel booking details
        return new HotelBookingDetails(
                booking.getBookingNumber(),
                booking.getCustomer().getFirstName(),
                booking.getCustomer().getLastName(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getBookingStatus(),
                booking.getHotelName(),
                booking.getRoomType(),
                booking.getNumberOfGuests()
        );
    }
}
