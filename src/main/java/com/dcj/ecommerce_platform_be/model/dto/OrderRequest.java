package com.dcj.ecommerce_platform_be.model.dto;

import java.util.List;

public record OrderRequest(String customerName, String email, List<OrderItemRequest> items) {
}
