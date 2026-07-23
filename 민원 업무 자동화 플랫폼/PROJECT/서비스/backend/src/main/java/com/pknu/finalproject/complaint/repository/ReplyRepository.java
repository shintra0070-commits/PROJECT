package com.pknu.finalproject.complaint.repository;

import com.pknu.finalproject.complaint.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<Reply> findByWorkWorkId(Long workId);
}
