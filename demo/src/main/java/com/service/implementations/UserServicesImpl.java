package com.service.implementations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enitity.dto.UserDTO;
import com.enitity.dto.UserFoodDTO;
import com.enitity.dto.UserPackageDTO;
import com.enitity.dto.UserRoomDTO;
import com.entity.enums.AdminEnum;
import com.entity.enums.LoginEnum;
import com.entity.enums.UserEnum;
import com.entity.model.classes.Admin;
import com.entity.model.classes.AppUser;
import com.entity.model.classes.ConfirmationPackage;
import com.entity.model.classes.Food;
import com.entity.model.classes.Package;
import com.entity.model.classes.Role;
import com.entity.model.classes.Room;
import com.entity.model.classes.UserFood;
import com.entity.model.classes.UserPackage;
import com.entity.model.classes.UserRoom;
import com.exception.ActivePackageNotFoundException;
import com.exception.EntityNotFoundException;
import com.repo.AdminRepo;
import com.repo.AppUserRepo;
import com.repo.FoodRepo;
import com.repo.PackageRepo;
import com.repo.RoleRepo;
import com.repo.RoomRepo;
import com.repo.UserFoodRepo;
import com.repo.UserPackageRepo;
import com.repo.UserRoomRepo;
import com.response.dto.FoodsResponse;
import com.response.dto.PackagesResponse;
import com.response.dto.RoomsResponse;
import com.service.interfaces.UserServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServicesImpl implements UserServices {
	private final AdminRepo adminRepo;
	private final AppUserRepo appUserRepo;
	private final RoomRepo roomRepo;
	private final PackageRepo packageRepo;
	private final FoodRepo foodRepo;
	private final RoleRepo roleRepo;
	private final PasswordEncoder passwordEncoder;
	private final UserPackageRepo userPackageRepo;
	private final UserFoodRepo userFoodRepo;
	private final UserRoomRepo userRoomRepo;

	@Override
	public boolean saveUser(UserDTO userDto) {
		Role role = new Role(2L, "ROLE_USER");
		AppUser user = new AppUser(userDto.getEmail(), userDto.getAadhar(), userDto.getLastname(),
				userDto.getFirstname(), userDto.getMobile(), userDto.getGender(), new Date(System.currentTimeMillis()),
				userDto.getPassword(), userDto.getAddress(), role, null, null, null);
		log.info("saving user {} to db", user.getFirstname());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		appUserRepo.save(user);
		return true;
	}

	@Override
	public AppUser getUser(String username) {
		return appUserRepo.findByEmail(username).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@Override
	public List<AppUser> getUsers() {
		return appUserRepo.findAll();
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser user = appUserRepo.findByEmail(username).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Role role = roleRepo.findByName(roleName).orElseThrow(
				() -> new EntityNotFoundException(LoginEnum.ROLENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		user.setRole(role);
	}

	@Override
	public void addUserToRole(String roleName, String username) {
		Role role = roleRepo.findByName(roleName).orElseThrow(
				() -> new EntityNotFoundException(LoginEnum.ROLENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		AppUser user = appUserRepo.findByEmail(username).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		role.getAppUsers().add(user);
	}

	@Override
	public void addUserRoom(UserRoom r) {
		userRoomRepo.save(r);
	}

	@Override
	public void addUserFood(UserFood food) {
		userFoodRepo.save(food);
	}

	@Override
	public void addUserPackagewithAdminandAppUser(UserPackageDTO pckg, String userEmail, Date date) {
		AppUser appUser = appUserRepo.findByEmail(userEmail).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Package optPckg = packageRepo.findPackageByNameAndAdminName(pckg.getName(), pckg.getAdminEmail()).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Package pckgOriginal = optPckg;
		Admin admin = adminRepo.findByEmail(pckg.getAdminEmail()).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		UserPackage p = new UserPackage(pckgOriginal.getPackageName(), pckgOriginal.getPlace(), date,
				pckg.getNumberOfPersons(), true, appUser, pckgOriginal.getAdminName(), null, null, null);
		userPackageRepo.save(p);
		appUser.getUserPackage().add(p);
		admin.getUserPackages().add(p);
	}

	@Override
	public void addRoomToUser(UserRoomDTO room, String userEmail){
		AppUser user = appUserRepo.findByEmail(userEmail).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Room optRoom = roomRepo.findRoomByAdminNameandHotelName(room.getAdminEmail(), room.getHotelName())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Room roomOri = optRoom;
		UserPackage userPackage = userPackageRepo.findByActivePackageForRoom(userEmail, room.getAdminEmail())
				.orElseThrow(
						() -> new EntityNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		if (userPackage != null) {
			UserRoom userRoom = new UserRoom(roomOri.getHotelName(), roomOri.getType(), roomOri.getSize(), user);
			userPackage.setUserRoom(userRoom);
			user.getUserRoom().add(userRoom);
//			userPackage.setUserRoom(userRoom);
		} else {
			System.out.println("please select a package first");
			throw new ActivePackageNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Override
	public void addFoodToUser(UserFoodDTO food, String userEmail){
		AppUser user = appUserRepo.findByEmail(userEmail).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Food optFood = foodRepo.findFoodByName(food.getName()).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Food foodOri = optFood;
		UserPackage userPackage = userPackageRepo.findByActivePackageForFood(userEmail).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		if (userPackage != null) {
			UserFood userFood = new UserFood(foodOri.getName(), foodOri.getType(), food.getQuantity(), user);
			userPackage.setUserFood(userFood);
			user.getUserFood().add(userFood);
		} else {
			System.out.println("please select a package first");
			throw new ActivePackageNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Override
	public List<PackagesResponse> packages() {
		List<Package> pckgs = packageRepo.findAll();
		List<PackagesResponse> pckgsResponse = new ArrayList<>();
		for (Package p : pckgs) {
			PackagesResponse pr = new PackagesResponse(p.getPackageName(), p.getPlace(), p.getPrice(), p.getDays(),
					p.getAdminName().getEmail());
			pckgsResponse.add(pr);
		}
		return pckgsResponse;
	}

	@Override
	public List<RoomsResponse> rooms() {
		List<Room> rooms = roomRepo.findAll();
		List<RoomsResponse> roomsResponse = new ArrayList<>();
		for (Room r : rooms) {
			RoomsResponse pr = new RoomsResponse(r.getHotelName(), r.getType(), r.getSize(), r.getPrice(),
					r.getAdminName().getEmail());
			roomsResponse.add(pr);
		}
		return roomsResponse;
	}

	@Override
	public List<FoodsResponse> foods() {
		List<Food> foods = foodRepo.findAll();
		List<FoodsResponse> foodsResponse = new ArrayList<>();
		for (Food p : foods) {
			FoodsResponse pr = new FoodsResponse(p.getName(), p.getType(), p.getCost(), p.getAdminName().getEmail());
			foodsResponse.add(pr);
		}
		return foodsResponse;
	}

	@Override
	public void addConfirmationToUser(String userEmail, Double balance) {
		UserPackage optUserPackage = userPackageRepo.findByActivePackageForConfirmation(userEmail).orElseThrow(
				() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.NOT_ACCEPTABLE));
		UserPackage p = optUserPackage;
		Package pckg = packageRepo.findPackageByName(p.getName()).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Double pckgCost = pckg.getPrice();
		UserFood userFood = p.getUserFood();
		int foodQuantity = userFood.getQuantity();
		Food food = foodRepo.findFoodByAdminNameandFoodName(p.getUserAdminName().getEmail(), userFood.getName())
				.orElseThrow(
						() -> new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Double foodCost = food.getCost();
		Double foodPrice = foodQuantity * foodCost;
		UserRoom userRoom = p.getUserRoom();
		Room room = roomRepo.findRoomByAdminNameandHotelName(p.getUserAdminName().getEmail(), userRoom.getHotelName())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Double roomCost = room.getPrice();
		Double total = roomCost + foodPrice + pckgCost;
		ConfirmationPackage confirmationPackage = new ConfirmationPackage(p.getName(), p.getPlace(), pckgCost, roomCost,
				foodPrice, total, balance);
		p.setActive(false);
		p.setConfirmationPackage(confirmationPackage);
		String subject = "Confirmation Mail for Your Payment";
		String body = "You Have been charged a total sum of: "+total;
		MailService.sendFromGMail(userEmail, subject, body);
	}
}