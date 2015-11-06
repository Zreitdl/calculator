package com.example.calculator;

public class LeftShift extends BinaryExpression { 
	public LeftShift(Actions first, Actions second) {
		super(first, second);
	}

	protected int count(int a, int b) {
		return a << b;
	}
}