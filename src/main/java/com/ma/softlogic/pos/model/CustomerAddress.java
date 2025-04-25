package com.ma.softlogic.pos.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "customer_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 200)
    private String street;

    @Column(length = 100)
    private String apartment;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(name = "zip_code", nullable = false, length = 20)
    private String zipCode;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    @Column(name = "address_type", length = 50)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(length = 100)
    private String label;

    public enum AddressType {
        HOME, WORK, BILLING, SHIPPING, OTHER
    }
}
