package com.ma.softlogic.pos.repository;


import com.ma.softlogic.pos.model.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    // Trouver tous les items d'une commande
    List<OrderItem> findByOrderId(UUID orderId);

    // Statistiques de vente par produit
    @Query("SELECT SUM(oi.quantity), SUM(oi.quantity * oi.unitPrice) " +
            "FROM OrderItem oi WHERE oi.product.id = :productId")
    Object[] findSalesStatsByProduct(@Param("productId") UUID productId);

    // Produits les plus vendus
    @Query("SELECT oi.product.id, SUM(oi.quantity) as totalQuantity " +
            "FROM OrderItem oi GROUP BY oi.product.id ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts(Pageable pageable);
}
