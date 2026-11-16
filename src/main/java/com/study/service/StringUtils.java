package com.study.service;

import java.util.Set;

public class StringUtils {

  private static final Set<Character> VOWELS = Set.of('a', 'e', 'i', 'o', 'u');

  public static boolean isPalindrome(String input) {
    if (input == null) {
      return false;
    }
    if (input.isBlank()) {
      return true;
    }
    String reversed = new StringBuilder(input).reverse().toString();
    return reversed.equalsIgnoreCase(input);
  }

  public static int countVowels(String input) {
    if (input == null || input.isBlank()) {
      return 0;
    }

    return Math.toIntExact(input.toLowerCase().chars()
        .mapToObj(c -> (char) c)
        .filter(VOWELS::contains)
        .count());
  }

}
