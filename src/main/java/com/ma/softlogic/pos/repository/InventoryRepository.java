package com.ma.softlogic.pos.repository;


import com.ma.softlogic.pos.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    // Trouver par produit
    Inventory findByProductId(UUID productId);

    // Produits nécessitant réapprovisionnement
    @Query("SELECT i FROM Inventory i WHERE i.quantity < i.minQuantity")
    Page<Inventory> findProductsNeedingRestock(Pageable pageable);

    // Mise à jour de stock optimiste
    @Modifying
    @Transactional
    @Query("UPDATE Inventory i SET i.quantity = i.quantity + :amount WHERE i.product.id = :productId")
    int updateStock(@Param("productId") UUID productId, @Param("amount") int amount);

    // Historique des stocks bas
    @Query("SELECT i FROM Inventory i WHERE i.quantity < i.minQuantity * 1.2")
    List<Inventory> findLowStockHistory();
}
