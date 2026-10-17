package com.study.dto;

import com.study.enums.Status;

public record TestResult(String name, Status status, long durationMs) {
  @Override
  public String toString() {
    return "\t" + name + " - " + status.name() + " (" + durationMs + " ms)";
  }
}
