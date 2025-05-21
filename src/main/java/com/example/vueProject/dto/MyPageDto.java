package com.example.vueProject.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class MyPageDto {
	
	private MemberDto member;
	private List<PostDto> posts;
	
	@Autowired
	public MyPageDto(MemberDto member, List<PostDto> posts)
	{
		this.member = member;
		this.posts = posts;
		
	}

	public List<PostDto> getPosts() {
		return posts;
	}
	public void setPosts(List<PostDto> posts) {
		this.posts = posts;
	}
	public MemberDto getMember() {
		return member;
	}
	public void setMember(MemberDto member) {
		this.member = member;
	}
	
	

}
