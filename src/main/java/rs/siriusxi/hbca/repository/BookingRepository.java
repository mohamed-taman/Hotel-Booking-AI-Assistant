package rs.siriusxi.hbca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.siriusxi.hbca.domain.Booking;

import java.util.Optional;


/**
 * BookingRepository is a Spring Data repository interface for managing
 * {@link Booking} entities. It extends {@link JpaRepository}, providing
 * built-in CRUD operations and JPA query capabilities. This interface serves
 * as the central access point for database operations related to bookings.
 *
 * The following additional query methods are defined:
 *
 * - {@code findBookingBy(String, String, String)}: Fetches a booking based on
 *   booking number, customer's first name, and last name.
 *
 * - {@code findByBookingNumber(String)}: Retrieves a booking by its unique
 *   booking number.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
            SELECT b FROM Booking b
            WHERE b.bookingNumber = ?1
            AND upper(b.customer.firstName) = UPPER(?2)
            AND upper(b.customer.lastName) = UPPER(?3)
            """)
    Optional<Booking> findBookingBy(String bookingNumber,
                                    String firstName,
                                    String lastName);

    Optional<Booking> findByBookingNumber(String bookingNumber);
}
