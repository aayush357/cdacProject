package com.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.dto.LogOutRequest;
import com.entity.dto.RefreshDTO;
import com.entity.enums.LoginEnum;
import com.entity.model.classes.AppUser;
import com.event.OnUserLogoutSuccessEvent;
import com.exception.TokenNotFoundException;
import com.jwt.JwtProvider;
import com.response.ApiReponse;
import com.service.interfaces.LoginService;
import com.service.interfaces.UserServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class LoginController {
	private final LoginService loginService;
	private final UserServices userServices;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final JwtProvider jwtProvider;

	@GetMapping("/users")
	public ResponseEntity<List<AppUser>> getUsers() {
		return ResponseEntity.ok().body(userServices.getUsers());
	}

	@PutMapping("/logout")
	public ResponseEntity<ApiReponse> logoutUser(@AuthenticationPrincipal String currentUser,
			@RequestBody LogOutRequest logOutRequest) {
		OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(currentUser,
				logOutRequest.getToken(), logOutRequest);
		applicationEventPublisher.publishEvent(logoutSuccessEvent);
		return ResponseEntity.ok(new ApiReponse(true, LoginEnum.LOGGEDOUT.toString()));
	}

//	@GetMapping("/token/refresh")
//	public ResponseEntity<Map<String, String>> refreshToken(HttpServletRequest request, HttpServletResponse response){
//		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//		ResponseEntity<Map<String, String>> result = null;
//		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//			result = jwtProvider.generateRefreshToken(authorizationHeader, loginService, request);
//			return result;
//		}
//		throw new TokenNotFoundException(LoginEnum.TOKENNOTFOUND.toString(), HttpStatus.FORBIDDEN);
//	}

	@PostMapping("/token/refresh")
	public ResponseEntity<Map<String, String>> refreshToken(HttpServletRequest request, HttpServletResponse response, @RequestBody RefreshDTO refresh){
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		ResponseEntity<Map<String, String>> result = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			result = jwtProvider.generateRefreshToken(refresh.getRefreshToken(), loginService, request);
			return result;
		}
		throw new TokenNotFoundException(LoginEnum.TOKENNOTFOUND.toString(), HttpStatus.FORBIDDEN);
	}

	
}
