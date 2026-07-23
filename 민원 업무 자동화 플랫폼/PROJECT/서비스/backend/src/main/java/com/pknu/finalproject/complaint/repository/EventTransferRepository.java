package com.pknu.finalproject.complaint.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.complaint.entity.EventTransfer;

@Repository
public interface EventTransferRepository extends JpaRepository<EventTransfer, Long> {

    @Query("""
            SELECT t FROM EventTransfer t
            JOIN FETCH t.fromDept
            JOIN FETCH t.toDept
            WHERE t.event.eventId = :eventId
            """)
    Optional<EventTransfer> findByEventEventId(@Param("eventId") Long eventId);

    @Query("""
            SELECT COUNT(t) FROM EventTransfer t
            WHERE t.event.work.complaint.complaintId = :complaintId
            """)
    long countByComplaintId(@Param("complaintId") Long complaintId);
}
