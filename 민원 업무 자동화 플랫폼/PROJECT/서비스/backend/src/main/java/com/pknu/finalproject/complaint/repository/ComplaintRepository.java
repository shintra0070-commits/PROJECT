package com.pknu.finalproject.complaint.repository;

import com.pknu.finalproject.complaint.entity.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    @Query("""
            SELECT c
            FROM Complaint c
            LEFT JOIN c.guest g
            WHERE (
                    (:accountId IS NOT NULL AND c.user.accountId = :accountId)
                    OR (:phoneDigits IS NOT NULL AND REPLACE(g.phone, '-', '') = :phoneDigits)
                    OR (:accountId IS NULL AND :phoneDigits IS NULL)
                  )
              AND (
                    :status IS NULL
                    OR (
                        :status = '답변완료'
                        AND EXISTS (
                            SELECT completedWork.workId FROM ComplaintWork completedWork
                            WHERE completedWork.complaint = c AND completedWork.enabled = 'Y'
                        )
                        AND NOT EXISTS (
                            SELECT incompleteWork.workId FROM ComplaintWork incompleteWork
                            WHERE incompleteWork.complaint = c
                              AND incompleteWork.enabled = 'Y'
                              AND (incompleteWork.status IS NULL OR incompleteWork.status.statusCode <> 'COMPLETED')
                        )
                    )
                    OR (
                        :status = '처리중'
                        AND EXISTS (
                            SELECT processingWork.workId FROM ComplaintWork processingWork
                            WHERE processingWork.complaint = c
                              AND processingWork.enabled = 'Y'
                              AND processingWork.status.statusName = '처리중'
                        )
                    )
                    OR (
                        :status = '접수'
                        AND NOT EXISTS (
                            SELECT processingWork.workId FROM ComplaintWork processingWork
                            WHERE processingWork.complaint = c
                              AND processingWork.enabled = 'Y'
                              AND processingWork.status.statusName = '처리중'
                        )
                        AND (
                            NOT EXISTS (
                                SELECT activeWork.workId FROM ComplaintWork activeWork
                                WHERE activeWork.complaint = c AND activeWork.enabled = 'Y'
                            )
                            OR EXISTS (
                                SELECT pendingWork.workId FROM ComplaintWork pendingWork
                                WHERE pendingWork.complaint = c
                                  AND pendingWork.enabled = 'Y'
                                  AND (pendingWork.status IS NULL OR pendingWork.status.statusCode <> 'COMPLETED')
                            )
                        )
                    )
                  )
            """)
    Page<Complaint> findForRequester(
            @Param("accountId") Long accountId,
            @Param("phoneDigits") String phoneDigits,
            @Param("status") String status,
            Pageable pageable
    );
}
