package com.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.dto.FoodDTO;
import com.entity.dto.PackageDTO;
import com.entity.dto.ResetDTO;
import com.entity.dto.RoomDTO;
import com.entity.enums.AdminEnum;
import com.entity.enums.DTOEnum;
import com.entity.enums.LoginEnum;
import com.entity.model.classes.Admin;
import com.entity.model.classes.Food;
import com.entity.model.classes.Package;
import com.entity.model.classes.Role;
import com.entity.model.classes.Room;
import com.exception.EntityAlreadyPresentException;
import com.exception.EntityNotFoundException;
import com.exception.EntityNullException;
import com.repo.AdminRepo;
import com.repo.FoodRepo;
import com.repo.PackageRepo;
import com.repo.RoleRepo;
import com.repo.RoomRepo;
import com.service.interfaces.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServicesImpl implements AdminService {
	private final AdminRepo adminRepo;
	private final RoleRepo roleRepo;
	private final RoomRepo roomRepo;
	private final PackageRepo packageRepo;
	private final FoodRepo foodRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Admin saveAdmin(Admin admin) {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		return adminRepo.save(admin);
	}

	public boolean resetPassword(ResetDTO resetData) {
		Admin admin = adminRepo.findByEmail(resetData.getEmail()).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.UNAUTHORIZED));
		admin.setPassword(passwordEncoder.encode(resetData.getPassword()));
		adminRepo.save(admin);
		return true;
	}
	
	public boolean getLink(String email) {
		Admin admin = adminRepo.findByEmail(email).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.UNAUTHORIZED));
		String subject = "Password Reset Link";
		String body = "Link for Password Reset is: http://localhost:3000/adminReset";
		MailService.sendFromGMail(email, subject, body);
		return true;
	}
	
	@Override
	public Admin getAdmin(String username) {
		return adminRepo.findByEmail(username).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@Override
	public void addRoleToAdmin(String adminname, String roleName) {
		Admin admin = adminRepo.findByEmail(adminname).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Role role = roleRepo.findByName(roleName).orElseThrow(
				() -> new EntityNotFoundException(LoginEnum.ROLENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		admin.setRole(role);
	}

	@Override
	public void addAdminToRole(String roleName, String adminname) {
		Role role = roleRepo.findByName(roleName).orElseThrow(
				() -> new EntityNotFoundException(LoginEnum.ROLENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Admin admin = adminRepo.findByEmail(adminname).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		role.getAdmins().add(admin);
	}

	@Override
	public void addRoomToAdmin(String adminName, String hotelName) {
		Admin admin = adminRepo.findByEmail(adminName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Room room = roomRepo.findRoomByAdminNameandHotelName(adminName, hotelName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Room r = room;
		admin.getRooms().add(r);
	}

	@Override
	public void addPackageToAdmin(String adminName, String packageName) {
		Admin admin = adminRepo.findByEmail(adminName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Package pckg = packageRepo.findPackageByName(packageName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		admin.getPackages().add(pckg);
	}

	@Override
	public List<Package> getPackages(String adminEmail) {
		List<Package> packages = packageRepo.findPackageByAdmin(adminEmail).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		return packages;
	}

	@Override
	public List<Food> getFood(String adminEmail) {
		List<Food> foods = foodRepo.findFoodByAdmin(adminEmail).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		return foods;
	}

	@Override
	public List<Room> getRoom(String adminEmail) {
		List<Room> rooms = roomRepo.findRoomByAdmin(adminEmail).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		return rooms;
	}

	@Override
	public Room getRoom(String hotelName, String adminEmail) {
		Room room = roomRepo.findRoomByAdminNameandHotelName(adminEmail, hotelName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		return room;
	}

	@Override
	public Package getPackage(String packageName, String adminEmail) {
		Package pckg = packageRepo.findPackageByNameAndAdminName(packageName, adminEmail).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		return pckg;
	}
	
	public boolean updatePackage(String adminEmail, PackageDTO pckg) {
		Package pkg = packageRepo.findPackageByNameAndAdminName(pckg.getPackageName(), adminEmail).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		pkg.setDays(pckg.getDays());
		pkg.setPrice(pckg.getPrice());
		packageRepo.save(pkg);
		return true;
	}
	
	public boolean updateRoom(String adminEmail, RoomDTO room) {
		Room r = roomRepo.findRoomByAdminNameandHotelName(adminEmail, room.getHotelName()).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		r.setPrice(room.getPrice());
		r.setSize(room.getSize());
		roomRepo.save(r);
		return true;
	}
	
	public boolean updateFood(String adminEmail, FoodDTO food) {
		Food f = foodRepo.findFoodByAdminNameandFoodName(adminEmail, food.getName()).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		f.setCost(food.getCost());
		f.setType(food.getType());
		foodRepo.save(f);
		return true;
	}

	@Override
	public Food getFood(String FoodName, String adminEmail) {
		Food food = foodRepo.findFoodByAdminNameandFoodName(adminEmail, FoodName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		return food;
	}

	@Override
	public void addFoodToAdmin(String adminName, String foodName) {
		Admin admin = adminRepo.findByEmail(adminName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Food food = foodRepo.findFoodByName(foodName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		admin.getFoods().add(food);
	}

	@Override
	public void addPackage(PackageDTO pckg, String adminName) {
		Admin admin = adminRepo.findByEmail(adminName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Optional<Package> p = packageRepo.findPackageByNameAndAdminName(pckg.getPackageName(), adminName);
		try {
			if (p.isEmpty()) {
				Package p2 = new Package(pckg.getPackageName(), pckg.getPlace(), pckg.getPrice(), pckg.getDays(), admin);
				packageRepo.save(p2);
			} else {
				throw new EntityAlreadyPresentException(AdminEnum.PACKAGEALREADYPRESENT.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (IllegalArgumentException exc) {
			throw new EntityNullException(DTOEnum.PACKAGEDTONULL.toString(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public void addRoom(RoomDTO room, String adminName) {
		Admin admin = adminRepo.findByEmail(adminName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Optional<Room> r = roomRepo.findRoomByHotelName(room.getHotelName(), adminName);
		try {
			if (r.isEmpty()) {
				Room r2 = new Room(room.getHotelName(), room.getType(), room.getSize(), room.getPrice(), admin);
				roomRepo.save(r2);
			} else {
				throw new EntityAlreadyPresentException(AdminEnum.ROOMALREADYPRESENT.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (IllegalArgumentException exc) {
			throw new EntityNullException(DTOEnum.ROOMDTONULL.toString(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public void addFood(FoodDTO food, String adminName) {
		Admin admin = adminRepo.findByEmail(adminName).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Optional<Food> f = foodRepo.findFoodByAdminNameandFoodName(adminName, food.getName());
		try {
			if (f.isEmpty()) {
				Food f2 = new Food(food.getName(), food.getType(), food.getCost(), admin);
				foodRepo.save(f2);
			} else {
				throw new EntityAlreadyPresentException(AdminEnum.FOODALREADYPRESENT.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (IllegalArgumentException exc) {
			throw new EntityNullException(DTOEnum.FOODDTONULL.toString(), HttpStatus.BAD_REQUEST);
		}

	}

}
