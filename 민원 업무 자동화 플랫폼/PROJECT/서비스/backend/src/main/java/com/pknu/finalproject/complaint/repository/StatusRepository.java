package com.pknu.finalproject.complaint.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.complaint.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByStatusName(String statusName);
    Optional<Status> findByStatusCode(String statusCode);
}
