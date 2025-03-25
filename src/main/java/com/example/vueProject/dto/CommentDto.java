package com.example.vueProject.dto;

import java.time.LocalDate;

import com.example.vueProject.entity.Comment;

import lombok.Data;

@Data
public class CommentDto {
	
	private Long id;
	private String content;
	private String writer;
	private LocalDate createdAt;
	private CommentDto parentComment;
	
	private Long postId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public CommentDto getParentComment() {
		return parentComment;
	}
	public void setParentComment(CommentDto commentDto) {
		this.parentComment = commentDto;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}



}
