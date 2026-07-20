package com.pknu.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.backend.model.Community;
import com.pknu.backend.repository.CommunityRepository;
import com.pknu.backend.service.CommunityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
@Slf4j


public class CommunityController {

    // 중간 객체들을 자동 연결
    @Autowired
    private CommunityRepository communityRepository;    
    private final CommunityService communityService;

    /**
     * 링크 테스트
     * url : http://localhost:8080/community/check
    */
    @GetMapping("/check")
    public String checkLink() {
        return "CommunityController 정상";
    }

    /**
     * 전체 조회
     * url : http://localhost:8080/community/list
    */
    @GetMapping(path = "/list")
    public ResponseEntity<List<Community>> getCommunityList(@RequestParam(required = false) String mem_id, Pageable pageable) {
        log.info("getCommunityList() 메소드 호출");
        // 내 게시글 조회
        if(mem_id != null){
            return ResponseEntity.ok(this.communityRepository.findByMemId(mem_id, pageable).getContent()
                );
            }
        // 전체 게시글 조회
        return ResponseEntity.ok(this.communityRepository .findAll(pageable).getContent());
    }

    /**
     * 상세 조회
     * url : http://localhost:8080/community/view/{com_id}
    */
    @GetMapping("/view/{com_id}")
    public ResponseEntity<Community> getCommunityView(@PathVariable Integer com_id) {
        log.info("게시글 상세 조회 : {}", com_id);
        return ResponseEntity.ok(this.communityService.getCommunityView(com_id));
    }

    /**
     * 등록
     * http://localhost:8080/community/insert
    */
    @PostMapping("/insert")
    public ResponseEntity<?> setCommunityInsert(@RequestBody Community community) {

        Community insert_community = this.communityService.setCommunityInsert(community);

        if (insert_community == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("게시글 번호 중복");
        }

        return ResponseEntity.ok(insert_community);
    }

    /**
     * 수정
     * http://localhost:8080/community/update
    */
    @PutMapping("/update")
    public ResponseEntity<Community> setCommunityUpdate(@RequestBody Community community) {

        return ResponseEntity.ok(this.communityService.setCommunityUpdate(community));
    }

    /**
     * 삭제
     * http://localhost:8080/community/delete/{com_id}
    */
    @DeleteMapping("/delete/{com_id}")
    public ResponseEntity<Void> setCommunityDelete(@PathVariable Integer com_id) {

        this.communityService.setCommunityDelete(com_id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 좋아요
    */
    @PostMapping("/like/{com_id}")
    public ResponseEntity<Community> setCommunityLike(@PathVariable Integer com_id) {

        return ResponseEntity.ok(this.communityService.setCommunityLike(com_id));
    }
    
    /**
     * Paging
    */
    @GetMapping("/list_paging")

    public ResponseEntity<Page<Community>>getCommunityListPaging(@RequestParam(name = "page",defaultValue = "1") int page,
                                                                 @RequestParam(name = "size", defaultValue = "10") int size) {
            log.info("MemberListPaging() 메소드 호출,,,");
            
            Page<Community> community_list = this.communityService.getCommunityListPaging(page - 1, size);
            return ResponseEntity.ok(community_list);
    }

    /**
     * 인기글 조회
    */
    @GetMapping("/top_list")
    public ResponseEntity<List<Community>> getTopCommunityList(){
        return ResponseEntity.ok(this.communityService.getTopCommunityList());
    }

    /*
     * 내 게시글 조회
    */
    @GetMapping("/board/my/{mem_id}")
    public List<Community> myBoard(@PathVariable("mem_id") String mem_id) {
        return communityService.myBoard(mem_id);
    }
}