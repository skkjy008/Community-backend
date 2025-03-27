package com.example.vueProject.controller;

import com.example.vueProject.dto.CommentDto;
import com.example.vueProject.domain.MasterRes;
import com.example.vueProject.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards/{postId}/api/comments")
@CrossOrigin(origins = "http://localhost:8080")
public class CommentController {

    private final CommentService commentService;
    
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    // 모든 댓글 조회 (필요하다면 특정 게시글에 대한 댓글 조회도 고려)
    @GetMapping
    public ResponseEntity<MasterRes<List<CommentDto>>> getCommentByPost(@PathVariable("postId") Long postId) {
        List<CommentDto> comments = commentService.getCommentByPostId(postId);
        return ResponseEntity.ok(new MasterRes<>(200, "댓글 조회 성공", comments));
    }
    
    // 새 댓글 생성
    @PostMapping
    public ResponseEntity<MasterRes<CommentDto>> createComment(@PathVariable("postId") Long postId, 
    													@RequestBody CommentDto commentDto) {
    	commentDto.setPostId(postId);
    	CommentDto created = commentService.createComment(commentDto);
    	return ResponseEntity.ok(new MasterRes<>(200, "댓글 생성 성공",created));
     
    }
    

    @PostMapping("/{parentId}/reply")
    public ResponseEntity<MasterRes<CommentDto>> createReply(
            @PathVariable("parentId") Long parentId,
            @RequestBody CommentDto commentDto) {
        // 만약 댓글에 댓글 기능을 구현하려면, CommentDto와 Comment 엔티티에 부모 댓글을 나타내는 필드가 필요합니다.
        commentDto.setParentComment(commentService.getCommentById(parentId)); // DTO에 부모 댓글 ID 설정 (필드 추가 필요)
        CommentDto created = commentService.createComment(commentDto);
        return ResponseEntity.ok(new MasterRes<>(200, "답글 생성 성공", created));
    }
    
    @DeleteMapping("/{commentId}")
    public ResponseEntity<MasterRes> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId)
    {
    	commentService.deleteComment(commentId);
    	return ResponseEntity.ok(new MasterRes<>(200, "댓글 삭제 완료"));
    }
    
    
    @PutMapping("/{commentId}")
    public ResponseEntity<MasterRes<CommentDto>> updateComment(
    		@PathVariable("postId") Long postId,
    		@PathVariable("commentId") Long commentId,
    		@RequestBody CommentDto commentDto){
    	
    	commentDto.setPostId(postId);
    	CommentDto updated = commentService.updateComment(commentId,commentDto);
    	return ResponseEntity.ok(new MasterRes<>(200, "댓글 수정 성공",updated));
    }
    
}
