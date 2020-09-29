package com.omaryaya.jetbrains.repository;

import java.util.Optional;

import com.omaryaya.jetbrains.entity.Role;
import com.omaryaya.jetbrains.entity.RoleName;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(RoleName roleName);
}
