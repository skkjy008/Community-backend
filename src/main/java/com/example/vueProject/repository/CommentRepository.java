package com.example.vueProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vueProject.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByParentComment(Comment parentComment);
	
	List<Comment> findByPost_Id(Long postId);
	
}
