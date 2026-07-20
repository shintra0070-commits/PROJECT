package com.pknu.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pknu.backend.model.Community;
import com.pknu.backend.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityService {

    private final CommunityRepository communityRepository;

    /**
     * 전체 조회
    */
    public List<Community> getCommunityList() {
        log.info("게시판 전체 조회");
        return this.communityRepository.findAll();
    }

    /**
     * 상세 조회
    */
    public Community getCommunityView(Integer com_id) {
        
        Optional<Community> community = this.communityRepository.findById(com_id);

        if (community.isPresent()) {
            
            Community community_view = community.get();

            // 조회수 증가
            community_view.setCom_view(community_view.getCom_view() + 1);

            this.communityRepository.save(community_view);

            return community_view;
        }

        return null;
    }

    /**
     * 등록
    */
    public Community setCommunityInsert(Community community) {
        
        community.setCom_view(0);
        community.setCom_like(0);

        return this.communityRepository.save(community);
    }

    /**
     * 수정
    */
    public Community setCommunityUpdate(Community p_community) {

        Optional<Community> community = this.communityRepository.findById(p_community.getCom_id());

        if (community.isPresent()) {

            Community community_update = community.get();

            community_update.setCom_category(
                    p_community.getCom_category());

            community_update.setCom_title(
                    p_community.getCom_title());

            community_update.setCom_content(
                    p_community.getCom_content());

            return this.communityRepository
                    .save(community_update);
        }

        return null;
    }

    /**
     * 삭제
    */
    public String setCommunityDelete(Integer com_id) {

        if (this.communityRepository
                .existsById(com_id)) {

            this.communityRepository
                    .deleteById(com_id);

            return "게시글 삭제 성공";
        }

        return "삭제할 게시글 없음";
    }

    /**
     * 좋아요 증가
    */
    public Community setCommunityLike(Integer com_id) {

        Optional<Community> community = this.communityRepository
                .findById(com_id);

        if (community.isPresent()) {

            Community community_like = community.get();

            community_like.setCom_like(community_like.getCom_like() + 1);

            return this.communityRepository.save(community_like);
        }

        return null;
    }
   
    /**
     * Paging 처리
    */
    public Page<Community>getCommunityListPaging(int page, int size){
            log.info("게시판 Paging 조회");

            Pageable pageable = PageRequest.of(page, size);
            
            return this.communityRepository.findAllPaging(pageable);
        }

    /**
     * 인기글 조회
    */
    public List<Community> getTopCommunityList(){
        return this.communityRepository.findTopCommunityList();
    }

    /*
    * 마이페이지에서 내 게시글 조회
    * @Autowired : 객체를 자동으로 넣어주는 기능
    */
    @Autowired
    private CommunityRepository repository;

    public List<Community> myBoard(String mem_id) {

        return repository.myBoard(mem_id);
    }
}