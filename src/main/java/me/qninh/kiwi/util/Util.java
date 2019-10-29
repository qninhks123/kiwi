package me.qninh.kiwi.util;

import com.google.common.base.CaseFormat;

public class Util {

    public static String toSnakeCase(String str) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }
}