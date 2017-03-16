package com.wecash.calculate.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wecash.calculate.CalculateParam;
import com.wecash.calculate.Calculator;
import org.apache.commons.lang.StringUtils;

/**
 * Created by hh on 2017/3/7.
 */
public class SimpleConfigCalculator implements Calculator{

    public static final String DEFAULT_NULL_VALUE = "null";

    public void clear() {
    }

    public String calculate(CalculateParam calculateParam) {
        JSONObject params = JSON.parseObject(calculateParam.getParams());
        String result = params.getString(calculateParam.getExpression());
        return packageResult(result);
    }

    private String packageResult(String result) {
        return StringUtils.isBlank(result) ? DEFAULT_NULL_VALUE : result;
    }

}
