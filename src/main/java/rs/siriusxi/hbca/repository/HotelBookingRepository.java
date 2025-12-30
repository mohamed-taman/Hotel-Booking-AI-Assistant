package rs.siriusxi.hbca.repository;

import lombok.Getter;
import lombok.Setter;
import rs.siriusxi.hbca.domain.Booking;
import rs.siriusxi.hbca.domain.Customer;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class HotelBookingRepository {
    private List<Customer> customers = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
}
