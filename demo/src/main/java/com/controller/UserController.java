package com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.dto.AppUserDTO;
import com.entity.dto.ConfirmationDTO;
import com.entity.dto.ResetDTO;
import com.entity.dto.UserConfirmationResponseDTO;
import com.entity.dto.UserDTO;
import com.entity.dto.UserFoodDTO;
import com.entity.dto.UserFoodResponseDTO;
import com.entity.dto.UserPackageDTO;
import com.entity.dto.UserPackageResponseDTO;
import com.entity.dto.UserRoomDTO;
import com.entity.dto.UserRoomResponseDTO;
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
	public boolean addUser(@Valid @RequestBody UserDTO user) {
		return userServices.saveUser(user);
	}
	
	@PostMapping("/updateUser")
	public boolean updateUser(Authentication authentication, @RequestBody AppUserDTO appUserDTO) {
		String email = authentication.getPrincipal().toString();
		return userServices.updateUser(email, appUserDTO);
	}


	@PostMapping("/updatePassLink")
	public boolean updatePassword(@RequestBody ResetDTO email) {
		return userServices.getLink(email.getEmail());
	}

	@PostMapping("/updatePassword")
	public boolean resetPassword(@RequestBody ResetDTO email) {
		return userServices.resetPassword(email);
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

	@GetMapping("/getUsers")
	public List<UserConfirmationResponseDTO> getUsers(Authentication authentication) {
		String userEmail = authentication.getPrincipal().toString();
		return userServices.getUsers(userEmail);
	}
	
	@GetMapping("/getUserPackages")
	public List<UserPackageResponseDTO> getUserPackages(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.getUserPackages(email);
	}

	@PostMapping("/updateUserPackage")
	public boolean updateUserPackage(Authentication authentication, @Valid @RequestBody UserPackageDTO userPackage) {
		String email = authentication.getPrincipal().toString();
		return userServices.updateUserPackage(email, userPackage);
	}
	
	@GetMapping("/deleteUserPackage")
	public boolean deleteUserPackage(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.deletePackage(email);
	}
	
	@GetMapping("/deleteUserFood")
	public boolean deleteUserFood(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.deleteFood(email);
	}
	
	@GetMapping("/deleteUserRoom")
	public boolean deleteUserRoom(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.deleteRoom(email);
	}

	@GetMapping("/getUserRooms")
	public List<UserRoomResponseDTO> getUserRooms(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.getUserRooms(email);
	}

	@PostMapping("/updateUserRoom")
	public boolean updateUserRoom(Authentication authentication, @Valid @RequestBody UserRoomDTO userRoom) {
		String email = authentication.getPrincipal().toString();
		return userServices.updateUserRoom(email, userRoom);
	}

	@GetMapping("/getUserFoods")
	public List<UserFoodResponseDTO> getUserFoods(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.getUserFoods(email);
	}

	@PostMapping("/updateUserFood")
	public boolean updateUserFood(Authentication authentication, @Valid @RequestBody UserFoodDTO userFood) {
		String email = authentication.getPrincipal().toString();
		return userServices.updateUserFood(email, userFood);
	}

	@GetMapping("/getFoods")
	public List<FoodsResponse> getFoods(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.foods(email);
	}

	@GetMapping("/getRooms")
	public List<RoomsResponse> getRooms(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		return userServices.rooms(email);
	}

	@PostMapping("/selectPackage")
	public boolean addSelectPackage(Authentication authentication, @Valid @RequestBody UserPackageDTO pckg) {
		String email = authentication.getPrincipal().toString();
		userServices.addUserPackagewithAdminandAppUser(pckg, email, pckg.getDate());
		return true;
	}

	@PostMapping("/selectRoom")
	public boolean addSelectRoom(Authentication authentication, @Valid @RequestBody UserRoomDTO pckg) {
		String email = authentication.getPrincipal().toString();
		userServices.addRoomToUser(pckg, email);
		return true;
	}

	@PostMapping("/selectFood")
	public boolean addSelectFood(Authentication authentication, @Valid @RequestBody UserFoodDTO pckg) {
		String email = authentication.getPrincipal().toString();
		userServices.addFoodToUser(pckg, email);
		return true;
	}

	@PostMapping("/calculateBill")
	public UserConfirmationResponseDTO calculateBill(Authentication authentication,
			@Valid @RequestBody ConfirmationDTO pckg) {
		String email = authentication.getPrincipal().toString();
		return userServices.calculateBill(email, pckg.getBalance());

	}

	@PostMapping("/addConfirmation")
	public boolean addConfirmation(Authentication authentication, @Valid @RequestBody ConfirmationDTO pckg) {
		String email = authentication.getPrincipal().toString();
		return userServices.addConfirmationToUser(email, pckg.getBalance());
	}

}
