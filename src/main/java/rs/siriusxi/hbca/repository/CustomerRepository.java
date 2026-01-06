package rs.siriusxi.hbca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.siriusxi.hbca.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
