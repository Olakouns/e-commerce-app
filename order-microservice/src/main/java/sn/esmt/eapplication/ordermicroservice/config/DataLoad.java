package sn.esmt.eapplication.ordermicroservice.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import sn.esmt.eapplication.ordermicroservice.entities.Order;
import sn.esmt.eapplication.ordermicroservice.entities.enums.OrderStatus;
import sn.esmt.eapplication.ordermicroservice.entities.enums.PaymentMethod;
import sn.esmt.eapplication.ordermicroservice.repositories.OrderRepository;
import sn.esmt.eapplication.ordermicroservice.services.OrderService;

@Configuration
@RequiredArgsConstructor
public class DataLoad {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @PostConstruct
    public void loadOrders() {
        System.err.println("Loading orders..." + orderRepository.count().block());


//        System.out.println(reactiveMongoTemplate.findById("656311911ac046621c56f1e2", Order.class).block());
//         orderRepository.deleteAll().thenMany(
//                 Flux.just(
//                         new Order(null, 1000, null, null, null, OrderStatus.NEW, PaymentMethod.CREDIT_CARD),
//                         new Order(null, 1000, null, null, null, OrderStatus.NEW, PaymentMethod.CREDIT_CARD)
//                 )
//         ).flatMap(orderRepository::save).subscribe();
    }
}
