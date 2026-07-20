package com.pknu.backend.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

import com.pknu.backend.model.Comment;
import com.pknu.backend.model.CommentId;
import com.pknu.backend.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@RequestMapping("/comment")
@CrossOrigin("*")

public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 조회
    */
    @GetMapping("/list/{comid}")
    public List<Comment> getCommentList(@PathVariable Integer comid){

        return this.commentService.getCommentList(comid);
    }

    /**
     * 댓글 등록
     */
    @PostMapping("/insert")
    public Comment setCommentInsert(@RequestBody Comment comment){

        return this.commentService.setCommentInsert(comment);
    }

    /**
     * 댓글 삭제
    */
    @DeleteMapping("/delete")
    public String setCommentDelete(

        @RequestParam Integer comid,
        @RequestParam String memid,
        @RequestParam String commentcreated){

        commentcreated = commentcreated.replace("T", " ").substring(0,16);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime time = LocalDateTime.parse(commentcreated, formatter);

        CommentId commentId = new CommentId(comid, memid, time);

        return this.commentService.setCommentDelete(commentId);
    }

    /**
    * 댓글 수정
    */
    @PutMapping("/update")

    public Comment setCommentUpdate(@RequestBody Comment comment){

        return this.commentService.setCommentUpdate(comment);
    }
}