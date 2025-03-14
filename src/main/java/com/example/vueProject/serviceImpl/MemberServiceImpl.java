package com.example.vueProject.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.vueProject.dto.RegisterRequest;
import com.example.vueProject.entity.Member;
import com.example.vueProject.repository.MemberRepository;
import com.example.vueProject.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository memberrepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Member registerMember(RegisterRequest request) {
		Member member = new Member(
				request.getUsername(),
				request.getNickname(),
				request.getEmail(),
				passwordEncoder.encode(request.getPassword())
				);
		return memberrepository.save(member);
	}

	@Override
	public int countMemberByUsername(String username) {
	    if (username == null || username.trim().isEmpty()) {
	        System.out.println("[ERROR] Username is null or empty");
	        return 0;
	    }
		return memberrepository.countMemberByUsername(username);

		
	}

	@Override
	public Member login(String username, String password) {
	    Member member = memberrepository.findByUsername(username);
	    if (member != null && passwordEncoder.matches(password, member.getPassword())) {
	        return member;
	    
	}
		return null;

}

	@Override
	public void updateRefreshToken(Long memberId, String refreshToken) {
        Optional<Member> optionalMember = memberrepository.findById(memberId);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            member.setRefreshToken(refreshToken);
            memberrepository.save(member);
	}
	
}

	@Override
	public Member findByUsername(String username) {
		
		return memberrepository.findByUsername(username);
	}
	
}
