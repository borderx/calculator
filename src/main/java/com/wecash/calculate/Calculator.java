package com.wecash.calculate;


import com.alibaba.fastjson.JSON;
import com.wecash.utils.CalculatorUtils;

import java.math.BigDecimal;

import java.util.Map;
import java.util.Stack;

/**
 * Created by hh on 2017/2/27.
 */
public class Calculator {

    private Stack<String> numberStack;

    private Stack<Operator> operatorStack;

    private Map<String, BigDecimal> params;

    public Calculator() {
        numberStack = new Stack<String>();
        operatorStack = new Stack<Operator>();
    }

    public BigDecimal calculate(String expression, Map<String, BigDecimal> params) {
        this.params = params;
        scan(expression);
        while (!operatorStack.empty()) {
            Operator op = operatorStack.pop();
            int opCnt = op.getOpCnt();
            String[] args = new String[opCnt];
            for (int i = 0; i < opCnt; i++) {
                args[opCnt - i - 1] = numberStack.pop();
            }
            compute(op, args);
        }
        return params.get(numberStack.peek());
    }

    private void compute(Operator op, String[] args) {
        BigDecimal[] input = new BigDecimal[args.length];
        for(int i = 0; i < args.length; i++) {
            input[i] = params.get(args[i]);
        }
        String key = packageKey(op, args);
        params.put(packageKey(op, args), op.compute(input));
        numberStack.push(key);
        System.out.println("numberStack:" + JSON.toJSONString(numberStack)
                + ",operatorStack:" + JSON.toJSONString(operatorStack) + ",params:" + JSON.toJSONString(params));
    }

    private String packageKey(Operator op, String[] args) {
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
            Operator operator = Operator.toOperator(chr);
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

    private void newOperator(Operator operator) {
        if (operatorStack.empty()) {
            operatorStack.push(operator);
        } else if (operator.equals(Operator.BraLeft)) {
            operatorStack.push(operator);
        } else if (operator.equals(Operator.BraRight)) {
            Operator op = operatorStack.pop();
            while (!op.equals(Operator.BraLeft)) {
                int opCnt = op.getOpCnt();
                String[] args = new String[opCnt];
                for (int i = 0; i < opCnt; i++) {
                    args[opCnt - i - 1] = numberStack.pop();
                }
                compute(op, args);
                op = operatorStack.pop();
            }
        } else {
            if (operatorStack.peek().equals(Operator.BraLeft)) {
                operatorStack.push(operator);
            } else if (operatorStack.peek().getPri() > operator.getPri()) {
                operatorStack.push(operator);
            } else {
                Operator op = operatorStack.pop();
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
