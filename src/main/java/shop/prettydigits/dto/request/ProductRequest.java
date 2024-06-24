package shop.prettydigits.dto.request;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 11:33 AM
@Last Modified 6/22/2024 11:33 AM
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductRequest {

    @NotBlank(message = "number must not be blank")
    private String number;


    private LocalDate validityPeriod;

    private String description;

    private Boolean isAvailable;

    private String type;
}
