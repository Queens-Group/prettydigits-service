package shop.prettydigits.model;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 1:21 AM
@Last Modified 6/22/2024 1:21 AM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "address")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private User user;

    @Column(nullable = false)
    @NotBlank(message = "province must not be blank")
    private String province;

    @Column(nullable = false)
    @NotBlank(message = "district must not be blank")
    private String district;

    @Column(name = "sub_district", nullable = false)
    @NotBlank(message = "subDistrict must not be blank")
    private String subDistrict;

    @Column(nullable = false)
    @NotBlank(message = "village must not be blank")
    private String village;

    @Column(nullable = false)
    @NotBlank(message = "zipCode must not be blank")
    private String zipCode;

    @Column(nullable = false)
    @NotBlank(message = "please provide address's details")
    private String details;

    @Column(name = "created_at")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s %s", details, village, subDistrict, district,province, zipCode);
    }
}
