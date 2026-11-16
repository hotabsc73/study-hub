package com.study.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculatorTest {

  private final Calculator calculator = new Calculator();

  @ParameterizedTest
  @CsvSource({"1,1,2", "1,0,1", "0,0,0", "-1,-1,-2", "-1,1,0"})
  @DisplayName("Сложение двух чисел должно возвращать правильный результат")
  public void testAddition_whenNumbers_thenOk(int a, int b, int expected) {
    var actual = calculator.add(a, b);

    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @CsvSource({"1,1,0", "1,0,1", "0,0,0", "-1,-1,0", "-1,1,-2"})
  @DisplayName("Вычитание двух чисел должно возвращать правильный результат")
  public void testSubtract_whenNumbers_thenOk(int a, int b, int expected) {
    var actual = calculator.subtract(a, b);

    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @CsvSource({"1,1,1", "1,0,0", "0,0,0", "-1,-1,1", "-1,1,-1"})
  @DisplayName("Умножение двух чисел должно возвращать правильный результат")
  public void testMultiply_whenNumbers_then(int a, int b, int expected) {
    var actual = calculator.multiply(a, b);

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Деление на ноль должно выбрасывать ArithmeticException")
  public void testDivision_whenByZero_thenThrow() {
    var actual = Assertions.assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));

    assertEquals("Деление на 0 запрещено!", actual.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"1,1,1", "10,2,5", "-1,-1,1", "-1,1,-1", "10,4,2.5"})
  @DisplayName("Деление двух чисел должно возвращать правильный результат")
  public void testMultiply_whenNumbers_then(int a, int b, double expected) {
    var actual = calculator.divide(a, b);

    assertEquals(expected, actual);
  }
}