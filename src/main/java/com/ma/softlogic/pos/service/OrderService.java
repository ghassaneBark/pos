package com.ma.softlogic.pos.service;


import com.ma.softlogic.pos.dto.request.OrderRequest;
import com.ma.softlogic.pos.dto.response.OrderResponse;
import com.ma.softlogic.pos.exception.ResourceNotFoundException;
import com.ma.softlogic.pos.mapper.OrderMapper;
import com.ma.softlogic.pos.model.Customer;
import com.ma.softlogic.pos.model.Order;
import com.ma.softlogic.pos.model.OrderItem;
import com.ma.softlogic.pos.model.Product;
import com.ma.softlogic.pos.repository.CustomerRepository;
import com.ma.softlogic.pos.repository.OrderRepository;
import com.ma.softlogic.pos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(UUID id) {
        return orderRepository.findById(id)
                .map(orderMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toEntity(orderRequest);

        // Set customer if provided
        if (orderRequest.getCustomerId() != null) {
            Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + orderRequest.getCustomerId()));
            order.setCustomer(customer);
        }

        // Generate order number (you might want a more sophisticated approach)
        order.setOrderNumber("ORD-" + System.currentTimeMillis());

        // Calculate total and process items
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + item.getProduct().getId()));

            item.setUnitPrice(product.getPrice());
            item.setOrder(order);
            total = total.add(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Transactional
    public OrderResponse updateOrderStatus(UUID id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponse(updatedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByCustomer(UUID customerId) {
        return orderRepository.findByCustomerId(customerId,null).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }
}
