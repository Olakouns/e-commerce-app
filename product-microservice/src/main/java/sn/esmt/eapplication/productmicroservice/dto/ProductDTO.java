package sn.esmt.eapplication.productmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    private String name;
    private String description;
    private double price;
    private double quantityInStock;
}
