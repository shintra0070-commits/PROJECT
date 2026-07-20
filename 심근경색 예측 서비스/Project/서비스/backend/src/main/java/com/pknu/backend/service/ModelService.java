package com.pknu.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu.backend.model.Model;
import com.pknu.backend.repository.ModelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModelService {
    private final ModelRepository modelRepository;

    public List<Model> getModelDataByMemId(String mem_id){
        List<Model> list = this.modelRepository.findByMEM_ID(mem_id);

        if(!list.isEmpty()){
            log.info("회원아이디[{}]의 정보를 정상적으로 조회 (총 {}건)", mem_id, list.size());
            return list;
        }
        log.warn("회원아이디[{}]의 데이터가 존재하지 않습니다.", mem_id);
        return list; // 빈 리스트 반환        
    }
    
    public boolean hasPredictedToday(String mem_id) {
        return modelRepository.countTodayByMemId(mem_id) > 0;
    }

    public Double getTodayPredict(String mem_id) {
        return modelRepository.findTodayPredictByMemId(mem_id);
    }

    public String setAnalysisDelete(Integer data_id) {

        if (this.modelRepository.existsById(data_id)) {
            
            this.modelRepository.deleteById(data_id);
            return "분석기록 성공";
        }

        return "삭제할 분석기록 없음";
    }
}
