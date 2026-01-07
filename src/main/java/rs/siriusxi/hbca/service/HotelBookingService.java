package rs.siriusxi.hbca.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.siriusxi.hbca.domain.Booking;
import rs.siriusxi.hbca.domain.BookingStatus;
import rs.siriusxi.hbca.domain.RoomType;
import rs.siriusxi.hbca.repository.BookingRepository;
import rs.siriusxi.hbca.service.mapper.BookingDetailsMapper;
import rs.siriusxi.hbca.ui.dto.HotelBookingDetail;

import java.util.List;

/**
 * Service class responsible for handling hotel booking operations.
 * <p>
 * This class provides functionalities to retrieve, update, and manage
 * hotel bookings. It interacts with the {@link BookingRepository}
 * to perform data persistence and retrieval and uses the
 * {@link BookingDetailsMapper} to map entities to DTOs.
 * <p>
 * Responsibilities:
 * - Fetch all hotel bookings with relevant details.
 * - Cancel a specific booking for a customer.
 * - Change the room type for an existing booking.
 * <p>
 * Annotations:
 * - {@code @Service}: Marks this class as a Spring service component.
 * - {@code @RequiredArgsConstructor}: Automatically generates a constructor
 * with required arguments for final fields.
 * <p>
 * Transactional Behavior:
 * - Methods annotated with {@code @Transactional} ensure database
 * transaction management for data modification operations.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class HotelBookingService {

    private final BookingRepository bookingRepository;
    private final BookingDetailsMapper mapper;

    /**
     * Finds booking by number and customer name
     */
    private Booking findBooking(String bookingNumber, String firstName, String lastName) {
        return bookingRepository.
                findBookingBy(bookingNumber, firstName, lastName)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    /**
     * Finds booking by number; maps to detail; throws if missing
     */
    @Transactional(readOnly = true)
    public @Nullable HotelBookingDetail findBooking(String bookingNumber) {
        log.info("Finding booking by number {}", bookingNumber);

        var booking = bookingRepository
                .findByBookingNumber(bookingNumber.trim())
                .map(mapper::bookingToHotelBookingDetail)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        log.info("Found booking {}", booking);

        return booking;
    }

    public List<HotelBookingDetail> getBookings() {
        return bookingRepository
                .findAll()
                .stream()
                .map(mapper::bookingToHotelBookingDetail).toList();
    }

    @Transactional
    public void cancelBooking(String bookingNumber, String firstName, String lastName) {
        var booking = findBooking(bookingNumber, firstName, lastName);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Transactional
    public void changeBookingRoomType(String bookingNumber, String firstName, String lastName, String roomType) {
        RoomType updatedRoomType = RoomType.valueOf(roomType);
        var booking = findBooking(bookingNumber, firstName, lastName);
        booking.setRoomType(updatedRoomType);
        bookingRepository.save(booking);
    }
}
