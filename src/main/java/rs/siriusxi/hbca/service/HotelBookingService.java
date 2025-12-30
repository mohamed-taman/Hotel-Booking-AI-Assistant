package rs.siriusxi.hbca.service;

import org.springframework.stereotype.Service;
import rs.siriusxi.hbca.domain.Booking;
import rs.siriusxi.hbca.domain.BookingStatus;
import rs.siriusxi.hbca.domain.Customer;
import rs.siriusxi.hbca.domain.RoomType;
import rs.siriusxi.hbca.repository.HotelBookingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelBookingService {

    private final HotelBookingRepository hotelBookingRepository;

    public HotelBookingService() {
        hotelBookingRepository = new HotelBookingRepository();
        initData();
    }

    public void initData() {
        List<String> firstNames = List.of("Jack", "Chloe", "Kim", "David", "Michelle");
        List<String> lastNames = List.of("Bauer", "O'Brian", "Bauer", "Palmer", "Dessler");
        List<String> hotelNames = List.of("Marriot", "Hilton", "Sheraton", "Westin", "Four Seasons");

        var customers = new ArrayList<Customer>();
        var bookings = new ArrayList<Booking>();

        for (int i = 0; i < firstNames.size(); i++) {
            var customer = new Customer(firstNames.get(i), lastNames.get(i));

            RoomType roomType = RoomType.values()[i % RoomType.values().length];
            String hotelName = hotelNames.get(i);
            Booking booking = new Booking(hotelName, "10" + (i + 1),
                    LocalDate.now().plusDays(2 * i), LocalDate.now().plusDays(2 * i + 2),
                    customer, roomType, 2, BookingStatus.CONFIRMED);

            customers.add(customer);
            bookings.add(booking);
        }

        hotelBookingRepository.setCustomers(customers);
        hotelBookingRepository.setBookings(bookings);
    }

    public List<HotelBookingDetails> getBookings() {
        return hotelBookingRepository.getBookings().stream().map(this::toHotelBookingDetails).toList();
    }

    /**
     * Finds booking by number and customer name
     */
    private Booking findBooking(String bookingNumber, String firstName, String lastName) {
        // Filters bookings by number and first name
        return hotelBookingRepository.getBookings().stream()
                .filter(b -> b.getBookingNumber().equalsIgnoreCase(bookingNumber))
                .filter(b -> b.getCustomer().getFirstName().equalsIgnoreCase(firstName))
                .filter(b -> b.getCustomer().getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public void cancelBooking(String bookingNumber, String firstName, String lastName) {
        var booking = findBooking(bookingNumber, firstName, lastName);
        booking.setBookingStatus(BookingStatus.CANCELLED);
    }

    public void roomTypeChangeRequest(String bookingNumber, String firstName, String lastName, String roomType) {
        RoomType updatedRoomType = RoomType.valueOf(roomType);
        var booking = findBooking(bookingNumber, firstName, lastName);
        booking.setRoomType(updatedRoomType);

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
