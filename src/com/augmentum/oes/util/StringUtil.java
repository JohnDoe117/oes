package com.augmentum.oes.util;

public class StringUtil {
    public static String doWithNll(Object o) {
        if (o == null) {
            return "";
        } else {
            String returnValu = o.toString();
            if (returnValu.equalsIgnoreCase("null")) {
                return "";
            } else {
                return returnValu.trim();
            }
        }
    }

    public static Boolean isEmpty(String string) {
        if (string == null || string.length() == 0) {
            return true;
        }
        return false;
    }
}
