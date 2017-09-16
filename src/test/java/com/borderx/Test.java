package com.borderx;

import com.alibaba.fastjson.JSON;
import com.borderx.calculate.CalculateParam;
import com.borderx.calculate.CalculateType;
import com.borderx.calculate.Calculator;
import com.borderx.calculate.arithmetic.ArithmeticCalculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hh on 2017/2/27.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String expression = "1-1000/(((1000/2+1000*0.043)-(1000/2+1000*0.043)*(1+0.115/12)@(0-2))/(0.115/12))";
        Map<String, BigDecimal> params = new HashMap<String, BigDecimal>();
        params.put("useMonthRate", new BigDecimal("0.1"));
        params.put("power", new BigDecimal(2));
        params.put("rate", new BigDecimal(0.5));
        params.put("consumePeriods", new BigDecimal(5));

        CalculateParam calculateParam = new CalculateParam(expression, JSON.toJSONString(params), 6, BigDecimal.ROUND_HALF_UP, CalculateType.Arithmetic);

        Calculator calculator = new ArithmeticCalculator();
        calculator.clear();
        String result = calculator.calculate(calculateParam);
        System.out.println(result);
        //31.284271247461905
    }
}
