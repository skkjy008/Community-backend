package com.example.vueProject.service;

import com.example.vueProject.dto.PostDto;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface PostService {
    List<PostDto> getAllPosts();
    List<PostDto> searchPosts(String query);
    PostDto createPost(PostDto postDto);
	PostDto getPostById(Long id);
	List<PostDto> getPostByUsername(String user);
}
