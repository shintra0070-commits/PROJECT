package com.pknu.backend.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.backend.model.Model;
import com.pknu.backend.service.ModelService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/modeldata")
@RequiredArgsConstructor
@Slf4j
public class ModelController {
    private final ModelService modelservice;

    @GetMapping("/view/{mem_id}")
    public ResponseEntity<List<Model>> getModelData(@PathVariable("mem_id") String mem_id) {
        log.info("요청된 mem_id = {}", mem_id);
        List<Model> result = this.modelservice.getModelDataByMemId(mem_id);
        return ResponseEntity.ok(result);
    }
    

    @GetMapping("/check/{mem_id}")
    public ResponseEntity<Boolean> checkTodayPredict(@PathVariable("mem_id") String mem_id) {
        log.info("오늘 예측 여부 확인 mem_id = {}", mem_id);
        boolean hasPredicted = this.modelservice.hasPredictedToday(mem_id);
        return ResponseEntity.ok(hasPredicted);
    }

    @GetMapping("/today/{mem_id}")
    public ResponseEntity<Double> getTodayPredict(@PathVariable("mem_id") String mem_id) {
        log.info("오늘 예측값 조회 mem_id = {}", mem_id);
        Double predict = this.modelservice.getTodayPredict(mem_id);
        return ResponseEntity.ok(predict);
    }

    @DeleteMapping("/delete/{data_id}")
    public ResponseEntity<Void> setAnalysisDelete(@PathVariable("data_id") Integer data_id) {

        this.modelservice.setAnalysisDelete(data_id);
        return ResponseEntity.noContent().build();
    }
}
