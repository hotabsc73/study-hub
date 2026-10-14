package com.study.enums;

import java.util.Arrays;

public enum Status {
  PASSED, FAILED, SKIPPED;

  public static boolean isStatus(String value) {
    if (value == null) {
      return false;
    }
    return Arrays.stream(Status.values())
        .anyMatch(status -> value.equalsIgnoreCase(status.name()));
  }
}
