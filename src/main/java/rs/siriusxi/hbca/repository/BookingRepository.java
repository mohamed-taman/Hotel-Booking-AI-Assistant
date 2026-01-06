package rs.siriusxi.hbca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.siriusxi.hbca.domain.Booking;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingNumberAndCustomerFirstNameIgnoreCaseAndCustomerLastNameIgnoreCase(
            String bookingNumber, String firstName, String lastName);
}
