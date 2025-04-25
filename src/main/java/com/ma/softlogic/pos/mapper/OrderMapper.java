package com.ma.softlogic.pos.mapper;


import com.ma.softlogic.pos.dto.request.OrderRequest;
import com.ma.softlogic.pos.dto.response.OrderItemResponse;
import com.ma.softlogic.pos.dto.response.OrderResponse;
import com.ma.softlogic.pos.model.Order;
import com.ma.softlogic.pos.model.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toEntity(OrderRequest orderRequest);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "items", source = "items")
    OrderResponse toResponse(Order order);

    List<OrderItemResponse> mapItems(List<OrderItem> items);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "subtotal", expression = "java(item.getUnitPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))")
    OrderItemResponse toOrderItemResponse(OrderItem item);
}