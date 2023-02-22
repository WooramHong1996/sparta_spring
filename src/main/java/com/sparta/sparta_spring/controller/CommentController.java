package com.sparta.sparta_spring.controller;


import com.sparta.sparta_spring.dto.CommentDto;
import com.sparta.sparta_spring.entity.Blog;
import com.sparta.sparta_spring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;


//    // 요구사항1. 전체 게시글 목록 조회 API (GET)
//    @GetMapping("/blogs")
//    public ResponseEntity<List<CommentDto.Response>> getBlogs() {
//        return commentService.getComment();
//    }

    // 요구사항1. 댓글 작성 API (POST)
    @PostMapping("/{id}")
    public ResponseEntity<Object> createComment(@PathVariable Long id,@RequestBody CommentDto.Request commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(id, commentRequestDto, request);
    }

//    // 요구사항3. 선택한 게시글 조회 API (GET)
//    @GetMapping("/blogs/{id}")
//    public ResponseEntity<Object> getBlogs(@PathVariable Long id) {
//        return blogService.getBlogs(id);
//    }


    // 요구사항2. 선택한 댓글 수정 API (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable Long blogId, @PathVariable Long id, @RequestBody CommentDto.Request commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(blogId, id, commentRequestDto, request);
    }

    // 요구사항3. 선택한 댓글 삭제 API (DEL)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long blogId, @PathVariable Long id, HttpServletRequest request) {
        return commentService.deleteComment(blogId, id, request);
    }
}
