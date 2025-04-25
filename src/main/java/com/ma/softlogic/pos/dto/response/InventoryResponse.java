package com.ma.softlogic.pos.dto.response;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private UUID id;
    private UUID productId;
    private String productName;
    private int quantity;
    private int minQuantity;
    private LocalDateTime lastRestockDate;
}
