package com.example.calculator;

public class Variable implements Actions {
	String strVal;
	int intVal;

	public Variable(String x) {
		this.strVal = x;
	}

	public int evaluate(int x) {
		this.intVal = x;
		return x;
	}
	
	public int evaluate(int x, int y, int z) {
		if (this.strVal == "x") {
			this.intVal = x;
			return x;
		} else if (this.strVal == "y") {
			this.intVal = y;
			return y;
		} else {
			this.intVal = z;
			return z;
		}
	}
}