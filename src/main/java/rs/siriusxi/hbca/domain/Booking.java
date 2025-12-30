package rs.siriusxi.hbca.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Booking {
    private String hotelName;
    private String bookingNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Customer customer;
    private RoomType roomType; // e.g., Single, Double, Suite
    private int numberOfGuests;
    private BookingStatus bookingStatus;
}

