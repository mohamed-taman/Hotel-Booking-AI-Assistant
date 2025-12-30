package rs.siriusxi.hbca.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import rs.siriusxi.hbca.domain.BookingStatus;
import rs.siriusxi.hbca.domain.RoomType;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record HotelBookingDetails(
        String bookingNumber,
        String firstName,
        String lastName,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BookingStatus bookingStatus,
        String hotelName,
        RoomType roomType,
        int numberOfGuests) {
}
