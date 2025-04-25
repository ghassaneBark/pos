package com.ma.softlogic.pos.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.math.BigDecimal;

@Entity
@Table(name = "customers",
        indexes = {
                @Index(name = "idx_customer_email", columnList = "email", unique = true),
                @Index(name = "idx_customer_phone", columnList = "phone")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_customer_email", columnNames = "email")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"passwordHash", "orders"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 200)
    private String passwordHash;

    @Column(length = 20)
    private String phone;

    // Adresse décomposée
    @Column(length = 200)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(name = "zip_code", length = 20)
    private String zipCode;

    @Column(length = 100)
    private String country;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "email_verified", nullable = false)
    @Builder.Default
    private boolean emailVerified = false;

    @Column(name = "phone_verified", nullable = false)
    @Builder.Default
    private boolean phoneVerified = false;

    @Column(name = "terms_accepted", nullable = false)
    @Builder.Default
    private boolean termsAccepted = false;

    @Column(name = "marketing_opt_in")
    private Boolean marketingOptIn;

    @Column(name = "account_active", nullable = false)
    @Builder.Default
    private boolean accountActive = true;

    @Column(name = "customer_tier", length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CustomerTier customerTier = CustomerTier.STANDARD;

    @Column(name = "loyalty_points")
    @Builder.Default
    private Integer loyaltyPoints = 0;

    @Column(name = "last_purchase_date")
    private LocalDateTime lastPurchaseDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_active_at")
    private LocalDateTime lastActiveAt;

    @Column(name = "preferred_language", length = 10)
    @Builder.Default
    private String preferredLanguage = "fr";

    @Column(name = "preferred_currency", length = 3)
    @Builder.Default
    private String preferredCurrency = "EUR";

    @Column(name = "tax_exempt", nullable = false)
    @Builder.Default
    private boolean taxExempt = false;

    @Column(name = "vat_number", length = 50)
    private String vatNumber;

    @Column(name = "notes")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> notes;

    // Relations
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CustomerAddress> addresses = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "customer_favorite_products",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @Builder.Default
    private Set<Product> favoriteProducts = new HashSet<>();

    // Méthodes utilitaires
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }

    public void addAddress(CustomerAddress address) {
        addresses.add(address);
        address.setCustomer(this);
    }

    // Enum pour le niveau de client
    public enum CustomerTier {
        STANDARD, SILVER, GOLD, PLATINUM, VIP
    }

    // Méthode pour vérifier si le client est valide
    @PrePersist
    @PreUpdate
    private void validate() {
        if (termsAccepted == false) {
            throw new IllegalStateException("Customer must accept terms and conditions");
        }
        if (birthDate != null && birthDate.plusYears(18).isAfter(LocalDate.now())) {
            throw new IllegalStateException("Customer must be at least 18 years old");
        }
    }
}