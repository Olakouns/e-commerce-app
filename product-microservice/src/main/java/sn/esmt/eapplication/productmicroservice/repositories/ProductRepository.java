package sn.esmt.eapplication.productmicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.eapplication.productmicroservice.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
