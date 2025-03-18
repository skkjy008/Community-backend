package com.example.vueProject.controller;

import com.example.vueProject.domain.MasterRes;
import com.example.vueProject.dto.PostDto;
import com.example.vueProject.entity.Post;
import com.example.vueProject.service.PostService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins = "http://localhost:8081")
public class BoardController {
    private final PostService postService;
    
    public BoardController(PostService postService) {
        this.postService = postService;
    }
    
    // 전체 게시글 조회: GET /api/board
    @GetMapping
    public MasterRes<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return new MasterRes<>(200, "게시글 조회 성공", posts);
    }
    
    // 게시글 검색: GET /api/board/search?query=검색어
    @GetMapping("/search")
    public MasterRes<List<PostDto>> searchPosts(@RequestParam("query") String query) {
        List<PostDto> posts = postService.searchPosts(query);
        return new MasterRes<>(200, "게시글 검색 성공", posts);
    }
    
    // 게시글 생성: POST /api/board
    @PostMapping("/create")
    public MasterRes<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);
        return new MasterRes<>(200, "게시글 생성 성공", createdPost);
    }
}