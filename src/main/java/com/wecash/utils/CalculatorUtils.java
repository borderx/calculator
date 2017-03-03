package com.wecash.utils;

import java.util.regex.Pattern;

/**
 * Created by hh on 2017/3/1.
 */
public class CalculatorUtils {
    public static boolean isNumber(String str) {
        String regix = "-?[0-9]*\\.?[0-9]*";
        return Pattern.matches(regix, str);
    }

    public static void main(String[] args) {
        System.out.println(CalculatorUtils.isNumber("-3526521."));
    }
}
