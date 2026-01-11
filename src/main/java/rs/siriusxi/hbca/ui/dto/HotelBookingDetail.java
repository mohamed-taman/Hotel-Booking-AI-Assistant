package rs.siriusxi.hbca.ui.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import rs.siriusxi.hbca.domain.BookingStatus;
import rs.siriusxi.hbca.domain.RoomType;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record HotelBookingDetail(
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
