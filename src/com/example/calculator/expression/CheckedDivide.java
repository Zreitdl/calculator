package com.example.calculator;

public class CheckedDivide extends BinaryExpression {

    public CheckedDivide(Actions a, Actions b) {
        super(a, b);
    }

    public int count(int a, int b) throws MyException{
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new MyException("overflow");
        }
        if (b == 0) {
            throw new MyException("division by zero");
        }
        return a / b;
    }
}
