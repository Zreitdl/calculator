package com.example.calculator;

public class MyException extends RuntimeException {
    public String text = "";
    public MyException(String s) {
        text = s;
    }
}
