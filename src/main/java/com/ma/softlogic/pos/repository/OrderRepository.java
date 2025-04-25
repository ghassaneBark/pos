package com.ma.softlogic.pos.repository;


import com.ma.softlogic.pos.model.Order;
import com.ma.softlogic.pos.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    // Trouver les commandes par client avec pagination
    Page<Order> findByCustomerId(UUID customerId, Pageable pageable);

    // Trouver les commandes par statut
    List<Order> findByStatus(OrderStatus status);

    // Requête personnalisée pour les commandes récentes
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :date ORDER BY o.createdAt DESC")
    List<Order> findRecentOrders(@Param("date") LocalDateTime date);

    // Statistiques de commandes par client
    @Query("SELECT COUNT(o), SUM(o.total), AVG(o.total) FROM Order o WHERE o.customer.id = :customerId")
    Object[] findOrderStatsByCustomer(@Param("customerId") UUID customerId);

    // Trouver les commandes avec des produits spécifiques
    @Query("SELECT DISTINCT o FROM Order o JOIN o.items i WHERE i.product.id = :productId")
    Page<Order> findByProductId(@Param("productId") UUID productId, Pageable pageable);
}