package com.borderx.calculate.arithmetic;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.borderx.calculate.CalculateParam;
import com.borderx.calculate.Calculator;
import com.borderx.utils.CalculatorUtils;

import java.math.BigDecimal;

import java.util.Stack;

/**
 * Created by hh on 2017/2/27.
 */
public class ArithmeticCalculator implements Calculator {
    public static final Integer DEFAULT_SCALE_LENGTH = 2;

    private Stack<String> numberStack;

    private Stack<ArithmeticOperator> operatorStack;

    private JSONObject params;

    public ArithmeticCalculator() {
        numberStack = new Stack<String>();
        operatorStack = new Stack<ArithmeticOperator>();
        params = new JSONObject();
    }

    public void clear() {
        this.numberStack.clear();
        this.operatorStack.clear();
        this.params.clear();
    }

    public String calculate(CalculateParam calculateParam) {
        this.params = JSON.parseObject(calculateParam.getParams());
        scan(calculateParam.getExpression());
        while (!operatorStack.empty()) {
            ArithmeticOperator op = operatorStack.pop();
            int opCnt = op.getOpCnt();
            String[] args = new String[opCnt];
            for (int i = 0; i < opCnt; i++) {
                args[opCnt - i - 1] = numberStack.pop();
            }
            compute(op, args);
        }
        return scale(calculateParam).toPlainString();
    }

    private BigDecimal scale(CalculateParam calculateParam) {
        int scale = calculateParam.getScale() == null ? DEFAULT_SCALE_LENGTH : calculateParam.getScale();
        int roundingMode = calculateParam.getRoundingMode() == null ? BigDecimal.ROUND_HALF_UP : calculateParam.getRoundingMode();
        return new BigDecimal(params.get(numberStack.peek()).toString()).setScale(scale, roundingMode);
    }

    private void compute(ArithmeticOperator op, String[] args) {
        BigDecimal[] input = new BigDecimal[args.length];
        for(int i = 0; i < args.length; i++) {
            input[i] = new BigDecimal(params.get(args[i]).toString());
        }
        String key = packageKey(op, args);
        params.put(packageKey(op, args), op.compute(input));
        numberStack.push(key);
        System.out.println("numberStack:" + JSON.toJSONString(numberStack)
                + ",operatorStack:" + JSON.toJSONString(operatorStack) + ",params:" + JSON.toJSONString(params));
    }

    private String packageKey(ArithmeticOperator op, String[] args) {
        StringBuilder key = new StringBuilder(op.getSymbol());
        for(String str : args) {
            key.append("_").append(str);
        }
        return key.toString();
    }

    private void scan(String expression) {
        StringBuilder word = new StringBuilder(20);
        for (int i = 0; i < expression.length(); i++) {
            final char chr = expression.charAt(i);
            ArithmeticOperator operator = ArithmeticOperator.toOperator(chr);
            if (null != operator) {
                boolean isNewAdded = newNumber(word);
                newOperator(operator);
            } else if (' ' == chr) {
                newNumber(word);
                continue;
            } else {
                word.append(chr);
            }
        }
        newNumber(word);
    }

    private boolean newNumber(StringBuilder word) {
        boolean newAdded = false;
        if (word.length() > 0) {
            if(CalculatorUtils.isNumber(word.toString())) {
                String key = CalculatorUtils.getRandomKey();
                params.put(key, new BigDecimal(word.toString()));
                numberStack.push(key);
            } else {
                numberStack.push(word.toString());
            }
            word.delete(0, word.length());
            newAdded = true;
        }
        return newAdded;
    }

    private void newOperator(ArithmeticOperator operator) {
        if (operatorStack.empty()) {
            operatorStack.push(operator);
        } else if (operator.equals(ArithmeticOperator.BraLeft)) {
            operatorStack.push(operator);
        } else if (operator.equals(ArithmeticOperator.BraRight)) {
            ArithmeticOperator op = operatorStack.pop();
            while (!op.equals(ArithmeticOperator.BraLeft)) {
                int opCnt = op.getOpCnt();
                String[] args = new String[opCnt];
                for (int i = 0; i < opCnt; i++) {
                    args[opCnt - i - 1] = numberStack.pop();
                }
                compute(op, args);
                op = operatorStack.pop();
            }
        } else {
            if (operatorStack.peek().equals(ArithmeticOperator.BraLeft)) {
                operatorStack.push(operator);
            } else if (operatorStack.peek().getPri() > operator.getPri()) {
                operatorStack.push(operator);
            } else {
                ArithmeticOperator op = operatorStack.pop();
                int opCnt = op.getOpCnt();
                String[] args = new String[opCnt];
                for (int i = 0; i < opCnt; i++) {
                    args[opCnt - i - 1] = numberStack.pop();
                }
                compute(op, args);
                newOperator(operator);
            }
        }
    }
}
