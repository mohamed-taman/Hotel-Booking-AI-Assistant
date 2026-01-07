package rs.siriusxi.hbca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.siriusxi.hbca.domain.Customer;

/**
 * CustomerRepository is a Spring Data repository interface for managing
 * {@link Customer} entities. It extends {@link JpaRepository}, providing
 * built-in CRUD operations and JPA query capabilities. This interface is a
 * central access point for database operations related to customers.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
