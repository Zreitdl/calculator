package com.example.calculator;

public class Abs extends UnaryExpression { 
	public Abs(Actions first) {
		super(first);
	}

	protected int count(int a) {
		if (a == Integer.MIN_VALUE) {
				throw new MyException("overflow");
		}
		if (a >= 0) {
			return a;
		} else {
			return -a;
		}
	}
}