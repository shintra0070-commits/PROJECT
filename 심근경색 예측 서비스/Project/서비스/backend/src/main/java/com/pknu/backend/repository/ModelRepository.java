package com.pknu.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pknu.backend.model.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
    @Query("SELECT m FROM Model m WHERE m.mem_id = :mem_id")
    List<Model> findByMEM_ID(String mem_id);

    @Query(value = "SELECT COUNT(*) FROM data_test WHERE mem_id = :mem_id AND TRUNC(check_date) = TRUNC(SYSDATE)", nativeQuery = true)
    int countTodayByMemId(@Param("mem_id") String mem_id);
    
    @Query(value = "SELECT PREDICT FROM data_test WHERE mem_id = :mem_id AND TRUNC(check_date) = TRUNC(SYSDATE) AND ROWNUM = 1", nativeQuery = true)
    Double findTodayPredictByMemId(@Param("mem_id") String mem_id);
        
}   
