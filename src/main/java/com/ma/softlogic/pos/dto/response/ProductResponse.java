package com.ma.softlogic.pos.dto.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private UUID categoryId;
    private String categoryName;
    private String imageUrl;
    private boolean featured;
    private LocalDateTime createdAt;
}
