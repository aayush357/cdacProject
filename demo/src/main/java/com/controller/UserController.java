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
import com.enitity.dto.UserConfirmationResponseDTO;
import com.enitity.dto.UserDTO;
import com.enitity.dto.UserFoodDTO;
import com.enitity.dto.UserFoodResponseDTO;
import com.enitity.dto.UserPackageDTO;
import com.enitity.dto.UserPackageResponseDTO;
import com.enitity.dto.UserRoomDTO;
import com.enitity.dto.UserRoomResponseDTO;
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
	
	@GetMapping("/getUserPackages")
	public List<UserPackageResponseDTO> getUserPackages(Authentication authentication ) {
		String email = authentication.getPrincipal().toString();
		return userServices.getUserPackages(email);
	}
	
	@PostMapping("/updateUserPackage")
	public boolean updateUserPackage(Authentication authentication, @RequestBody UserPackageDTO userPackage) {
		String email = authentication.getPrincipal().toString();
		return userServices.updateUserPackage(email, userPackage);
	}
	
	@GetMapping("/getUserRooms")
	public List<UserRoomResponseDTO> getUserRooms(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.getUserRooms(email);
	}
	
	@PostMapping("/updateUserRoom")
	public boolean updateUserRoom(Authentication authentication, @RequestBody UserRoomDTO userRoom) {
		String email = authentication.getPrincipal().toString();
		return userServices.updateUserRoom(email, userRoom);
	}
	
	@GetMapping("/getUserFoods")
	public List<UserFoodResponseDTO> getUserFoods(Authentication authentication ) {
		String email = authentication.getPrincipal().toString();
		return userServices.getUserFoods(email);
	}
	
	@PostMapping("/updateUserFood")
	public boolean updateUserFood(Authentication authentication, @RequestBody UserFoodDTO userFood) {
		String email = authentication.getPrincipal().toString();
		return userServices.updateUserFood(email, userFood);
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
	
	@PostMapping("/calculateBill")
	public UserConfirmationResponseDTO calculateBill(Authentication authentication, @RequestBody ConfirmationDTO pckg) {
		String email = authentication.getPrincipal().toString();
		return userServices.calculateBill(email, pckg.getBalance());
		
	}
	
	@PostMapping("/addConfirmation")
	public boolean addConfirmation(Authentication authentication,@RequestBody ConfirmationDTO pckg) {
		String email = authentication.getPrincipal().toString();
		return userServices.addConfirmationToUser(email, pckg.getBalance());
	}
	
}
