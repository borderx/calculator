package com.wecash.calculate;

import java.math.BigDecimal;

/**
 * Created by hh on 15/7/8.
 */
public enum Operator {
    Add {
        public String getSymbol() {
            return "+";
        }

        public int getPri() {
            return 4;
        }

        public int getOpCnt() {
            return 2;
        }

        public BigDecimal compute(BigDecimal... ops) {
            return ops[0].add(ops[1]);
        }
    }, Minus {
        public String getSymbol() {
            return "-";
        }

        public int getPri() {
            return 4;
        }

        public int getOpCnt() {
            return 2;
        }

        public BigDecimal compute(BigDecimal... ops) {
            if(ops.length == 1) {
                return ops[0].negate();
            }
            return ops[0].subtract(ops[1]);
        }
    }, Multiply {
        public String getSymbol() {
            return "*";
        }

        public int getPri() {
            return 3;
        }

        public int getOpCnt() {
            return 2;
        }

        public BigDecimal compute(BigDecimal... ops) {
            return ops[0].multiply(ops[1]);
        }
    }, Divide {
        public String getSymbol() {
            return "/";
        }

        public int getPri() {
            return 3;
        }

        public int getOpCnt() {
            return 2;
        }

        public BigDecimal compute(BigDecimal... ops) {
            return ops[0].divide(ops[1], 20, BigDecimal.ROUND_HALF_UP);
        }
    }, BraLeft {
        public String getSymbol() {
            return "(";
        }

        public int getPri() {
            return 1;
        }

        public int getOpCnt() {
            return 0;
        }

        public BigDecimal compute(BigDecimal... ops) {
            return new BigDecimal(0);
        }
    }, BraRight {
        public String getSymbol() {
            return ")";
        }

        public int getPri() {
            return 1;
        }

        public int getOpCnt() {
            return 0;
        }

        public BigDecimal compute(BigDecimal... ops) {
            return new BigDecimal(0);
        }
    }, Mod {
        public String getSymbol() {
            return "%";
        }

        public int getPri() {
            return 3;
        }

        public int getOpCnt() {
            return 2;
        }

        public BigDecimal compute(BigDecimal... ops) {
            return ops[0].divideAndRemainder(ops[1])[1];
        }
    }, Power {
        public String getSymbol() {
            return "@";
        }

        public int getPri() {
            return 1;
        }

        public int getOpCnt() {
            return 2;
        }

        public BigDecimal compute(BigDecimal... ops) {
            double arg1 = ops[0].doubleValue();
            double arg2 = ops[1].doubleValue();
            double res = Math.pow(arg1, arg2);
            return BigDecimal.valueOf(res);
        }
    };

    public abstract String getSymbol();

    public abstract int getPri();

    public abstract int getOpCnt();

    public abstract BigDecimal compute(BigDecimal... ops);


    public static Operator toOperator(char op) {
        for(Operator o : values()) {
            if(o.getSymbol().equals(String.valueOf(op))) {
                return o;
            }
        }
        return null;
    }
}
