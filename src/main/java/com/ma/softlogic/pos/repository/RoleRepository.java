package com.ma.softlogic.pos.repository;

import com.ma.softlogic.pos.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);

    @Query("SELECT r FROM Role r JOIN r.features f WHERE f.code = :featureCode")
    List<Role> findByFeatureCode(@Param("featureCode") String featureCode);

    @Query("SELECT r.name, COUNT(u) FROM Role r JOIN r.users u GROUP BY r.name")
    List<Object[]> countUsersByRole();
}
