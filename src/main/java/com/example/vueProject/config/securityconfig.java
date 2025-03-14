package com.example.vueProject.config;

import java.util.List;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class securityconfig {
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (POST 요청 가능하도록)
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/register").permitAll() // 특정 API 허용
            .anyRequest().permitAll());
        return http.build();
    }


	public CorsConfigurationSource corsConfigurationSource() {
		
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(List.of("http://localhost:8080")); // Vue.js 도메인 허용
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    config.setAllowedHeaders(List.of("*"));
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}


	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
	

	   @Bean
	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }
		

	   @Bean
	   public BCryptPasswordEncoder getPasswordEncoder()
	   {
	     return new BCryptPasswordEncoder();
	   }
}
