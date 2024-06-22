package shop.prettydigits.model;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 10:52 AM
@Last Modified 6/22/2024 10:52 AM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "order_item")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonBackReference
    private Order order;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "created_at")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}
