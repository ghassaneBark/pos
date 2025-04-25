package com.ma.softlogic.pos.repository;


import com.ma.softlogic.pos.model.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, ProductSupplier> {

    List<ProductSupplier> findByProductId(UUID productId);

    List<ProductSupplier> findBySupplierId(UUID supplierId);

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM ProductSupplier ps WHERE ps.productId = :productId")
//    void deleteAllByProductId(@Param("productId") UUID productId);
}
