package shop.prettydigits.dto.request;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 2:03 PM
@Last Modified 6/22/2024 2:03 PM
Version 1.0
*/

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDTO {

    @NotNull
    @Min(0)
    private Integer productId;
}
