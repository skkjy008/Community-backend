package com.example.vueProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vueProject.domain.MasterRes;
import com.example.vueProject.dto.MemberDto;
import com.example.vueProject.dto.MyPageDto;
import com.example.vueProject.dto.PostDto;
import com.example.vueProject.dto.RegisterRequest;
import com.example.vueProject.service.MemberService;
import com.example.vueProject.service.PostService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
	
	@Autowired
    private final PostService postService;
	private final MemberService memberService;
	
    public MyPageController(PostService postService, MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }
    
  @GetMapping("/{user}")
  public MasterRes<MyPageDto> getPost(@PathVariable("user") String user)
  {

	  MemberDto memberDto = memberService.getUserByNickname(user);
	  
	  List<PostDto> posts = postService.getPostByWriter(user);
	  
	  MyPageDto myPageDto = new MyPageDto(memberDto,posts);
	  
	  return new MasterRes<>(200, "마이페이지 데이터 조회 성공", myPageDto);

  	
  }
    

}
