package com.pknu.finalproject.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardStatsResponse {
    // 오늘 접수 건수
    private int received;

    // 현재 처리중 건수
    private int processing;

    // 완료 건수
    private int completed;


    public DashboardStatsResponse(
            int received,
            int processing,
            int completed
    ){
        this.received = received;
        this.processing = processing;
        this.completed = completed;
    }
}
