package com.study.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class StringUtilsTest {

  @ParameterizedTest
  @ValueSource(strings = {"level", "RaceCar", "radar", "", " "})
  @DisplayName("Палиндромные строки должны быть палиндромом")
  void isPalindrome_isPalindrome(String input) {
    assertTrue(StringUtils.isPalindrome(input));
  }

  @ParameterizedTest
  @NullSource
  @DisplayName("Когда приходит NULL это не должно быть палиндромом")
  void isPalindrome_whenNull_thenIsNotPalindrome(String input) {
    assertFalse(StringUtils.isPalindrome(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"hello", "another"})
  @DisplayName("Не палиндромные строки не должны быть палиндромом")
  void isPalindrome_isNotPalindrome(String input) {
    assertFalse(StringUtils.isPalindrome(input));
  }

  @ParameterizedTest
  @CsvSource({"xyz,0", "hello,2", " ,0", "AEIOU,5"})
  @DisplayName("Количество гласных в строке должно быть верным")
  void countVowels_Ok(String input, int expected) {
    assertEquals(expected, StringUtils.countVowels(input));
  }

  @ParameterizedTest
  @NullSource
  @EmptySource
  @DisplayName("Когда приходит NULL количество гласных должно быть 0")
  void countVowels_whenNullOrEmpty_thenZero(String input) {
    assertEquals(0, StringUtils.countVowels(input));
  }
}