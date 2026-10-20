package com.study.dto;

public record Errors(String line, String error) {

  @Override
  public String toString() {
    return "\t" + line + " - " + error;
  }
}
