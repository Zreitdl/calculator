package com.example.calculator;

public class CheckedMultiply extends BinaryExpression {

    public CheckedMultiply(Actions first, Actions second) {
        super(first, second);
    }

    public int count(int a, int b) throws MyException{
        if (a> 0 && (b > Integer.MAX_VALUE / a || b < Integer.MIN_VALUE / a)) {
            throw new MyException("overflow");
        }
        if (a < -1 && (b > Integer.MIN_VALUE / a || b < Integer.MAX_VALUE / a)) {
            throw new MyException("overflow");
        }
        if (a == -1 && b == Integer.MIN_VALUE) {
            throw new MyException("overflow");
        }
        return a * b;
    }
}
