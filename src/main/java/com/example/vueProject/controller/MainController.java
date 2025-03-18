package com.example.vueProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vueProject.domain.MasterRes;
import com.example.vueProject.dto.AuthResponse;
import com.example.vueProject.dto.LoginRequest;
import com.example.vueProject.dto.RefreshTokenRequest;
import com.example.vueProject.dto.RegisterRequest;
import com.example.vueProject.entity.Member;
import com.example.vueProject.jwt.JwtMaker;
import com.example.vueProject.service.MemberService;
import com.example.vueProject.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MainController {
	

	private final JwtMaker jwtmaker;
	private final MemberService memberservice;
	
	   @Autowired
	   public MainController(JwtMaker jwtmaker, MemberService memberservice) {
	        this.jwtmaker = jwtmaker;
	        this.memberservice = memberservice;
	    }
	

	@PostMapping(value = "/register", produces = "application/json")
	public ResponseEntity<MasterRes> registerMember(@RequestBody RegisterRequest request)
	{
	    System.out.println("[INFO] Username: " + request.getUsername());
	    System.out.println("[INFO] Email: " + request.getEmail());
		
		int result = memberservice.countMemberByUsername(request.getUsername());
		
	    if (result == 1) {
	    	 MasterRes res = new MasterRes(4001, "중복된 계정이 존재합니다.");
	    	 return ResponseEntity.badRequest().body(res);
	    } else {
	        Member member = memberservice.registerMember(request);
	        MasterRes res = new MasterRes(2001, "계정 생성이 완료되었습니다.", member.getUsername());
	        return ResponseEntity.ok(res);
	    }
		
	}
	
	  @PostMapping("/login") public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
	  {
		  if(request.getUsername()==""||request.getPassword()=="")
		  {
			  return ResponseEntity.status(402)
					  .body(new AuthResponse(402,"ID or password is Null",null,null));
		  }
		  
		  Member member = memberservice.login(request.getUsername(), request.getPassword());
		  System.out.println(request.getUsername());
		  System.out.println(request.getPassword());
		  if (member==null)
		  {
              return ResponseEntity.status(401)
                      .body(new AuthResponse(401, "Invalid refresh token", null, null));
		  }
	       String accessToken = jwtmaker.generateAccessToken(member);
	       String refreshToken = jwtmaker.generateRefreshToken(member);
	       
	       memberservice.updateRefreshToken(member.getId(), refreshToken);
	       
	       
		  AuthResponse authResponse = new AuthResponse(2000, "로그인 성공",accessToken,refreshToken,member.getNickname());
		  System.out.println(accessToken);
		  System.out.println(refreshToken);
		  return ResponseEntity.ok(authResponse);
	  }
	  
	    // 토큰 재발급: refresh 토큰 검증 후 새로운 access 토큰 발급
	    @PostMapping("/refresh")
	    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
	        String refreshToken = request.getRefreshToken();
	        if (!jwtmaker.validateToken(refreshToken)) {
	            return ResponseEntity.status(401)
	                .body(new AuthResponse(401, "Refresh token expired. Please login again.", null, null));
	        }
	        String username = jwtmaker.getUsernameFromToken(refreshToken);
	        Member member = memberservice.findByUsername(username);
	        if (member == null || !refreshToken.equals(member.getRefreshToken())) {
	            return ResponseEntity.status(401)
	                .body(new AuthResponse(401, "Invalid refresh token. Please login again.", null, null));
	        }
	        // 유효한 refresh 토큰이면 새로운 access 토큰 발급
	        String newAccessToken = jwtmaker.generateAccessToken(member);
	        AuthResponse authResponse = new AuthResponse(2002, "Token refreshed", newAccessToken, refreshToken);
	        return ResponseEntity.ok(authResponse);
	    }
	  
	 
    
    @GetMapping("/success")
    public String success()
    {
		return "로그인 성공";
    	
    }
    
    @GetMapping("/")
    public String start()
    {
    	return "시작 페이지입니다.";
    }
 
    
}
