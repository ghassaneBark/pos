package com.ma.softlogic.pos.dto.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private UUID id;
    private UUID customerId;
    private String customerName;
    private String orderNumber;
    private BigDecimal total;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
