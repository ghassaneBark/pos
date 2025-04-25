package com.ma.softlogic.pos.repository;


import com.ma.softlogic.pos.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRole> {

    List<UserRole> findByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserRole ur WHERE ur.userId = :userId")
    void deleteAllByUserId(@Param("userId") UUID userId);

    @Query("SELECT r.name FROM UserRole ur JOIN Role r ON ur.roleId = r.id WHERE ur.userId = :userId")
    List<String> findRoleNamesByUserId(@Param("userId") UUID userId);
}