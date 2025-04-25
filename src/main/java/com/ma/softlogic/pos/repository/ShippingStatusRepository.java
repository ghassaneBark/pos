package com.ma.softlogic.pos.repository;

import com.ma.softlogic.pos.model.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingStatusRepository extends JpaRepository<ShippingStatus, Integer> {

    Optional<ShippingStatus> findByName(String name);
}
