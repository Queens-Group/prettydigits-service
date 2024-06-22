package shop.prettydigits.config.constant;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 9:44 PM
@Last Modified 6/21/2024 9:44 PM
Version 1.0
*/

public class Route {




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

}
