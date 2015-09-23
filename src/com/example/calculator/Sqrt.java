package com.example.calculator;

public class Sqrt extends UnaryExpression {

    public Sqrt(Actions first) {
        super(first);
    }

    public int count(int a) throws MyException {
        if (a < 0)
            throw new MyException("wrong sqrt");
        if (a == 0)
            return 0;

        return (int) Math.sqrt(a);
	}
}