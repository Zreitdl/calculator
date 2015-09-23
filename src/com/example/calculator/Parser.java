package com.example.calculator;

public interface Parser {
    Actions parse(String expression) throws Except;
}
