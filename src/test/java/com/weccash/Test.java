package com.weccash;

import com.alibaba.fastjson.JSON;
import com.wecash.calculate.CalculateParam;
import com.wecash.calculate.CalculateType;
import com.wecash.calculate.Calculator;
import com.wecash.calculate.arithmetic.ArithmeticCalculator;

import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hh on 2017/2/27.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String expression = "25.3";
        Map<String, BigDecimal> params = new HashMap<String, BigDecimal>();
        params.put("consumeAmount", new BigDecimal(10));
        params.put("rate", new BigDecimal(0.5));
        params.put("periods", new BigDecimal(5));

        CalculateParam calculateParam = new CalculateParam(expression, JSON.toJSONString(params), 3, BigDecimal.ROUND_HALF_UP, CalculateType.Arithmetic);

        Calculator calculator = new ArithmeticCalculator();
        calculator.clear();
        String result = calculator.calculate(calculateParam);
        System.out.println(result);
        //31.284271247461905
    }
}
