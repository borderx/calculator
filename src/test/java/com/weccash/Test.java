package com.weccash;

import com.wecash.calculate.Calculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hh on 2017/2/27.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String expression = "3+5*2@2";
        Map<String, BigDecimal> params = new HashMap<String, BigDecimal>();
        params.put("consumeAmount", new BigDecimal(10));
        params.put("rate", new BigDecimal(0.5));
        params.put("periods", new BigDecimal(5));
        BigDecimal result = new Calculator().calculate(expression, params);
        System.out.println(result.toString());
    }
}
