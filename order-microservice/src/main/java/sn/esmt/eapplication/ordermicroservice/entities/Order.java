package sn.esmt.eapplication.ordermicroservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import sn.esmt.eapplication.ordermicroservice.entities.enums.OrderStatus;
import sn.esmt.eapplication.ordermicroservice.entities.enums.PaymentMethod;

import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private String id;
    private double totalAmount;
    private Customer customer;
    private List<OrderItem> orderItems;
    private Date orderDate;
    @Field(targetType = FieldType.STRING)
    private OrderStatus orderStatus;
    @Field(targetType = FieldType.STRING)
    private PaymentMethod paymentMethod;
}

