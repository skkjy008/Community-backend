package com.example.vueProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vueProject.domain.MasterRes;
import com.example.vueProject.dto.PostDto;
import com.example.vueProject.service.PostService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
	
	@Autowired
    private final PostService postService;
	
    public MyPageController(PostService postService) {
        this.postService = postService;
    }
    
  @GetMapping("/{user}")
  public MasterRes<List<PostDto>> getPost(@PathVariable("user") String user)
  {
  	List<PostDto> posts = postService.getPostByWriter(user);
  	return new MasterRes<>(200, "게시글 조회 성공",posts);
  	
  }
    

}
