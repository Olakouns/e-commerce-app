package sn.esmt.eapplication.productmicroservice.entities;


import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "stocks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantityInEnStock;
    private String emplacement;

    @ManyToOne
    private Product product;
}
