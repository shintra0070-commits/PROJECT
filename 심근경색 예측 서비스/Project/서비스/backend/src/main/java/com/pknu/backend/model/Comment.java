package com.pknu.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 복합키 클래스 사용
*/
@Entity
@Table(name = "comment_test")

@IdClass(CommentId.class)

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

@ToString

public class Comment {

    /**
     * 게시글 번호
    */
    @Id
    @Column(name = "com_id")
    private Integer comid;

    /**
     * 작성자 ID
    */
    @Id
    @Column(name = "mem_id")
    private String memid;

    /**
     * 댓글 작성일시
    */
    @Id
    @Column(name = "comment_created")
    private LocalDateTime commentcreated;

    /**
     * 댓글 내용
    */
    @Column(name = "comment_content")
    private String commentcontent;

    @PrePersist
    public void prePersist(){

        this.commentcreated = LocalDateTime.now();
    }
}