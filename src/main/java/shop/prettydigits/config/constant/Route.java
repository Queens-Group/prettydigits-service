package shop.prettydigits.config.constant;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:44 PM
@Last Modified 6/21/2024 9:44 PM
Version 1.0
*/

public class Route {


    public static final String CART_ID = "/{cartId}";
    public static final String ITEM_ID = "/{itemId}";
    public static final String ITEM = "/item";
    public static final String ORDERS = "/orders";
    public static final String CHECKOUT = "/checkout";
    public static final String MIDTRANS = "/midtrans";
    public static final String NOTIFICATION = "/notification";
    public static final String CHECK_EXPIRY = "/check-expiry";
    public static final String ORDER_ID = "/{orderId}";
    public static final String ADMIN = "/admin";
    public static final String UPDATE_STATUS = "/update-status";

    private Route() {}

    public static final String API_V1 = "/api/v1";
    public static final String AUTH = "/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";

    public static final String USER_INFO = "/userInfo";

    public static final String ADDRESS = "/address";

    public static final String ADMIN_PRODUCTS = "/admin/products" ;
    public static final String NEW_PRODUCT = "/new-product" ;
    public static final String REMOVE = "/remove";
    public static final String PRODUCT_ID_VAR = "/{productId}";

    public static final String PRODUCTS = "/products";

    public static final String AVAILABLE = "/available";

    public static final String CART = "/cart";

    public static final String ADD_ITEM = "/add-item";

}
