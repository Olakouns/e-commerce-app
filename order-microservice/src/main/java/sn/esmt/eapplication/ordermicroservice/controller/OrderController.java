package sn.esmt.eapplication.ordermicroservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sn.esmt.eapplication.ordermicroservice.dto.ApiResponse;
import sn.esmt.eapplication.ordermicroservice.dto.OrdersDto;
import sn.esmt.eapplication.ordermicroservice.services.OrderService;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public Mono<ResponseEntity<ApiResponse>> createOrder(@RequestParam String customerId, @RequestBody OrdersDto ordersDto) {
        return this.orderService.createOrder(customerId, ordersDto);
    }
}
