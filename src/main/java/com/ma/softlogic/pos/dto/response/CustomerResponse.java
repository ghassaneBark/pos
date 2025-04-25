package com.ma.softlogic.pos.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclut les champs null du JSON
public class CustomerResponse {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private AddressDTO address;
    private CustomerStatsDTO stats;
    private LocalDateTime createdAt;
    private LocalDateTime lastActive;
    private List<OrderSummaryDTO> recentOrders;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private String customerTier;

    // DTO imbriqué pour l'adresse
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDTO {
        private String street;
        private String city;
        private String zipCode;
        private String country;
        private Boolean isPrimary;
    }

    // DTO imbriqué pour les statistiques
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerStatsDTO {
        private Integer totalOrders;
        private BigDecimal totalSpent;
        private BigDecimal averageOrderValue;
        private Integer daysSinceLastOrder;
        private Integer completedOrders;
        private Integer canceledOrders;
        private LocalDateTime firstPurchaseDate;
        private LocalDateTime lastPurchaseDate;
        private Integer loyaltyPoints;

    }

    // DTO imbriqué pour les commandes récentes
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderSummaryDTO {
        private UUID orderId;
        private String orderNumber;
        private LocalDateTime orderDate;
        private BigDecimal totalAmount;
        private String status;
    }
}
