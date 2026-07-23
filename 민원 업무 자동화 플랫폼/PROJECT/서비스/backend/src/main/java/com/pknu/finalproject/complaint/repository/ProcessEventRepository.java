package com.pknu.finalproject.complaint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.complaint.entity.ProcessEvent;

@Repository
public interface ProcessEventRepository extends JpaRepository<ProcessEvent, Long> {

    @Query("SELECT COALESCE(MAX(e.eventSequence), 0) FROM ProcessEvent e WHERE e.work.workId = :workId")
    Long findMaxEventSequence(@Param("workId") Long workId);

    @Query("""
            SELECT e FROM ProcessEvent e
            JOIN FETCH e.eventType
            LEFT JOIN FETCH e.account
            WHERE e.work.complaint.complaintId = :complaintId
            ORDER BY e.eventTime ASC, e.eventId ASC
            """)
    List<ProcessEvent> findTimelineByComplaintId(@Param("complaintId") Long complaintId);
}
