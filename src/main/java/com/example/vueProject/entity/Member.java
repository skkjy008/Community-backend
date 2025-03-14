package com.example.vueProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "member")

public class Member {
	
    public Member() {}
	
    // 생성자
    public Member(String username, String nickname, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(nullable = false, unique = true, name= "username") // NOT NULL + UNIQUE 제약조건
    private String username;

    @Column(nullable = false, name= "nickname")
    private String nickname;

    @Column(nullable = false, name= "email")
    private String email;

    @Column(nullable = false, name= "password")
    private String password;
    
    @Column(name= "refreshToken")
    private String refreshToken;
    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


    public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	// 추가 필드 (예: 가입일)
    @Column(updatable = false)
    private String createdAt = java.time.LocalDate.now().toString();

    

}
