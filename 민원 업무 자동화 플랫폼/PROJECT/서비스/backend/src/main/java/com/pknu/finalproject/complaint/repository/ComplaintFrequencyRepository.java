package com.pknu.finalproject.complaint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.complaint.entity.ComplaintFrequency;

@Repository
public interface ComplaintFrequencyRepository extends JpaRepository<ComplaintFrequency, Long> {
    List<ComplaintFrequency> findByEnabledOrderByDisplayOrderAscFaqIdAsc(String enabled);
}
