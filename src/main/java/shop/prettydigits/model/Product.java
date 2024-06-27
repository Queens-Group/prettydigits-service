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
import org.hibernate.annotations.ColumnDefault;
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

    @Column(length = 20, nullable = false)
    private String number;

    @Column
    private String description;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column
    private String provider;

    @Column(nullable = false)
    @ColumnDefault("0.0")
    private Double price = 0.0;


    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "validity_period")
    private LocalDate validityPeriod;

    @Column(name = "created_at")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}


