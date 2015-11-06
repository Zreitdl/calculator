package com.example.calculator;

public class CheckedAdd extends BinaryExpression {

    public CheckedAdd(Actions first, Actions second) {
        super(first, second);
    }

    public int count(int a, int b) throws MyException {
        if ((a > 0 && b > Integer.MAX_VALUE - a) || (a < 0 && b < Integer.MIN_VALUE - a)) {
            throw new MyException("overflow");
        }
        return a + b;
    }
}
