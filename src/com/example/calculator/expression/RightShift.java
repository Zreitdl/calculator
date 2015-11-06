package com.example.calculator;

public class RightShift extends BinaryExpression { 
	public RightShift(Actions first, Actions second) {
		super(first, second);
	}

	protected int count(int a, int b) {
		return a >> b;
	}
}