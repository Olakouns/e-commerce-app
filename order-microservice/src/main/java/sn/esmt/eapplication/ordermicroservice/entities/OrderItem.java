package sn.esmt.eapplication.ordermicroservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    private long productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double subTotal;
}
