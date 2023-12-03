package sn.esmt.eapplication.ordermicroservice.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import sn.esmt.eapplication.ordermicroservice.entities.Order;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}
