package com.example.calculator;

public class Const implements Actions { 
	int value;
	
	public Const(int a) {
		this.value = a;
	}
	
	public int evaluate(int x) {
		return this.value;
	}
	
	public int evaluate(int x, int y, int z) {
		return this.value;
	}
}