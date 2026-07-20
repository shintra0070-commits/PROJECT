package com.pknu.backend.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pknu.backend.model.Comment;
import com.pknu.backend.model.CommentId;

public interface CommentRepository
     extends JpaRepository<Comment, CommentId>{

    /**
     * 게시글별 댓글 조회
    */
    List<Comment> findByComid(Integer comid);

    /**
     * 작성자별 댓글 조회
    */
    List<Comment> findByMemid(String memid);

    /**
     * 댓글 삭제
    */
    @Transactional
    @Modifying

    @Query(value = """

    DELETE FROM comment_test

    WHERE com_id = :comid

    AND TRIM(mem_id) = TRIM(:memid)

    AND TO_CHAR(
        comment_created,
         'YYYY-MM-DD HH24:MI'
    )

    =

    :commentcreated

    """, nativeQuery = true)

    int deleteComment(

        @Param("comid")
        Integer comid,

        @Param("memid")
        String memid,

        @Param("commentcreated")
        String commentcreated
    );
}