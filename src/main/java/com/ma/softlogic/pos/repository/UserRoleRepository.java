package com.ma.softlogic.pos.repository;


import com.ma.softlogic.pos.model.UserRole;
import com.ma.softlogic.pos.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    List<UserRole> findByIdUserId(UUID userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserRole ur WHERE ur.id.userId = :userId")
    void deleteAllByUserId(@Param("userId") UUID userId);

    @Query("SELECT r.name FROM UserRole ur JOIN ur.role r WHERE ur.id.userId = :userId")
    List<String> findRoleNamesByUserId(@Param("userId") UUID userId);
}
