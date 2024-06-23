package shop.prettydigits.dto.midtrans;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/24/2024 12:13 AM
@Last Modified 6/24/2024 12:13 AM
Version 1.0
*/

import org.json.JSONObject;

import java.util.Optional;


public class MidtransNotification {

    private final JSONObject payload;
    public MidtransNotification(JSONObject payload) {
        this.payload = payload;
    }

    public String getTransactionId() {
        return payload.getString("transaction_id");
    }

    public String getTransactionStatus() {
        return payload.getString("transaction_status");
    }

    public String getFraudStatus() {
        return Optional.ofNullable(payload.getString("fraud_status")).orElse("");
    }

    public String getOrderId() {
        return payload.getString("order_id");
    }

    public String getPaymentType() {
        return payload.getString("payment_type");
    }

    public String getStatusCode() {
        return payload.getString("status_code");
    }

    public String getGrossAmount() {
        return payload.getString("gross_amount");
    }

    public String getSignatureKey() {
        return payload.getString("signature_key");
    }
}
