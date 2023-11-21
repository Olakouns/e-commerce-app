package sn.esmt.eapplication.productmicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.eapplication.productmicroservice.entities.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
