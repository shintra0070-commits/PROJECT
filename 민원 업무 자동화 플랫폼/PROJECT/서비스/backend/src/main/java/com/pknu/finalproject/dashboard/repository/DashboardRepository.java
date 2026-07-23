package com.pknu.finalproject.dashboard.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 오늘 접수
    public int getTodayReceived() {

        String sql = """
            SELECT COUNT(*)
            FROM COMPLAINT
            WHERE TRUNC(CREATED_AT) = TRUNC(SYSDATE)
            """;

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // 현재 담당자 배정 또는 처리 중인 원본 민원
    public int getProcessing() {

        String sql = """
            SELECT COUNT(DISTINCT cw.COMPLAINT_ID)
            FROM COMPLAINT_WORK cw
            JOIN STATUS s ON s.STATUS_ID = cw.STATUS_ID
            WHERE cw.ENDED_AT IS NULL
              AND cw.ENABLED = 'Y'
              AND s.STATUS_CODE IN ('ASSIGNED', 'PROCESSING')
            """;

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // 오늘 완료
    public int getCompleted() {

        String sql = """
            SELECT COUNT(DISTINCT cw.COMPLAINT_ID)
            FROM PROCESS_EVENT pe
            JOIN EVENT_TYPE et ON et.EVENT_TYPE_ID = pe.EVENT_TYPE_ID
            JOIN COMPLAINT_WORK cw ON cw.WORK_ID = pe.WORK_ID
            WHERE et.EVENT_CODE = 'REPLY_COMPLETED'
              AND TRUNC(pe.EVENT_TIME) = TRUNC(SYSDATE)
            """;

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

}
