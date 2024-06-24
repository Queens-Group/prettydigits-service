package shop.prettydigits.dto.request;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/24/2024 2:14 PM
@Last Modified 6/24/2024 2:14 PM
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import shop.prettydigits.constant.order.OrderStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
public class UpdateOrderStatusReq {

    private String adminUsername;

    @NotBlank
    private String orderId;


    private ZonedDateTime updatedAt;

    @NotNull
    private OrderStatus status;

    public UpdateOrderStatusReq() {
        this.updatedAt = ZonedDateTime.now().withZoneSameInstant(ZoneId.systemDefault());
    }


}
