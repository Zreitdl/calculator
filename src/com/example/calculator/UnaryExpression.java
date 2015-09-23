package com.example.calculator;

abstract class UnaryExpression implements Actions {
	protected Actions first;

	abstract protected int count(int a);

	public UnaryExpression(Actions first) {
		this.first = first;
	}

	public int evaluate(int value) {
		return count(first.evaluate(value));
	}

	public int evaluate(int x, int y, int z) {
		return count(first.evaluate(x, y, z));
	}
}