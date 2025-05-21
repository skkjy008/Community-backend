package com.example.vueProject.serviceImpl;

import com.example.vueProject.dto.AttachmentDto;
import com.example.vueProject.dto.PostDto;
import com.example.vueProject.entity.Attachment;
import com.example.vueProject.entity.Post;
import com.example.vueProject.repository.PostRepository;
import com.example.vueProject.service.FileStorageService;
import com.example.vueProject.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FileStorageService fileStorageService;
    
    public PostServiceImpl(PostRepository postRepository, FileStorageService fileStorageService) {
        this.postRepository = postRepository;
        this.fileStorageService = fileStorageService;
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
        return postRepository.findAllByOrderByIdDesc().stream()
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
	public PostDto getPostByIdAsc(Long id) {
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

	@Override
	public PostDto createPostWithFiles(PostDto dto, MultipartFile[] files) {
        // 1) DTO → 엔티티
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setWriter(dto.getWriter());
        post.setUsername(dto.getUsername());
        post.setCreatedAt(LocalDate.now());

        // 2) 파일 저장 및 Attachment 엔티티 연결
        for (MultipartFile file : files) {
            String url = fileStorageService.store(file);
            Attachment att = new Attachment();
            att.setFileName(file.getOriginalFilename());
            att.setUrl(url);
            att.setPost(post);
            post.getAttachments().add(att);
        }

        // 3) 저장 (CascadeType.ALL 로 Attachment 동시 저장)
        Post saved = postRepository.save(post);

        // 4) 엔티티 → DTO
        PostDto out = new PostDto();
        out.setId(saved.getId());
        out.setTitle(saved.getTitle());
        out.setContent(saved.getContent());
        out.setWriter(saved.getWriter());
        out.setUsername(saved.getUsername());
        out.setCreatedAt(saved.getCreatedAt());
        out.setAttachments(
            saved.getAttachments().stream().map(a -> {
                AttachmentDto ad = new AttachmentDto();
                ad.setId(a.getId());
                ad.setFileName(a.getFileName());
                ad.setUrl(a.getUrl());
                return ad;
            }).collect(Collectors.toList())
        );

        return out;
	}


}