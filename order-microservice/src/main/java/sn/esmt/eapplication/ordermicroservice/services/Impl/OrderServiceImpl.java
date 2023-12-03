package sn.esmt.eapplication.ordermicroservice.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import sn.esmt.eapplication.ordermicroservice.dto.ApiResponse;
import sn.esmt.eapplication.ordermicroservice.dto.OrdersDto;
import sn.esmt.eapplication.ordermicroservice.entities.Customer;
import sn.esmt.eapplication.ordermicroservice.entities.Order;
import sn.esmt.eapplication.ordermicroservice.repositories.OrderRepository;
import sn.esmt.eapplication.ordermicroservice.services.OrderService;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final WebClient.Builder webClientBuilder;
    private final Scheduler elasticScheduler;

    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    @Override
    public Mono<ApiResponse> createOrder(String customerId, OrdersDto ordersDto) {
        // TODO: 11/26/2023 GET CUSTOMER INFO BY ID FROM USER-MICROSERVICE
        Mono<Object> customer = webClientBuilder
                .build()
                .get()
                .uri("http://product-microservice/api/products/all")
                .retrieve()
                .bodyToMono(Object.class);

        Mono<Object> customer2 = WebClient.builder()
                .filter(lbFunction)
                .build()
                .get()
                .uri("http://product-microservice/api/products/all")
                .retrieve()
                .bodyToMono(Object.class);




        // TODO: 11/26/2023 FOR EACH PRODUCT IN ORDER_ITEMS, GET PRODUCT INFO FROM PRODUCT-MICROSERVICE AND CHECK IF PRODUCT IS AVAILABLE IN STOCK

        // TODO: 11/26/2023 CALCULATE SUB_TOTAL FOR EACH ORDER_ITEM

        // TODO: 11/26/2023 CALCULATE TOTAL_AMOUNT FOR ORDER

        // TODO: 11/26/2023 SAVE ORDER IN MONGODB
//        return null;
        return Mono.zip(customer, customer2)
                .flatMap(objects -> {
                    System.out.println(objects.getT1());
                    System.out.println(objects.getT2());
                    return Mono.just(ApiResponse.builder().success(true).message("Order created successfully").build());
                })
                .subscribeOn(elasticScheduler);
    }

    @Override
    public Mono<ApiResponse> placeOrder(String orderId) {
        return null;
    }

    @Override
    public Mono<OrdersDto> getOrder(String orderId) {
        return null;
    }

    @Override
    public Flux<OrdersDto> getCustomerOrder(String customerId) {
        return null;
    }
}
