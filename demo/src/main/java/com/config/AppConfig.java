package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jwt.JwtProvider;
import com.jwt.LoggedOutJwtTokenCache;

@Configuration
public class AppConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtProvider jwtProvider(LoggedOutJwtTokenCache loggedOutJwtTokenCache) {
		JwtProvider jwtProvider = new JwtProvider();
		jwtProvider.setLoggedOutJwtTokenCache(loggedOutJwtTokenCache);
		return jwtProvider;
	}
	
	@Bean
	public LoggedOutJwtTokenCache loggedOutJwtTokenCache() {
		LoggedOutJwtTokenCache loggedOutJwtTokenCache = new LoggedOutJwtTokenCache();
		loggedOutJwtTokenCache.setTokenProvider(jwtProvider(loggedOutJwtTokenCache));
		return loggedOutJwtTokenCache;
	}
}
