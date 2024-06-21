package shop.prettydigits.utils;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 11:55 PM
@Last Modified 6/21/2024 11:55 PM
Version 1.0
*/

public class CommonUtils {

    private CommonUtils() {}

    public static String formatPhoneNumber(String inputPhone) {
        String result = inputPhone;
        if (inputPhone.startsWith("08")) {
            result = "62" + inputPhone.substring(1);
        }
        return result;
    }
}
