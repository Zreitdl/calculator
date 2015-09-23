package com.example.calculator;

public class CheckedSubtract extends BinaryExpression {

    public CheckedSubtract(Actions first, Actions second) {
        super(first, second);
    }

    public int count(int a, int b) throws MyException {
        if (a >= 0 && b < 0 && a > Integer.MAX_VALUE + b || a < 0 && b >= 0 && a < Integer.MIN_VALUE + b) {
            throw new MyException("overflow");
        }
        return a - b;
    }
}
