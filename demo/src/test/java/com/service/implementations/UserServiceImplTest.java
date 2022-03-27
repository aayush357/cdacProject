package com.service.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.entity.model.classes.AppUser;
import com.repo.AppUserRepo;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
	@InjectMocks
	private UserServicesImpl userServicesImpl;
	
	@Mock
	private AppUserRepo appUserRepo;
	
	@Test
	void check() {
		AppUser user = new AppUser();
		user.setEmail("ashish@123");
		when(appUserRepo.findByEmail(any())).thenReturn(Optional.of(user));
		assertEquals("ashish@123", userServicesImpl.getUser(user.getEmail()).getEmail());
		
		
		
		
//		userServicesImpl.getUser(null)
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
