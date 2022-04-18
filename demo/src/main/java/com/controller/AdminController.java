package com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.dto.AdminDTO;
import com.entity.dto.FoodDTO;
import com.entity.dto.PackageDTO;
import com.entity.dto.ResetDTO;
import com.entity.dto.RoomDTO;
import com.entity.dto.UserConfirmationResponseDTO;
import com.entity.enums.AdminEnum;
import com.entity.model.classes.Admin;
import com.entity.model.classes.Food;
import com.entity.model.classes.Package;
import com.entity.model.classes.Role;
import com.entity.model.classes.Room;
import com.exception.EntityNotFoundException;
import com.service.interfaces.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;
	
	@PostMapping("/add")
	public boolean addAdmin(@Valid @RequestBody AdminDTO adminDTO) {
		adminService.saveAdmin(adminDTO);
		return true;
	}
	
	@PostMapping("/updatePassLink")
	public boolean updatePassword(@RequestBody ResetDTO email) {
		return adminService.getLink(email.getEmail());
	}
	
	@PostMapping("/updatePassword")
	public boolean resetPassword(@RequestBody ResetDTO email) {
		return adminService.resetPassword(email);
	}

//	@PostMapping("/delete")
//	public boolean deleteAdmin(@RequestBody String email) {
//		return adminService.delete(email);
//	}

	@PostMapping("/addpackage")
	public boolean addPackage(Authentication authentication,@Valid  @RequestBody PackageDTO pckg) {
		String adminEmail = authentication.getPrincipal().toString();
		adminService.addPackage(pckg, adminEmail);
		return true;
	}
	
	@PostMapping("/deletepackage")
	public boolean deletePackage(Authentication authentication,@Valid  @RequestBody PackageDTO pckg) {
		String adminEmail = authentication.getPrincipal().toString();
		adminService.deletePackage(adminEmail, pckg.getPackageName());
		return true;
	}

	@PostMapping("/addroom")
	public boolean addRoom(Authentication authentication,@Valid  @RequestBody RoomDTO room) {
		String adminEmail = authentication.getPrincipal().toString();
		adminService.addRoom(room, adminEmail);
		return true;
	}
	
	@PostMapping("/deleteroom")
	public boolean deleteRoom(Authentication authentication,@Valid  @RequestBody RoomDTO room) {
		String adminEmail = authentication.getPrincipal().toString();
		adminService.deleteRoom(adminEmail, room.getHotelName());
		return true;
	}

	@PostMapping("/addfood")
	public boolean addFood(Authentication authentication,@Valid  @RequestBody FoodDTO food) {
		String adminEmail = authentication.getPrincipal().toString();
		adminService.addFood(food, adminEmail);
		return true;
	}
	
	@PostMapping("/deletefood")
	public boolean deleteFood(Authentication authentication,@Valid  @RequestBody FoodDTO food) {
		String adminEmail = authentication.getPrincipal().toString();
		adminService.deleteFood(adminEmail, food.getName());
		return true;
	}

	@GetMapping("/foods")
	public List<Food> getFood(Authentication authentication) {
		String adminEmail = authentication.getPrincipal().toString();
		List<Food> foods = adminService.getFood(adminEmail);
		if (foods.size() == 0) {
			throw new EntityNotFoundException(AdminEnum.FOODSNOTPRESENT.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return foods;
	}

	@PostMapping("/food")
	public Food getFood(Authentication authentication,@Valid  @RequestParam("foodName") String foodName) {
		String adminEmail = authentication.getPrincipal().toString();
		Food food = adminService.getFood(foodName, adminEmail);
		if (food == null) {
			throw new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return food;
	}

	@PostMapping("/package")
	public Package getPackage(Authentication authentication,@Valid  @RequestParam("packageName") String packageName) {
		String adminEmail = authentication.getPrincipal().toString();
		Package pckg = adminService.getPackage(packageName, adminEmail);
		if (pckg == null) {
			throw new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return pckg;
	}

	@PostMapping("/room")
	public Room getRoom(Authentication authentication,@Valid  @RequestParam("hotelName") String hotelName) {
		String adminEmail = authentication.getPrincipal().toString();
		Room room = adminService.getRoom(hotelName, adminEmail);
		if (room == null) {
			throw new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return room;
	}

	@GetMapping("/rooms")
	public List<Room> getRoom(Authentication authentication) {
		String adminEmail = authentication.getPrincipal().toString();
		List<Room> rooms = adminService.getRoom(adminEmail);
		if (rooms.size() == 0) {
			throw new EntityNotFoundException(AdminEnum.ROOMSNOTPRESENT.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return rooms;
	}

	@GetMapping("/packages")
	public List<Package> getPackage(Authentication authentication) {
		String adminEmail = authentication.getPrincipal().toString();
		List<Package> packages = adminService.getPackages(adminEmail);
		if (packages.size() == 0) {
			throw new EntityNotFoundException(AdminEnum.PACKAGESNOTPRESENT.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return packages;
	}
	
	@GetMapping("/getUsers")
	public List<UserConfirmationResponseDTO> getUsers(Authentication authentication) {
		String adminEmail = authentication.getPrincipal().toString();
		return adminService.getUsers(adminEmail);
	}
	
	@PostMapping("/updatepackage")
	public boolean updatePackage(Authentication authentication,@Valid  @RequestBody PackageDTO pckg) {
		String adminEmail = authentication.getPrincipal().toString();
		return adminService.updatePackage(adminEmail, pckg);
	}
	
	@PostMapping("/updateroom")
	public boolean updateRoom(Authentication authentication,@Valid  @RequestBody RoomDTO room) {
		String adminEmail = authentication.getPrincipal().toString();
		return adminService.updateRoom(adminEmail, room);
	}
	
	@PostMapping("/updatefood")
	public boolean updateFood(Authentication authentication,@Valid  @RequestBody FoodDTO food) {
		String adminEmail = authentication.getPrincipal().toString();
		return adminService.updateFood(adminEmail, food);
	}

}
