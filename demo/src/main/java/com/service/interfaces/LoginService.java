package com.service.interfaces;

import org.springframework.security.core.userdetails.User;

import com.entity.model.classes.Role;

public interface LoginService {

	Role saveRole(Role role);

	User loadUser(String username);


}
