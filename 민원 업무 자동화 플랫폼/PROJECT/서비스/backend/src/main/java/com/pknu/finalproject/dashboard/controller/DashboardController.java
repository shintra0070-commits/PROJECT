package com.pknu.finalproject.dashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.finalproject.dashboard.dto.DashboardStatsResponse;
import com.pknu.finalproject.dashboard.service.DashboardService;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;


    public DashboardController(
            DashboardService dashboardService
    ){
        this.dashboardService = dashboardService;
    }



    @GetMapping("/today")
    public DashboardStatsResponse today(){


        return dashboardService.getTodayStats();

    }
}
