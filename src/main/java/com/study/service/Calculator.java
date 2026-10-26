package com.study.service;

public class Calculator {

  public int add(int a, int b) {
    return a + b;
  }

  public int subtract(int a, int b) {
    return a - b;
  }

  public int multiply(int a, int b) {
    return a * b;
  }

  public double divide(int a, int b) {
    if (b == 0) {
      throw new ArithmeticException("Деление на 0 запрещено!");
    }
    return (double) a / b;
  }

}
