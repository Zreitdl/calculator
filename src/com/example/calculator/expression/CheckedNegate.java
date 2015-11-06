package com.example.calculator;

public class CheckedNegate extends UnaryExpression {

    public CheckedNegate(Actions first) {
        super(first);
    }

    public int count(int a) throws MyException {
        if (a == Integer.MIN_VALUE) {
            throw new MyException("owerflow");
        }
        return -a;
    }
}
