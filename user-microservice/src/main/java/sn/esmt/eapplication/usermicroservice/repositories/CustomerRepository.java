package sn.esmt.eapplication.usermicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.eapplication.usermicroservice.entities.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserId(Long userId);
}
