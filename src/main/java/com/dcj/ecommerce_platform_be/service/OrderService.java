package com.dcj.ecommerce_platform_be.service;

import com.dcj.ecommerce_platform_be.model.Order;
import com.dcj.ecommerce_platform_be.model.OrderItem;
import com.dcj.ecommerce_platform_be.model.Product;
import com.dcj.ecommerce_platform_be.model.dto.OrderItemRequest;
import com.dcj.ecommerce_platform_be.model.dto.OrderItemResponse;
import com.dcj.ecommerce_platform_be.model.dto.OrderRequest;
import com.dcj.ecommerce_platform_be.model.dto.OrderResponse;
import com.dcj.ecommerce_platform_be.repo.OrderRepo;
import com.dcj.ecommerce_platform_be.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;

    public OrderResponse placeOrder(OrderRequest request) {
        Order order = new Order();
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        order.setOrderId(orderId);
        order.setCustomerName(request.customerName());
        order.setEmail(request.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemReq : request.items()) {
            Product product = productRepo.findById(itemReq.productId()).orElseThrow(() -> new RuntimeException("Product not found!"));
            product.setQuantity(product.getQuantity() - itemReq.quantity());
            productRepo.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())))
                    .order(order)
                    .build();

            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepo.save(order);

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    item.getProduct().getProductName(),
                    item.getQuantity(),
                    item.getTotalPrice()
            );
            orderItemResponses.add(orderItemResponse);
        }

        OrderResponse orderResponse = new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                orderItemResponses
        );

        return orderResponse;
    }

    public List<OrderResponse> getAllOrderResponses() {

        List<Order> orders = orderRepo.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order order: orders) {
            List<OrderItemResponse> itemResponses = new ArrayList<>();

            for (OrderItem item: order.getOrderItems()) {
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getProduct().getProductName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                itemResponses.add(orderItemResponse);
            }
            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            );
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }
}
