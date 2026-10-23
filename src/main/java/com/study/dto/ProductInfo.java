package com.study.dto;

import java.math.BigDecimal;

public record ProductInfo(String name, BigDecimal price, String description, double rating) {

}
