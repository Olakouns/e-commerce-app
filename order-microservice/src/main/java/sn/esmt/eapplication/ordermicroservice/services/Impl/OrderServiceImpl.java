package sn.esmt.eapplication.ordermicroservice.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import org.bson.json.JsonObject;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import sn.esmt.eapplication.ordermicroservice.dto.ApiResponse;
import sn.esmt.eapplication.ordermicroservice.dto.OrderItemDTO;
import sn.esmt.eapplication.ordermicroservice.dto.OrdersDto;
import sn.esmt.eapplication.ordermicroservice.entities.Customer;
import sn.esmt.eapplication.ordermicroservice.entities.Order;
import sn.esmt.eapplication.ordermicroservice.entities.OrderItem;
import sn.esmt.eapplication.ordermicroservice.entities.enums.OrderStatus;
import sn.esmt.eapplication.ordermicroservice.repositories.OrderRepository;
import sn.esmt.eapplication.ordermicroservice.services.OrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final WebClient.Builder webClientBuilder;
    private final Scheduler elasticScheduler;

    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    @Override
    public Mono<ResponseEntity<ApiResponse>> createOrder(String customerId, OrdersDto ordersDto) {
        // TODO: 11/26/2023 GET CUSTOMER INFO BY ID FROM USER-MICROSERVICE
        Mono<Customer> customer = webClientBuilder
                .build()
                .get()
                .uri("http://user-microservice/api/users/customer/" + customerId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .flatMap(body -> {
                            // TODO: 12/31/2023 get ONly details here and throw exception
                            System.err.println(body);
                            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, body));
                        }))
                .bodyToMono(Customer.class);

        Mono<ApiResponse> isAvailable = WebClient.builder()
                .filter(lbFunction)
                .build()
                .post()
                .uri("http://product-microservice/api/products/stock/checkAvailability")
                .bodyValue(ordersDto.getOrderItems())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .flatMap(body -> {
                            // TODO: 12/31/2023 get ONly details here and throw exception
                            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, body));
                        }))
                .bodyToMono(ApiResponse.class);

        return Mono.zip(customer, isAvailable)
                .flatMap(objects -> {


                    Customer customer1 = objects.getT1();
                    ApiResponse apiResponse = objects.getT2();

                    if (!apiResponse.isSuccess()) {
                        return Mono.just(new ResponseEntity<>(apiResponse, HttpStatus.NOT_ACCEPTABLE));
                    }


                    Order order = new Order();
                    order.setCustomer(customer1);
                    order.setOrderDate(new Date());
                    order.setOrderStatus(OrderStatus.NEW);
                    order.setPaymentMethod(ordersDto.getPaymentMethod());

                    List<OrderItem> orderItems = new ArrayList<>();

                    for (OrderItemDTO orderItem : ordersDto.getOrderItems()) {
                        OrderItem orderItem1 = OrderItem.builder()
                                .productName(orderItem.getProductName())
                                .productId(orderItem.getProductId())
                                .quantity(orderItem.getQuantity())
                                .unitPrice(orderItem.getUnitPrice())
                                .subTotal(orderItem.getUnitPrice() * orderItem.getQuantity())
                                .build();
                        orderItems.add(orderItem1);
                    }

                    order.setOrderItems(orderItems);
                    orderRepository.save(order);

                    // TODO: 1/2/2024 UPDATE PRODUCT STOCK

                    System.out.println(customer1);
                    System.out.println(apiResponse);
                    return Mono.just(ResponseEntity.ok().body(ApiResponse.builder().success(true).message("Order created successfully").build()));
                })
                .subscribeOn(elasticScheduler)
                .onErrorResume(throwable -> {
                    ApiResponse errorResponse = new ApiResponse();
                    errorResponse.setSuccess(false);
                    errorResponse.setMessage(throwable.getMessage());
                    return Mono.just(new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND));
                });
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
