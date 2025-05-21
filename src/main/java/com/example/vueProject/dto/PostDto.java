package com.example.vueProject.dto;

import lombok.Data;
import com.example.vueProject.dto.AttachmentDto;
import java.time.LocalDate;
import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String username;
	private LocalDate createdAt;
	private List<AttachmentDto> attachments;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<AttachmentDto> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AttachmentDto> attachments) {
		this.attachments = attachments;
	}
}
