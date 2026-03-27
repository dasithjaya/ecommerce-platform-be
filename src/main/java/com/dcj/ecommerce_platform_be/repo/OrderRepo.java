package com.dcj.ecommerce_platform_be.repo;

import com.dcj.ecommerce_platform_be.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderId(String orderId);
}
