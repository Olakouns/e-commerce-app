package sn.esmt.eapplication.productmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsAvailableDTO {
    private long productId;
    private int quantity;
}
