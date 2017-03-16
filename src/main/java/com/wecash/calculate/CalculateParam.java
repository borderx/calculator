package com.wecash.calculate;

import java.io.Serializable;

/**
 * Created by hh on 2017/3/7.
 */
public class CalculateParam implements Serializable {
    //算法表达式
    private String expression;
    //输入参数
    private String params;
    //计算精度
    private Integer scale;
    //小数位数保留方式
    private Integer roundingMode;
    //计算类型
    private CalculateType calculateType;

    public CalculateParam() {
    }

    public CalculateParam(String expression, String params, CalculateType calculateType) {
        this.expression = expression;
        this.params = params;
        this.calculateType = calculateType;
    }

    public CalculateParam(String expression, String params, Integer scale, Integer roundingMode, CalculateType calculateType) {
        this.expression = expression;
        this.params = params;
        this.scale = scale;
        this.roundingMode = roundingMode;
        this.calculateType = calculateType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getRoundingMode() {
        return roundingMode;
    }

    public void setRoundingMode(Integer roundingMode) {
        this.roundingMode = roundingMode;
    }

    public CalculateType getCalculateType() {
        return calculateType;
    }

    public void setCalculateType(CalculateType calculateType) {
        this.calculateType = calculateType;
    }
}
