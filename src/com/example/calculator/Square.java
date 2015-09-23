package com.example.calculator;

public class Square extends UnaryExpression { 
	public Square(Actions first) {
		super(first);
	}

	protected int count(int a) {
		CheckedMultiply cm = new CheckedMultiply(first, first);
		return cm.count(a,a);
	}
}