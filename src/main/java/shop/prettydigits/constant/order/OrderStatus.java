package shop.prettydigits.constant.order;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/22/2024 10:55 AM
@Last Modified 6/22/2024 10:55 AM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {

    UNPAID, PAID, COMPLETED, CANCELLED, IN_PROGRESS;

    @JsonCreator
    public static OrderStatus forOrderStatus(String orderStatus) {
        for (OrderStatus x : OrderStatus.values()) {
            if (x.name().equalsIgnoreCase(orderStatus)) {
                return x;
            }
        }
        return null;
    }
}
