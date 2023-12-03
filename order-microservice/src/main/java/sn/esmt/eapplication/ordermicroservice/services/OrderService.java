package sn.esmt.eapplication.ordermicroservice.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.esmt.eapplication.ordermicroservice.dto.ApiResponse;
import sn.esmt.eapplication.ordermicroservice.dto.OrdersDto;
import sn.esmt.eapplication.ordermicroservice.entities.Order;

public interface OrderService {
    Mono<ApiResponse> createOrder(String customerId, OrdersDto ordersDto);

    Mono<ApiResponse> placeOrder(String orderId);

    Mono<OrdersDto> getOrder(String orderId);

    Flux<OrdersDto> getCustomerOrder(String customerId);
}
