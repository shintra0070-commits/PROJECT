package com.pknu.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pknu.backend.model.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {

        @Query(value = "SELECT c FROM Community c WHERE c.mem_id = :mem_id")
        Page<Community> findByMemId(@Param("mem_id") String mem_id, Pageable pageable);

        /**
         * 인기글 조회
         * 좋아요 순
        */
        @Query(value = """
                        SELECT *
                        FROM(SELECT *FROM community_test ORDER BY com_like DESC)
                        WHERE ROWNUM <= 4
                        """, nativeQuery = true)
        List<Community> findTopCommunityList();

        /* 마이페이지에서 내 게시글 조회 */
        @Query(value = """
                        SELECT *
                        FROM community_test
                        WHERE MEM_ID = :mem_id
                        ORDER BY com_created DESC
                        """, nativeQuery = true)
        List<Community> myBoard(@Param("mem_id") String mem_id);

        /* 전체 게시글에서 최신날짜로 정렬 */
        @Query(value = """
                        SELECT *
                        FROM community_test
                        ORDER BY com_created DESC
                        """, countQuery = """
                        SELECT COUNT(*)
                        FROM community_test
                        """, nativeQuery = true)
        Page<Community> findAllPaging(Pageable pageable);
}