package com.service.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.entity.enums.AdminEnum;
import com.entity.model.classes.Admin;
import com.entity.model.classes.AppUser;
import com.entity.model.classes.Role;
import com.exception.EntityNotFoundException;
import com.repo.AdminRepo;
import com.repo.AppUserRepo;
import com.repo.RoleRepo;
import com.service.interfaces.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService, UserDetailsService {
	private final AppUserRepo userRepo;
	private final RoleRepo roleRepo;
	private final AdminRepo adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password;
		String email;
		Optional<AppUser> user = userRepo.findByEmail(username);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (user.isEmpty() == true) {
			System.out.println("checking admin");
			Admin admin = adminRepo.findByEmail(username).orElseThrow(
					() -> new UsernameNotFoundException(AdminEnum.ADMINNOTFOUND.toString()));
			if (admin == null) {
				log.error("user not found in database");
				throw new UsernameNotFoundException(AdminEnum.ADMINNOTFOUND.toString());
			} else {
				email = admin.getEmail();
				password = admin.getPassword();
				authorities.add(new SimpleGrantedAuthority(admin.getRole().getName()));
			}
		} else {
			email = user.get().getEmail();
			password = user.get().getPassword();
			authorities.add(new SimpleGrantedAuthority(user.get().getRole().getName()));
		}
//		user.getRoles().forEach(role -> {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		});
		return new User(email, password, authorities);
	}

	@Override
	public User loadUser(String username) throws UsernameNotFoundException {
		Optional<AppUser> user = userRepo.findByEmail(username);
		String password;
		String email;
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (user.isEmpty()) {
			Admin admin = adminRepo.findByEmail(username).orElseThrow(
					() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
			if (admin == null) {
				log.error("user not found in database");
				throw new UsernameNotFoundException(AdminEnum.ADMINNOTFOUND.toString());
			} else {
				email = admin.getEmail();
				password = admin.getPassword();
				authorities.add(new SimpleGrantedAuthority(admin.getRole().getName()));
			}
		} else {
			email = user.get().getEmail();
			password = user.get().getPassword();
			authorities.add(new SimpleGrantedAuthority(user.get().getRole().getName()));
		}
		return new User(email, password, authorities);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("saving role {} to db", role.getName());
		return roleRepo.save(role);
	}

}
