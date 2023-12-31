package sn.esmt.eapplication.ordermicroservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    @NotBlank(message = "productId is required")
    private long productId;
    private String productName;
    @NotBlank(message = "quantity is required")
    @Positive(message = "quantity must be greater than 0")
    private int quantity;
    private double unitPrice;
    private double subTotal;
}
