package com.pknu.finalproject.dashboard.service;
import org.springframework.stereotype.Service;

import com.pknu.finalproject.dashboard.dto.DashboardStatsResponse;
import com.pknu.finalproject.dashboard.repository.DashboardRepository;


@Service
/** 대시보드에 표시할 접수·처리중·완료 집계 값을 조합한다. */
public class DashboardService {
    private final DashboardRepository dashboardRepository;


    public DashboardService(
            DashboardRepository dashboardRepository
    ){
        this.dashboardRepository = dashboardRepository;
    }



    /** 세 개의 집계 쿼리 결과를 하나의 대시보드 응답으로 반환한다. */
    public DashboardStatsResponse getTodayStats(){


        int received =
                dashboardRepository.getTodayReceived();


        int processing =
                dashboardRepository.getProcessing();


        int completed =
                dashboardRepository.getCompleted();



        return new DashboardStatsResponse(
                received,
                processing,
                completed
        );

    }
}
