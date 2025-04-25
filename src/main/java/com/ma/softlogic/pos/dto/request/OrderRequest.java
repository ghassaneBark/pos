package com.ma.softlogic.pos.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private UUID customerId;

    @NotNull(message = "Order items cannot be null")
    @Size(min = 1, message = "Order must have at least one item")
    private List<OrderItemRequest> items;
}
