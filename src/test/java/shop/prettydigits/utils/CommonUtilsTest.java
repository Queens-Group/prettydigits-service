package shop.prettydigits.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilsTest {

    @Test
    void givenPhoneNumberShouldFormatPhoneNumberCorrectly() {
        String input1 = "085296223972";
        String input2 = "6285296223972";

        assertEquals("6285296223972", CommonUtils.formatPhoneNumber(input1));
        assertEquals("6285296223972", CommonUtils.formatPhoneNumber(input2));
    }
}