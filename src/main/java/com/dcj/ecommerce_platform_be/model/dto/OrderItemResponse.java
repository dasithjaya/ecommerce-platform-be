package com.dcj.ecommerce_platform_be.model.dto;

import java.math.BigDecimal;

public record OrderItemResponse(String productName, int quantity, BigDecimal totalPrice) {
}
