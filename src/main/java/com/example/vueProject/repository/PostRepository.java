package com.example.vueProject.repository;

import com.example.vueProject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	//제목, 내용, 작성자 하나라도 검색어 포함 게시글 조회
    List<Post> findByTitleContainingOrContentContainingOrWriterContaining(String title, String content, String writer);
    
}	
