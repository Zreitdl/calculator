package com.example.calculator;

public class Modulus extends BinaryExpression { 
	public Modulus(Actions first, Actions second) {
		super(first, second);
	}

	protected int count(int a, int b) {
		return a % b;
	}
}