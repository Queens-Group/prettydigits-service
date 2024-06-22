package shop.prettydigits.model;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 10:59 AM
@Last Modified 6/22/2024 10:59 AM
Version 1.0
*/

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "product")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false, unique = true)
    private String number;

    @Column
    private String description;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column
    private String type;

    @Column(name = "validity_period")
    private LocalDate validityPeriod;

    @Column(name = "created_at")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}


