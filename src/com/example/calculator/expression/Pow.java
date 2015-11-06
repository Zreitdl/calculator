package com.example.calculator;

public class Pow extends BinaryExpression { 

	public Pow(Actions first, Actions second) {
		super(first, second);
	}
	
	public void check(int a, int b) throws MyException {
        if (a != 0) {
            if ((a * b) / b != a || (a * b) / a != b) {
                throw new MyException("overflow");
            }
        }
    }

	protected int count(int a, int b) {
		if (b < 0 || a == 0 && b == 0) {
            throw new MyException("wrong pow");
        }
        int result = 1;
        while (b > 0) {
            if (b % 2 == 1) {
                check(a, result);
                result *= a;
            }
            b /= 2;
            if (b > 0) {
                check(a, a);
            }
            a *= a;
        }
        return result;
	}
}