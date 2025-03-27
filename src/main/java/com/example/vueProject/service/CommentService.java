package com.example.vueProject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.vueProject.dto.CommentDto;

@Service
public interface CommentService {
	
	List<CommentDto> getAllComment();
	CommentDto createComment(CommentDto commentDto);
	CommentDto getCommentById(Long id);
	List<CommentDto> getCommentByPostId(Long postId);
	void deleteComment(Long commentId);
	CommentDto updateComment(Long commentId, CommentDto commentDto);
}
