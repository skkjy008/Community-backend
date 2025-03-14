package com.example.vueProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vueProject.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	int countMemberByUsername(String username);
	Member findByUsername(String username);
}
 