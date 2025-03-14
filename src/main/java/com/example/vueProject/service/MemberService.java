package com.example.vueProject.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.example.vueProject.dto.RegisterRequest;
import com.example.vueProject.entity.Member;

@Service
public interface MemberService {

	Member registerMember(RegisterRequest request);
	@Query("SELECT COUNT(m) FROM Member m WHERE m.username = :username")
	int countMemberByUsername(String username);
	Member login(String username, String password);
	void updateRefreshToken(Long memberId, String refreshToken);
	Member findByUsername(String username);

}
