package com.example.vueProject.serviceImpl;

import com.example.vueProject.dto.PostDto;
import com.example.vueProject.entity.Post;
import com.example.vueProject.repository.PostRepository;
import com.example.vueProject.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    // 엔티티를 DTO로 변환하는 헬퍼 메서드
    private PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setWriter(post.getWriter());
        dto.setUsername(post.getUsername());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }
    
    // DTO를 엔티티로 변환하는 헬퍼 메서드
    private Post convertToEntity(PostDto dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setWriter(dto.getWriter());
        post.setUsername(dto.getUsername());
        post.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDate.now());
        return post;
    }
    
    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post->this.convertToDto(post))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.findByTitleContainingOrContentContainingOrWriterContaining(query, query, query);
        return posts.stream()
                    .map(post ->this.convertToDto(post))
                    .collect(Collectors.toList());
    }
    
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = convertToEntity(postDto);
        post.setCreatedAt(LocalDate.now());
        Post saved = postRepository.save(post);
        return convertToDto(saved);
    }

	@Override
	public PostDto getPostById(Long id) {
		 return postRepository.findById(id)
		            .map(post -> this.convertToDto(post))
		            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));
	}

	@Override
	public List<PostDto> getPostByUsername(String user) {
		List<Post> posts = postRepository.findByUsername(user);
		return posts.stream()
				.map(post -> this.convertToDto(post))
				.collect(Collectors.toList());
	}


}