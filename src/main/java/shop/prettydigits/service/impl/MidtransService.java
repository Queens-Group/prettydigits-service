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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.prettydigits.config.properties.AppProperties;
import shop.prettydigits.constant.order.OrderStatus;
import shop.prettydigits.dto.midtrans.MidtransItemDetails;
import shop.prettydigits.dto.midtrans.MidtransNotification;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.model.*;
import shop.prettydigits.repository.OrderRepository;
import shop.prettydigits.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MidtransService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final MidtransSnapApi midtransSnapApi;

    private final AppProperties appProperties;


    @Autowired
    public MidtransService(OrderRepository orderRepository, ProductRepository productRepository, MidtransSnapApi midtransSnapApi, AppProperties appProperties) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.midtransSnapApi = midtransSnapApi;
        this.appProperties = appProperties;
    }


    @Transactional(rollbackFor = Exception.class)
    @Modifying
    public ApiResponse<Void> handleNotification(JSONObject payload) {
        MidtransNotification notification = new MidtransNotification(payload);
        if (!isValidSignature(notification)) {
            return ApiResponse.<Void>builder()
                    .code(403)
                    .message("invalid signature")
                    .build();
        }

        String transactionId = notification.getTransactionId();
        String transactionStatus = notification.getTransactionStatus();
        String orderId = notification.getOrderId();
        String fraudStatus = notification.getFraudStatus();
        String paymentType = notification.getPaymentType();

        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            return ApiResponse.setSuccess("order not found");
        }

        Order orderToUpdate = order.get();
        if (isPaymentSuccessful(transactionStatus, fraudStatus)) {
            orderToUpdate.setStatus(OrderStatus.PAID);
            orderToUpdate.setMidtransTransactionId(transactionId);
            orderToUpdate.setPaymentType(paymentType);

        } else if (!"pending".equals(transactionStatus)) {
            orderToUpdate.setStatus(OrderStatus.CANCELLED);
            orderToUpdate.setMidtransTransactionId(transactionId);
            orderToUpdate.setPaymentType(paymentType);

            List<Product> products = orderToUpdate.getOrderItems().stream().map(item -> {
                Product p = item.getProduct();
                p.setIsAvailable(true);
                return p;
            }).toList();
            productRepository.saveAllAndFlush(products);

        }
        orderRepository.save(orderToUpdate);
        return ApiResponse.setSuccess();
    }

    private boolean isPaymentSuccessful(String transactionStatus, String fraudStatus) {
        return ("capture".equals(transactionStatus) && "accept".equals(fraudStatus)) || "settlement".equals(transactionStatus);
    }

    public boolean isValidSignature(MidtransNotification notification) {
        String orderId = notification.getOrderId();
        String statusCode = notification.getStatusCode();
        String grossAmount = notification.getGrossAmount();
        String serverKey = appProperties.getMIDTRANS_SERVER_KEY();
        String signature = Sha512DigestUtils.shaHex(orderId + statusCode + grossAmount + serverKey);

        return signature.equals(notification.getSignatureKey());
    }

    public String createSnapToken(Order order, Address address) throws MidtransError {
        User user = order.getUser();
        List<MidtransItemDetails> itemDetails = order.getOrderItems()
                .stream()
                .map(this::mapToMidtransItemDetails)
                .toList();

        Map<String, Object> shippingAddress = constructShippingAddress(address, user);
        JSONObject customerDetails = new JSONObject();
        customerDetails.put("phone", user.getPhone());
        customerDetails.put("first_name", user.getFullName());
        customerDetails.put("shipping_address", shippingAddress);
        customerDetails.put("billing_address", shippingAddress);


        Map<String, Object> transactionsDetails = new HashMap<>();
        transactionsDetails.put("order_id", order.getId());
        transactionsDetails.put("gross_amount", order.getGrandTotal().intValue());

        Map<String, Object> expiry = new HashMap<>();
        expiry.put("duration", appProperties.getMIDTRANS_EXPIRY_DURATION());
        expiry.put("unit", appProperties.getMIDTRANS_EXPIRY_UNIT());

        Map<String, Object> pageExpiry = new HashMap<>();
        pageExpiry.put("duration", appProperties.getMIDTRANS_EXPIRY_DURATION());
        pageExpiry.put("unit", "MINUTES");

        Map<String, Object> payload = new HashMap<>();
        payload.put("customer_details", customerDetails);
        payload.put("transaction_details", transactionsDetails);
        payload.put("item_details", itemDetails);
        payload.put("page_expiry", pageExpiry);
        payload.put("expiry", expiry);

        return midtransSnapApi.createTransactionToken(payload);
    }

    private Map<String, Object> constructShippingAddress(Address address, User user) {
        Map<String, Object> shippingAddress = new HashMap<>();
        shippingAddress.put("shipping_address", address.toString());
        shippingAddress.put("city", address.getDistrict());
        shippingAddress.put("country_code", "IDN");
        shippingAddress.put("postal_code", address.getZipCode());
        shippingAddress.put("phone", user.getPhone());
        shippingAddress.put("first_name", user.getFullName());
        return shippingAddress;
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
