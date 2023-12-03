package sn.esmt.eapplication.ordermicroservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import sn.esmt.eapplication.ordermicroservice.dto.ApiResponse;
import sn.esmt.eapplication.ordermicroservice.services.OrderService;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
//    public Mono<ApiResponse> createOrder(String customerId, OrdersDto ordersDto) {
    public Mono<ApiResponse> createOrder() {
        return this.orderService.createOrder("customerId", null);
    }
}
