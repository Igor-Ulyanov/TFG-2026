package com.igorul.authapi.repository;

import com.igorul.authapi.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByNameIn(List<String> permissions);

    Permission findByName(String permission);
}