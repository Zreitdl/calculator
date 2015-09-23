package com.example.calculator;

abstract class BinaryExpression implements Actions {
	protected Actions first;
	protected Actions second;

	abstract protected int count(int a, int b);

	public BinaryExpression(Actions first, Actions second) {
		this.first = first;
		this.second = second;
	}

	public int evaluate(int value) {
		return count(first.evaluate(value), second.evaluate(value));
	}

	public int evaluate(int x, int y, int z) {
		return count(first.evaluate(x, y, z), second.evaluate(x, y, z));
	}
}