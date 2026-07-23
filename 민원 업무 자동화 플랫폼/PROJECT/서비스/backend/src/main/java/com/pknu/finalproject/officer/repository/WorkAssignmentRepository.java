package com.pknu.finalproject.officer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.officer.entity.WorkAssignment;

@Repository
public interface WorkAssignmentRepository extends JpaRepository<WorkAssignment, Long> {
    Optional<WorkAssignment> findByWorkWorkIdAndUnassignedAtIsNull(Long workId);
    List<WorkAssignment> findByWorkComplaintComplaintIdOrderByAssignedAtAsc(Long complaintId);
}
