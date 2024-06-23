package shop.prettydigits.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/23/2024 1:29 PM
@Last Modified 6/23/2024 1:29 PM
Version 1.0
*/

import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.prettydigits.dto.midtrans.MidtransItemDetails;
import shop.prettydigits.model.Order;
import shop.prettydigits.model.OrderItem;
import shop.prettydigits.model.Product;
import shop.prettydigits.model.User;
import shop.prettydigits.repository.OrderRepository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MidtransService {

    private final OrderRepository orderRepository;

    private final MidtransSnapApi midtransSnapApi;


    @Value("${midtrans.expiry.duration:5}")
    private String midtransExpiryDuration;
    @Value("${midtrans.expiry.unit:MINUTES}")
    private String midtransExpiryUnit;

    @Autowired
    public MidtransService(OrderRepository orderRepository, MidtransSnapApi midtransSnapApi) {
        this.orderRepository = orderRepository;
        this.midtransSnapApi = midtransSnapApi;
    }


    @Transactional
    public String createSnapToken(String orderId) throws MidtransError {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new MidtransError("order does not exist"));
        return createSnapToken(order);
    }


    public String createSnapToken(Order order) throws MidtransError {
        User user = order.getUser();
        List<MidtransItemDetails> itemDetails = order.getOrderItems()
                .stream()
                .map(this::mapToMidtransItemDetails)
                .toList();
        Map<String, String> customerDetails = new HashMap<>();
        customerDetails.put("phone", user.getPhone());
        customerDetails.put("first_name", user.getFullName());

        Map<String, Object> transactionsDetails = new HashMap<>();
        transactionsDetails.put("order_id", order.getId());
        transactionsDetails.put("gross_amount", order.getGrandTotal().intValue());

        Map<String, Object> expiry = new HashMap<>();
        ZonedDateTime paymentExpiry = order.getCreatedAt().plusMinutes(5);
        expiry.put("start_time", paymentExpiry.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss Z")));
        expiry.put("duration", midtransExpiryDuration);
        expiry.put("unit", midtransExpiryUnit);

        Map<String, Object> payload = new HashMap<>();
        payload.put("customer_details", customerDetails);
        payload.put("transaction_details", transactionsDetails);
        payload.put("item_details", itemDetails);
        payload.put("expiry", expiry);

        return midtransSnapApi.createTransactionToken(payload);
    }

    public MidtransItemDetails mapToMidtransItemDetails(OrderItem orderItem) {
            MidtransItemDetails item = new MidtransItemDetails();
            Product product = orderItem.getProduct();
            item.setId(String.valueOf(product.getId()));
            item.setName(product.getNumber());
            item.setBrand(product.getType());
            item.setQuantity(1);
            item.setPrice(product.getPrice().intValue());
            return item;

    }
}
