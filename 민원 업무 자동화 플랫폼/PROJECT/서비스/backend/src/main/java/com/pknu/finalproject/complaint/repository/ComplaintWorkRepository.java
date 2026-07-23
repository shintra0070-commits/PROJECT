package com.pknu.finalproject.complaint.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.complaint.entity.ComplaintWork;
import jakarta.persistence.LockModeType;

import java.util.Optional;

@Repository
public interface ComplaintWorkRepository extends JpaRepository<ComplaintWork, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM ComplaintWork w WHERE w.workId = :workId")
    Optional<ComplaintWork> findByIdForUpdate(@Param("workId") Long workId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT w FROM ComplaintWork w
            WHERE w.complaint.complaintId = :complaintId
            ORDER BY w.workSequence ASC, w.workId ASC
            """)
    List<ComplaintWork> findAllByComplaintIdForUpdate(@Param("complaintId") Long complaintId);

    List<ComplaintWork> findByComplaintComplaintIdOrderByWorkSequenceAsc(Long complaintId);

    List<ComplaintWork> findByComplaintComplaintIdAndEndedAtIsNullOrderByWorkSequenceAsc(Long complaintId);

    @Query("""
            SELECT w FROM ComplaintWork w
            JOIN FETCH w.status
            WHERE w.complaint.complaintId IN :complaintIds
              AND w.enabled = 'Y'
            """)
    List<ComplaintWork> findActiveByComplaintIds(@Param("complaintIds") List<Long> complaintIds);

    @Query("SELECT COALESCE(MAX(w.workSequence), 0) FROM ComplaintWork w WHERE w.complaint.complaintId = :complaintId")
    Integer findMaxWorkSequence(@Param("complaintId") Long complaintId);

    @Query("""
            SELECT w FROM ComplaintWork w
            JOIN FETCH w.complaint c
            JOIN FETCH w.status st
            JOIN FETCH w.dept d
            LEFT JOIN FETCH w.officer o
            LEFT JOIN c.user u
            LEFT JOIN c.guest g
            WHERE (:returned = 1 OR d.deptId = :deptId)
              AND w.enabled = 'Y'
              AND (
                    (:returned = 0 AND w.endedAt IS NULL)
                    OR (
                        :returned = 1
                        AND st.statusCode = 'REJECTED'
                        AND 1 = (
                            SELECT COUNT(active.workId) FROM ComplaintWork active
                            WHERE active.complaint.complaintId = c.complaintId
                              AND active.enabled = 'Y'
                        )
                    )
                  )
              AND (
                    :officerId IS NULL
                    OR EXISTS (
                        SELECT wa.assignmentId FROM WorkAssignment wa
                        WHERE wa.work = w
                          AND wa.officer.accountId = :officerId
                          AND wa.unassignedAt IS NULL
                    )
                  )
              AND (:start IS NULL OR c.createdAt >= :start)
              AND (:end IS NULL OR c.createdAt < :end)
              AND (:statusName IS NULL OR st.statusName = :statusName)
              AND (
                    :keyword IS NULL
                    OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  )
            ORDER BY c.createdAt ASC, w.workId ASC
            """)
    List<ComplaintWork> searchByDeptAndFilters(
            @Param("deptId") Long deptId,
            @Param("officerId") Long officerId,
            @Param("returned") int returned,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("statusName") String statusName,
            @Param("keyword") String keyword
    );
}
