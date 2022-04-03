package com.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enitity.dto.ConfirmationDTO;
import com.enitity.dto.UserDTO;
import com.enitity.dto.UserFoodDTO;
import com.enitity.dto.UserPackageDTO;
import com.enitity.dto.UserRoomDTO;
import com.entity.model.classes.AppUser;
import com.response.dto.FoodsResponse;
import com.response.dto.PackagesResponse;
import com.response.dto.RoomsResponse;
import com.service.interfaces.UserServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
	private final UserServices userServices;
	
	@PostMapping("/add")
	public boolean addUser(@RequestBody UserDTO user) {
		return userServices.saveUser(user);
	}
	
	@GetMapping("/get")
	public AppUser getUser(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.getUser(email);
	}
	
	@GetMapping("/getPackages")
	public List<PackagesResponse> getPackages() {
		return userServices.packages();
	}
	
	@GetMapping("/getFoods")
	public List<FoodsResponse> getFoods() {
		return userServices.foods();
	}
	
	@GetMapping("/getRooms")
	public List<RoomsResponse> getRooms() {
		return userServices.rooms();
	}
	
	@PostMapping("/selectPackage")
	public boolean addSelectPackage(Authentication authentication,@RequestBody UserPackageDTO pckg) {
		String email = authentication.getPrincipal().toString();
		userServices.addUserPackagewithAdminandAppUser(pckg, email, new Date(System.currentTimeMillis()));
		return true;
	}
	
	@PostMapping("/selectRoom")
	public boolean addSelectRoom(Authentication authentication,@RequestBody UserRoomDTO pckg) {
		String email = authentication.getPrincipal().toString();
		userServices.addRoomToUser(pckg, email);			
		return true;
	}
	
	@PostMapping("/selectFood")
	public boolean addSelectFood(Authentication authentication,@RequestBody UserFoodDTO pckg) {
		String email = authentication.getPrincipal().toString();
		userServices.addFoodToUser(pckg, email);			
		return true;
	}
	
	@PostMapping("/addConfirmation")
	public boolean addConfirmation(Authentication authentication,@RequestBody ConfirmationDTO pckg) {
		String email = authentication.getPrincipal().toString();
		userServices.addConfirmationToUser(email, pckg.getBalance());			
		return true;
	}
	
}
