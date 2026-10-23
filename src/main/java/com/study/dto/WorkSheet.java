package com.study.dto;

import java.time.DayOfWeek;
import java.util.List;

public record WorkSheet(String name, List<DayOfWeek> workDays) { }
