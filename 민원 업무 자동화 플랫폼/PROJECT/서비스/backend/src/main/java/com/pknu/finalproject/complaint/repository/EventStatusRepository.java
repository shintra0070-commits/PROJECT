package com.pknu.finalproject.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.complaint.entity.EventStatus;

@Repository
public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
}
