package rs.siriusxi.hbca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.siriusxi.hbca.domain.Booking;

import java.util.Optional;

/**
 * This interface provides repository access for {@link Booking} entities.
 * It extends the {@link JpaRepository} to inherit standard CRUD operations
 * and provides custom query methods for more specific operations.
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
