package sn.esmt.eapplication.ordermicroservice.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.esmt.eapplication.ordermicroservice.entities.enums.OrderStatus;
import sn.esmt.eapplication.ordermicroservice.entities.enums.PaymentMethod;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDto {
    @NotBlank(message = "customerId is required")
    private long customerId;
    private double totalAmount;
    @NotEmpty(message = "orderItems is required")
    @Valid
    private List<OrderItemDTO> orderItems;
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
}

