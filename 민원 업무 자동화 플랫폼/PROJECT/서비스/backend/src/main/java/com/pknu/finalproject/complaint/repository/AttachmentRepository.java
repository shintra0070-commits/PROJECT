package com.pknu.finalproject.complaint.repository;

import com.pknu.finalproject.complaint.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findByComplaintComplaintId(Long complaintId);
}
