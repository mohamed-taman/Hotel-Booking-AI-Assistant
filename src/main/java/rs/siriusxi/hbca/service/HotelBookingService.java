package rs.siriusxi.hbca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.siriusxi.hbca.domain.Booking;
import rs.siriusxi.hbca.domain.BookingStatus;
import rs.siriusxi.hbca.domain.RoomType;
import rs.siriusxi.hbca.repository.BookingRepository;
import rs.siriusxi.hbca.service.mapper.BookingDetailsMapper;
import rs.siriusxi.hbca.ui.dto.HotelBookingDetails;

import java.util.List;

/**
 * Service class responsible for handling hotel booking operations.
 *
 * This class provides functionalities to retrieve, update, and manage
 * hotel bookings. It interacts with the {@link BookingRepository}
 * to perform data persistence and retrieval and uses the
 * {@link BookingDetailsMapper} to map entities to DTOs.
 *
 * Responsibilities:
 * - Fetch all hotel bookings with relevant details.
 * - Cancel a specific booking for a customer.
 * - Change the room type for an existing booking.
 *
 * Annotations:
 * - {@code @Service}: Marks this class as a Spring service component.
 * - {@code @RequiredArgsConstructor}: Automatically generates a constructor
 *   with required arguments for final fields.
 *
 * Transactional Behavior:
 * - Methods annotated with {@code @Transactional} ensure database
 *   transaction management for data modification operations.
 */
@Service
@RequiredArgsConstructor
public class HotelBookingService {

    private final BookingRepository bookingRepository;
    private final BookingDetailsMapper mapper;

    public List<HotelBookingDetails> getBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(mapper::bookingToHotelBookingDetails).toList();
    }

    /**
     * Finds booking by number and customer name
     */
    private Booking findBooking(String bookingNumber, String firstName, String lastName) {
        return bookingRepository.findBookingBy(bookingNumber, firstName, lastName)
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

    @Tool(name = "roomTypeChangeRequest",
            description = "Change room type for a customer booking")
    @Transactional
    public void roomTypeChangeRequest(String bookingNumber, String firstName, String lastName, String roomType) {
        RoomType updatedRoomType = RoomType.valueOf(roomType);
        var booking = findBooking(bookingNumber, firstName, lastName);
        booking.setRoomType(updatedRoomType);
        bookingRepository.save(booking);
    }
}
