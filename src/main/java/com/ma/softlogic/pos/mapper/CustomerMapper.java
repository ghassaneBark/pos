package com.ma.softlogic.pos.mapper;

import com.ma.softlogic.pos.dto.request.CustomerRequest;
import com.ma.softlogic.pos.dto.response.CustomerResponse;
import com.ma.softlogic.pos.model.Customer;
import com.ma.softlogic.pos.model.Order;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring",
        uses = {OrderMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CustomerMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    // Mapping de base Request -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", expression = "java(mapPassword(request.getPassword()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    public abstract Customer toEntity(CustomerRequest request);

    // Mapping de base Entity -> Response
    @Mapping(target = "address", expression = "java(mapAddress(customer))")
    @Mapping(target = "stats", ignore = true) // Rempli manuellement dans le service
    public abstract CustomerResponse toResponse(Customer customer);

    // Mise à jour partielle
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    public abstract void updateCustomerFromRequest(CustomerRequest request, @MappingTarget Customer customer);

    // Méthodes personnalisées
    protected String mapPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isBlank()) {
            return null;
        }
        return passwordEncoder.encode(plainPassword);
    }

    protected CustomerResponse.AddressDTO mapAddress(Customer customer) {
        if (customer.getAddress() == null && customer.getCity() == null && customer.getZipCode() == null) {
            return null;
        }

        return CustomerResponse.AddressDTO.builder()
                .street(customer.getAddress())
                .city(customer.getCity())
                .zipCode(customer.getZipCode())
                .country("France") // Valeur par défaut, peut être paramétrable
                .isPrimary(true)   // Par défaut
                .build();
    }

    // Pour les listes
    public abstract List<CustomerResponse> toResponseList(List<Customer> customers);

    @AfterMapping
    protected void enrichWithStats(@MappingTarget CustomerResponse response, Customer customer) {
        if (customer.getOrders() == null || customer.getOrders().isEmpty()) {
            return;
        }

        // Calcul une fois pour toutes
        LocalDateTime now = LocalDateTime.now();
        List<Order> completedOrders = customer.getOrders().stream()
                .filter(o -> "COMPLETED".equals(o.getStatus()))
                .toList();

        // Première commande (tous statuts)
        LocalDateTime firstPurchase = customer.getOrders().stream()
                .map(Order::getCreatedAt)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);

        // Dernière commande complétée
        LocalDateTime lastPurchase = completedOrders.stream()
                .map(Order::getCreatedAt)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);

        // Calcul des jours depuis dernière commande
        Integer daysSinceLastPurchase = lastPurchase != null
                ? (int) ChronoUnit.DAYS.between(lastPurchase, now)
                : null;

        // Calcul du montant total
        BigDecimal totalSpent = completedOrders.stream()
                .map(Order::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Construction du DTO
        CustomerResponse.CustomerStatsDTO stats = CustomerResponse.CustomerStatsDTO.builder()
                .totalOrders(customer.getOrders().size())
                .completedOrders(completedOrders.size())
                .canceledOrders((int) customer.getOrders().stream()
                        .filter(o -> "CANCELED".equals(o.getStatus()))
                        .count())
                .totalSpent(totalSpent)
                .averageOrderValue(completedOrders.isEmpty() ? BigDecimal.ZERO :
                        totalSpent.divide(
                                BigDecimal.valueOf(completedOrders.size()),
                                2, RoundingMode.HALF_UP))
                .firstPurchaseDate(firstPurchase)
                .lastPurchaseDate(lastPurchase)
                .daysSinceLastOrder(daysSinceLastPurchase)
                .loyaltyPoints(customer.getLoyaltyPoints())
                .build();

        response.setStats(stats);
    }
}