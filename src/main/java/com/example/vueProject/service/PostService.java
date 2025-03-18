package com.example.vueProject.service;

import com.example.vueProject.dto.PostDto;
import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();
    List<PostDto> searchPosts(String query);
    PostDto createPost(PostDto postDto);
}
