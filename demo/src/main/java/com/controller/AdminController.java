package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enitity.dto.AdminDTO;
import com.enitity.dto.FoodDTO;
import com.enitity.dto.PackageDTO;
import com.enitity.dto.RoomDTO;
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
	public boolean addAdmin(@RequestBody AdminDTO adminDTO) {
		Role r = new Role(1L, "ROLE_ADMIN");
		Admin admin = new Admin(adminDTO.getEmail(), adminDTO.getPassword(), adminDTO.getMobNo(), r, null, null, null,
				null);
		adminService.saveAdmin(admin);
		return true;
	}

//	@PostMapping("/delete")
//	public boolean deleteAdmin(@RequestBody String email) {
//		return adminService.delete(email);
//	}

	@PostMapping("/addpackage")
	public boolean addPackage(Authentication authentication, @RequestBody PackageDTO pckg) {
		String adminEmail = authentication.getPrincipal().toString();
		adminService.addPackage(pckg, adminEmail);
		return true;
	}

	@PostMapping("/addroom")
	public boolean addRoom(Authentication authentication, @RequestBody RoomDTO room) {
		String adminEmail = authentication.getPrincipal().toString();
		adminService.addRoom(room, adminEmail);
		return true;
	}

	@PostMapping("/addfood")
	public boolean addFood(Authentication authentication, @RequestBody FoodDTO food) {
		String adminEmail = authentication.getPrincipal().toString();
		adminService.addFood(food, adminEmail);
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
	public Food getFood(Authentication authentication, @RequestParam("foodName") String foodName) {
		String adminEmail = authentication.getPrincipal().toString();
		Food food = adminService.getFood(foodName, adminEmail);
		if (food == null) {
			throw new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return food;
	}

	@PostMapping("/package")
	public Package getPackage(Authentication authentication, @RequestParam("packageName") String packageName) {
		String adminEmail = authentication.getPrincipal().toString();
		Package pckg = adminService.getPackage(packageName, adminEmail);
		if (pckg == null) {
			throw new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return pckg;
	}

	@PostMapping("/room")
	public Room getRoom(Authentication authentication, @RequestParam("hotelName") String hotelName) {
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
	
	@PostMapping("/updatepackage")
	public boolean updatePackage(Authentication authentication, @RequestBody PackageDTO pckg) {
		String adminEmail = authentication.getPrincipal().toString();
		return adminService.updatePackage(adminEmail, pckg);
	}
	
	@PostMapping("/updateroom")
	public boolean updateRoom(Authentication authentication, @RequestBody RoomDTO room) {
		String adminEmail = authentication.getPrincipal().toString();
		return adminService.updateRoom(adminEmail, room);
	}
	
	@PostMapping("/updatefood")
	public boolean updateFood(Authentication authentication, @RequestBody FoodDTO food) {
		String adminEmail = authentication.getPrincipal().toString();
		return adminService.updateFood(adminEmail, food);
	}

}
