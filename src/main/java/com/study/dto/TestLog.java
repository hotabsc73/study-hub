package com.study.dto;

import java.util.List;
import java.util.Optional;

public record TestLog(List<TestResult> testResults, List<Errors> errors, Optional<TestResult> firstFailed) {}
