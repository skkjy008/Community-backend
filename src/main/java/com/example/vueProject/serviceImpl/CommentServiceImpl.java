package com.example.vueProject.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vueProject.dto.CommentDto;
import com.example.vueProject.entity.Comment;
import com.example.vueProject.entity.Post;
import com.example.vueProject.repository.CommentRepository;
import com.example.vueProject.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentrepository;
	
	CommentServiceImpl(CommentRepository commentrepository)
	{
		this.commentrepository = commentrepository;
	}
	
	
	private CommentDto convertToDto(Comment comment)
	{
		CommentDto dto = new CommentDto();
		dto.setId(comment.getId());
		dto.setContent(comment.getContent());
		dto.setCreatedAt(comment.getCreateAt());
		dto.setWriter(comment.getWriter());
		
		   if (comment.getParentComment() != null) {
		        CommentDto parentDto = new CommentDto();
		        parentDto.setId(comment.getParentComment().getId());
		        dto.setParentComment(parentDto);
		    }
		   
		   if(comment.getPost() != null)
		   {
			   dto.setPostId(comment.getPost().getId());
		   }
		return  dto;
	}
	
	private Comment convertToEntiry(CommentDto dto)
	{
		Comment comment = new Comment();
		comment.setId(dto.getId());
		comment.setContent(dto.getContent());
		comment.setCreateAt(dto.getCreatedAt());
		comment.setWriter(dto.getWriter());
	    if (dto.getParentComment() != null && dto.getParentComment().getId() != null) {
	        Comment parent = new Comment();
	        parent.setId(dto.getParentComment().getId());
	        comment.setParentComment(parent);
	    }
	    
	    if(dto.getPostId() != null)
	    {
	    	Post post = new Post();
	    	post.setId(dto.getPostId());
	    	comment.setPost(post);
	    }
	    
		return comment;
		
	}

	@Override
	public List<CommentDto> getAllComment() {
		return commentrepository.findAll().stream()
				.map(comment -> this.convertToDto(comment))
				.collect(Collectors.toList());
	}

	@Override
	public CommentDto createComment(CommentDto commentDto) {
		Comment comment = convertToEntiry(commentDto);
		comment.setCreateAt(LocalDate.now());
		Comment saved = commentrepository.save(comment);
		return convertToDto(saved);
	}


	@Override
	public CommentDto getCommentById(Long id) {
		 return commentrepository.findById(id)
		            .map(comment -> this.convertToDto(comment))
		            .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id: " + id));
	}


	@Override
	public List<CommentDto> getCommentByPostId(Long postId) {

		return commentrepository.findByPost_Id(postId).stream()
				.map(comment -> this.convertToDto(comment))
				.collect(Collectors.toList());
	}


	@Override
	public void deleteComment(Long commentId) {
		
		if(!commentrepository.existsById(commentId))
		{
			throw new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id:"+commentId);
		}
		
		commentrepository.deleteById(commentId);
		
	}


	@Override
	public CommentDto updateComment(Long commentId, CommentDto commentDto) {
		
		Comment comment = commentrepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id: " + commentId));
		comment.setContent(commentDto.getContent());
		
		Comment updated = commentrepository.save(comment);
		return convertToDto(updated);
		
	}

}
