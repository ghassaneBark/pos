package com.ma.softlogic.pos.repository;


import com.ma.softlogic.pos.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {

    Optional<Feature> findByCode(String code);

    List<Feature> findByCategory(String category);
}
