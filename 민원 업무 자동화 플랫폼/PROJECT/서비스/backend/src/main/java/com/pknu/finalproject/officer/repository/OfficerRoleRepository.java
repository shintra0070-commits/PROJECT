package com.pknu.finalproject.officer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.officer.entity.OfficerRole;

@Repository
public interface OfficerRoleRepository extends JpaRepository<OfficerRole, OfficerRole.OfficerRoleId> {
}
