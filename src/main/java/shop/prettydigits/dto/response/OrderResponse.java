package shop.prettydigits.dto.response;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/24/2024 10:31 AM
@Last Modified 6/24/2024 10:31 AM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import shop.prettydigits.constant.order.OrderStatus;
import shop.prettydigits.model.Address;
import shop.prettydigits.model.User;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;


@Data
public class OrderResponse implements Serializable {

    private String id;

    private Long userId;

    private Set<OrderItemResponse> orderItems;

    private Address shippingAddress;

    private Double grandTotal;

    private String paymentType;

    private String midtransTransactionId;

    private OrderStatus status;

    private String snapToken;

    private ZonedDateTime createdAt;

    private ZonedDateTime expiredAt;

    private ZonedDateTime updatedAt;

    @JsonProperty("user")
    public void setUser(User user) {
        this.userId = user.getUserId();
    }
}
