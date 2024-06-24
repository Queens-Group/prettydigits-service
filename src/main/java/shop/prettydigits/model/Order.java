package shop.prettydigits.model;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 10:50 AM
@Last Modified 6/22/2024 10:50 AM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import shop.prettydigits.constant.order.OrderStatus;

import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonManagedReference
    private Set<OrderItem> orderItems;

    @ManyToOne
    private Address shippingAddress;


    @Column(name = "grand_total")
    private Double grandTotal;

    @Column(name = "payment_type", length = 50)
    private String paymentType;

    @Column(name = "midtrans_transaction_id")
    private String midtransTransactionId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "snap_token")
    private String snapToken;

    @Column(name = "created_at")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "expired_at")
    private ZonedDateTime expiredAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;


}
