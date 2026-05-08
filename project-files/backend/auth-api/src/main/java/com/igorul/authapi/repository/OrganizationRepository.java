package com.igorul.authapi.repository;

import com.igorul.authapi.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}