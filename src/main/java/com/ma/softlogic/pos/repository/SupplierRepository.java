package com.ma.softlogic.pos.repository;


import com.ma.softlogic.pos.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    // Recherche par nom (insensible à la casse)
    Page<Supplier> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Fournisseurs actifs
    List<Supplier> findByStatus(String status);

    // Statistiques fournisseurs
    @Query("SELECT s.status, COUNT(s) FROM Supplier s GROUP BY s.status")
    List<Object[]> countByStatus();

//    // Trouver les fournisseurs d'un produit
//    @Query("SELECT s FROM Supplier s JOIN s.products p WHERE p.id = :productId")
//    Page<Supplier> findByProductId(@Param("productId") UUID productId, Pageable pageable);
}
