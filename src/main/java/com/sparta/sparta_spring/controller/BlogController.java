package com.sparta.sparta_spring.controller;

import com.sparta.sparta_spring.dto.BlogDto;
import com.sparta.sparta_spring.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BlogController {
    private final BlogService blogService;


    // 요구사항1. 전체 게시글 목록 조회 API (GET)
    @GetMapping("/blogs")
    public ResponseEntity<List<BlogDto.Response>> getBlogs() {
        return blogService.getBlogs();
    }

    // 요구사항2. 게시글 작성 API (POST)
    @PostMapping("/blogs")
    public ResponseEntity<Object> createBlog(@RequestBody BlogDto.Request blogrequestDto, HttpServletRequest request) {
        return blogService.createBlog(blogrequestDto, request);
    }

    // 요구사항3. 선택한 게시글 조회 API (GET)
    @GetMapping("/blogs/{id}")
    public ResponseEntity<Object> getBlogs(@PathVariable Long id) {
        return blogService.getBlogs(id);
    }


    // 요구사항4. 선택한 게시글 수정 API (PUT)
    @PutMapping("/blogs/{id}")
    public ResponseEntity<Object> updateBlog(@PathVariable Long id, @RequestBody BlogDto.Request blogRequestDto, HttpServletRequest request) {
        return blogService.updateBlog(id, blogRequestDto, request);
    }

    // 요구사항5. 선택한 게시글 삭제 API (DEL)
    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Object> deleteBlog(@PathVariable Long id, HttpServletRequest request) {
        return blogService.deleteBlog(id, request);
    }
}
