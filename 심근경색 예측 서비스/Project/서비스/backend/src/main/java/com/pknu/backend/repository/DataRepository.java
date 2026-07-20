package com.pknu.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pknu.backend.model.Data;

public interface DataRepository extends JpaRepository<Data, String> {

}
