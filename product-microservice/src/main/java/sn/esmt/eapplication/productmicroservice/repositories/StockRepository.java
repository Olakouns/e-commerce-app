package sn.esmt.eapplication.productmicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.eapplication.productmicroservice.entities.Product;
import sn.esmt.eapplication.productmicroservice.entities.Stock;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductId(long id);
}
