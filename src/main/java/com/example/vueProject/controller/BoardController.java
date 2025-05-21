package com.example.vueProject.controller;

import com.example.vueProject.domain.MasterRes;
import com.example.vueProject.dto.PostDto;
import com.example.vueProject.entity.Post;
import com.example.vueProject.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins = "http://localhost:8080")
public class BoardController {
	
	@Autowired
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
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MasterRes<PostDto> createPostWithFiles(
        @RequestPart("post") PostDto postDto,
        @RequestPart(value = "files", required = false) MultipartFile[] files
    ) {
        if (files == null) {
            files = new MultipartFile[0];
        }
        PostDto created = postService.createPostWithFiles(postDto, files);
        return new MasterRes<>(2002, "게시글 생성 성공", created);
    }
    
    @GetMapping("/{id}")
    public MasterRes<PostDto> getPost(@PathVariable("id") Long id)
    {
    	PostDto post = postService.getPostByIdAsc(id);
    	return new MasterRes<>(200, "게시글 조회 성공",post);
    	
    }
    
}