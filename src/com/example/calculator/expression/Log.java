package com.example.calculator;

public class Log extends BinaryExpression { 
	public Log(Actions first, Actions second) {
		super(first, second);
	}

	protected int count(int a, int b) {
		if (b <= 1 || a <= 0) {
            throw new MyException("wrong log");
        }
		if (a < b) { 
			return 0;
		}
		int result = 1;
		int pow = b;
        while (b * pow <= a && !((pow * b) / b != pow || (pow * b) / pow != b)) {
            pow *= b;
            result++;
        }
        return result;
	}
}