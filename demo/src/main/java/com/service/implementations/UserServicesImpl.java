package com.service.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.dto.AppUserDTO;
import com.entity.dto.ResetDTO;
import com.entity.dto.UserConfirmationResponseDTO;
import com.entity.dto.UserDTO;
import com.entity.dto.UserFoodDTO;
import com.entity.dto.UserFoodResponseDTO;
import com.entity.dto.UserPackageDTO;
import com.entity.dto.UserPackageResponseDTO;
import com.entity.dto.UserRoomDTO;
import com.entity.dto.UserRoomResponseDTO;
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
import com.exception.EntityAlreadyPresentException;
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

	public boolean updateUser(String userEmail, AppUserDTO userDTO) {
		AppUser user = appUserRepo.findByEmail(userEmail).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.UNAUTHORIZED));
		user.setAddress(userDTO.getAddress());
		user.setGender(userDTO.getGender());
		user.setFirstname(userDTO.getFirstname());
		user.setLastname(userDTO.getLastname());
		user.setMobile(userDTO.getMobile());
		appUserRepo.save(user);
		return true;
	}

	public boolean resetPassword(ResetDTO resetData) {
		AppUser user = appUserRepo.findByEmail(resetData.getEmail()).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.UNAUTHORIZED));
		user.setPassword(passwordEncoder.encode(resetData.getPassword()));
		appUserRepo.save(user);
		return true;
	}

	public boolean getLink(String email) {
		AppUser user = appUserRepo.findByEmail(email).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.UNAUTHORIZED));
		String subject = "Password Reset Link";
		String body = "Link for Password Reset is: http://localhost:3000/userReset";
		MailService.sendFromGMail(email, subject, body);
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

	public boolean deletePackage(String userName) {
		UserPackage pckg = userPackageRepo.findByActivePackageForConfirmation(userName)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		userPackageRepo.delete(pckg);
		return true;
	}

	public boolean deleteFood(String userName) {
		UserPackage pckg = userPackageRepo.findByActivePackageForConfirmation(userName)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		UserFood food = pckg.getUserFood();
		pckg.setUserFood(null);
		userFoodRepo.delete(food);
		return true;
	}

	public boolean deleteRoom(String userName) {
		UserPackage pckg = userPackageRepo.findByActivePackageForConfirmation(userName)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		UserRoom room = pckg.getUserRoom();
		pckg.setUserRoom(null);
		userRoomRepo.delete(room);
		return true;
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser user = appUserRepo.findByEmail(username).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Role role = roleRepo.findByName(roleName).orElseThrow(
				() -> new EntityNotFoundException(LoginEnum.ROLENOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		user.setRole(role);
	}

	public List<UserPackageResponseDTO> getUserPackages(String userName) {
		List<UserPackage> pckgs = userPackageRepo.findByUserEmail(userName)
				.orElseThrow(() -> new EntityNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		List<UserPackageResponseDTO> pckgsResponse = new ArrayList<>();
		for (UserPackage p : pckgs) {
			Optional<Package> optPckg = packageRepo.findPackageByName(p.getName());
			if (optPckg.isPresent() == true) {
//					.orElseThrow(() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(),
//							HttpStatus.INTERNAL_SERVER_ERROR));
				UserPackageResponseDTO pr = new UserPackageResponseDTO(p.getName(), p.getPlace(),
						p.getNumberOfPersons(), p.getDate(), p.isActive(), optPckg.get().getPrice(),
						p.getUserAdminName().getEmail());
				pckgsResponse.add(pr);
			} else {
				UserPackageResponseDTO pr = new UserPackageResponseDTO(p.getName(), p.getPlace(),
						p.getNumberOfPersons(), p.getDate(), p.isActive(), p.getConfirmationPackage().getPackageCost(),
						p.getUserAdminName().getEmail());
				pckgsResponse.add(pr);
			}
		}
		return pckgsResponse;
	}

	public boolean updateUserPackage(String userEmail, UserPackageDTO pckg) {
		UserPackage userPackage = userPackageRepo.findByActivePackageForConfirmation(userEmail)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		userPackage.setNumberOfPersons(pckg.getNumberOfPersons());
		userPackageRepo.save(userPackage);
		return true;
	}

	public List<UserRoomResponseDTO> getUserRooms(String userName) {
		List<UserRoom> rooms = userRoomRepo.findUserRoomByUserEmail(userName)
				.orElseThrow(() -> new EntityNotFoundException(UserEnum.ROOMNOTSELECTED.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		List<UserRoomResponseDTO> pckgsResponse = new ArrayList<>();
		for (UserRoom r : rooms) {
			UserRoomResponseDTO pr = new UserRoomResponseDTO(r.getHotelName(), r.getType(), r.getSize(),
					r.getUserPackage().getUserAdminName().getEmail(), r.getUserPackage().isActive());
			pckgsResponse.add(pr);
		}
		return pckgsResponse;
	}

	public boolean updateUserRoom(String userEmail, UserRoomDTO room) {
		UserRoom userRoom = userRoomRepo.findByActivePackageForConfirmation(userEmail)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		userRoom.setSize(room.getSize());
		userRoom.setType(room.getType());
		userRoomRepo.save(userRoom);
		return true;
	}

	public List<UserFoodResponseDTO> getUserFoods(String userName) {
		List<UserFood> foods = userFoodRepo.findFoodByUserEmail(userName)
				.orElseThrow(() -> new EntityNotFoundException(UserEnum.FOODNOTSELECTED.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		List<UserFoodResponseDTO> foodsResponse = new ArrayList<>();
		for (UserFood f : foods) {
			UserFoodResponseDTO pr = new UserFoodResponseDTO(f.getName(), f.getType(), f.getQuantity(),
					f.getUserPackage().getUserAdminName().getEmail(), f.getUserPackage().isActive());
			foodsResponse.add(pr);
		}
		return foodsResponse;
	}

	public boolean updateUserFood(String userEmail, UserFoodDTO userFood) {
		UserFood food = userFoodRepo.findByActivePackageForConfirmation(userEmail)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		food.setQuantity(userFood.getQuantity());
		userFoodRepo.save(food);
		return true;
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
		Optional<UserPackage> userPckg = userPackageRepo.findByActivePackageForConfirmation(userEmail);
		if (userPckg.isEmpty()) {
			AppUser appUser = appUserRepo.findByEmail(userEmail)
					.orElseThrow(() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(),
							HttpStatus.INTERNAL_SERVER_ERROR));
			Package optPckg = packageRepo.findPackageByNameAndAdminName(pckg.getName(), pckg.getAdminEmail())
					.orElseThrow(() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(),
							HttpStatus.INTERNAL_SERVER_ERROR));
			Package pckgOriginal = optPckg;
			Admin admin = adminRepo.findByEmail(pckg.getAdminEmail())
					.orElseThrow(() -> new EntityNotFoundException(AdminEnum.ADMINNOTFOUND.toString(),
							HttpStatus.INTERNAL_SERVER_ERROR));
			UserPackage p = new UserPackage(pckgOriginal.getPackageName(), pckgOriginal.getPlace(), date,
					pckg.getNumberOfPersons(), true, appUser, pckgOriginal.getAdminName(), null, null, null);
			userPackageRepo.save(p);
			appUser.getUserPackage().add(p);
			admin.getUserPackages().add(p);
		} else {
			throw new EntityAlreadyPresentException(UserEnum.ACTIVEPACKAGEALREADYPRESENT.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void addRoomToUser(UserRoomDTO room, String userEmail) {
		AppUser user = appUserRepo.findByEmail(userEmail).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Room optRoom = roomRepo.findRoomByAdminNameandHotelName(room.getAdminEmail(), room.getHotelName()).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Room roomOri = optRoom;
		UserPackage userPackage = userPackageRepo.findByActivePackageForRoom(userEmail, room.getAdminEmail())
				.orElseThrow(() -> new EntityNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));

		if (userPackage != null && userPackage.getUserRoom() == null) {
			UserRoom userRoom = new UserRoom(roomOri.getHotelName(), roomOri.getType(), roomOri.getSize(), user);
			userPackage.setUserRoom(userRoom);
			user.getUserRoom().add(userRoom);
//			userPackage.setUserRoom(userRoom);
		} else {
			if (userPackage == null) {
				System.out.println("please select a package first");
				throw new ActivePackageNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			} else if( userPackage.getUserRoom() != null) {
				throw new EntityAlreadyPresentException(UserEnum.ROOMALREADYSELECTED.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	public void addFoodToUser(UserFoodDTO food, String userEmail) {
		AppUser user = appUserRepo.findByEmail(userEmail).orElseThrow(
				() -> new EntityNotFoundException(UserEnum.USERNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Food optFood = foodRepo.findFoodByName(food.getName()).orElseThrow(
				() -> new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
		Food foodOri = optFood;
		UserPackage userPackage = userPackageRepo.findByActivePackageForFood(userEmail)
				.orElseThrow(() -> new EntityNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		if (userPackage != null && userPackage.getUserFood() == null) {
			UserFood userFood = new UserFood(foodOri.getName(), foodOri.getType(), food.getQuantity(), user);
			userPackage.setUserFood(userFood);
			user.getUserFood().add(userFood);
		} else {
			if (userPackage == null) {
			System.out.println("please select a package first");
			throw new ActivePackageNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
			} else if( userPackage.getUserFood() != null) {
				throw new EntityAlreadyPresentException(UserEnum.FOODALREADYSELECTED.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	public List<PackagesResponse> packages() {
		List<Package> pckgs = packageRepo.findAll();
		if (pckgs.size() == 0) {
			throw new EntityNotFoundException(AdminEnum.PACKAGESNOTPRESENT.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<PackagesResponse> pckgsResponse = new ArrayList<>();
		for (Package p : pckgs) {
			PackagesResponse pr = new PackagesResponse(p.getPackageName(), p.getPlace(), p.getPrice(), p.getDays(),
					p.getAdminName().getEmail());
			pckgsResponse.add(pr);
		}
		return pckgsResponse;
	}

//	@Override
//	public List<RoomsResponse> rooms() {
//		List<Room> rooms = roomRepo.findAll();
//		List<RoomsResponse> roomsResponse = new ArrayList<>();
//		for (Room r : rooms) {
//			RoomsResponse pr = new RoomsResponse(r.getHotelName(), r.getType(), r.getSize(), r.getPrice(),
//					r.getAdminName().getEmail());
//			roomsResponse.add(pr);
//		}
//		return roomsResponse;
//	}

	@Override
	public List<RoomsResponse> rooms(String userEmail) {
		UserPackage pckg = userPackageRepo.findByActivePackageForConfirmation(userEmail)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		List<Room> rooms = roomRepo.findRoomByAdmin(pckg.getUserAdminName().getEmail())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.ROOMSNOTPRESENT.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		List<RoomsResponse> roomsResponse = new ArrayList<>();
		for (Room r : rooms) {
			RoomsResponse pr = new RoomsResponse(r.getHotelName(), r.getType(), r.getSize(), r.getPrice(),
					r.getAdminName().getEmail());
			roomsResponse.add(pr);
		}
		return roomsResponse;
	}

//	@Override
//	public List<FoodsResponse> foods() {
//		List<Food> foods = foodRepo.findAll();
//		List<FoodsResponse> foodsResponse = new ArrayList<>();
//		for (Food p : foods) {
//			FoodsResponse pr = new FoodsResponse(p.getName(), p.getType(), p.getCost(), p.getAdminName().getEmail());
//			foodsResponse.add(pr);
//		}
//		return foodsResponse;
//	}

	@Override
	public List<FoodsResponse> foods(String userEmail) {
		UserPackage pckg = userPackageRepo.findByActivePackageForConfirmation(userEmail)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
//		List<Food> foods = foodRepo.findAll();
		List<Food> foods = foodRepo.findFoodByAdmin(pckg.getUserAdminName().getEmail())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.FOODSNOTPRESENT.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		List<FoodsResponse> foodsResponse = new ArrayList<>();
		for (Food p : foods) {
			FoodsResponse pr = new FoodsResponse(p.getName(), p.getType(), p.getCost(), p.getAdminName().getEmail());
			foodsResponse.add(pr);
		}
		return foodsResponse;
	}

	public List<UserConfirmationResponseDTO> getUsers(String userEmail) {
		List<UserPackage> userPckgs = userPackageRepo.findByUserEmail(userEmail)
				.orElseThrow(() -> new EntityNotFoundException(UserEnum.PACKAGENOTSELECTED.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		List<UserConfirmationResponseDTO> result = new ArrayList<>();
		userPckgs.forEach(pckg -> {
			if (pckg.isActive() == false) {
				UserConfirmationResponseDTO res = new UserConfirmationResponseDTO(pckg.getConfirmationPackage());
				res.setFoodName(pckg.getUserFood().getName());
				res.setHotelName(pckg.getUserRoom().getHotelName());
				res.setDate(pckg.getDate());
				res.setEmail(pckg.getUser().getEmail());
				res.setFirstName(pckg.getUser().getFirstname());
				result.add(res);
			}
		});
		return result;
	}

	public UserConfirmationResponseDTO calculateBill(String userEmail, Double balance) {
		UserPackage optUserPackage = userPackageRepo.findByActivePackageForConfirmation(userEmail)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		UserPackage p = optUserPackage;
		Package pckg = packageRepo.findPackageByName(p.getName())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		Double pckgCost = pckg.getPrice();
		UserFood userFood = p.getUserFood();
		if (userFood == null) {
			throw new EntityNotFoundException(UserEnum.FOODNOTSELECTED.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		int foodQuantity = userFood.getQuantity();
		Food food = foodRepo.findFoodByAdminNameandFoodName(p.getUserAdminName().getEmail(), userFood.getName())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		Double foodCost = food.getCost();
		Double foodPrice = foodQuantity * foodCost;
		UserRoom userRoom = p.getUserRoom();
		if (userRoom == null) {
			throw new EntityNotFoundException(UserEnum.ROOMNOTSELECTED.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Room room = roomRepo.findRoomByAdminNameandHotelName(p.getUserAdminName().getEmail(), userRoom.getHotelName())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		Double roomCost = room.getPrice();
		Double total = roomCost + foodPrice + pckgCost;
		UserConfirmationResponseDTO response = new UserConfirmationResponseDTO(p.getName(), p.getPlace(), pckgCost,
				roomCost, foodPrice, total, userFood.getName(), userRoom.getHotelName());
		response.setQuantity(foodQuantity);
		response.setFoodPrice(foodCost);
		return response;
	}

	@Override
	public boolean addConfirmationToUser(String userEmail, Double balance) {
		UserPackage optUserPackage = userPackageRepo.findByActivePackageForConfirmation(userEmail)
				.orElseThrow(() -> new ActivePackageNotFoundException(UserEnum.ACTIVEPACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		UserPackage p = optUserPackage;
		Package pckg = packageRepo.findPackageByName(p.getName())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.PACKAGENOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		Double pckgCost = pckg.getPrice();
		UserFood userFood = p.getUserFood();
		if (userFood == null) {
			throw new EntityNotFoundException(UserEnum.FOODNOTSELECTED.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		int foodQuantity = userFood.getQuantity();
		Food food = foodRepo.findFoodByAdminNameandFoodName(p.getUserAdminName().getEmail(), userFood.getName())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.FOODNOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		Double foodCost = food.getCost();
		Double foodPrice = foodQuantity * foodCost;
		UserRoom userRoom = p.getUserRoom();
		if (userRoom == null) {
			throw new EntityNotFoundException(UserEnum.ROOMNOTSELECTED.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Room room = roomRepo.findRoomByAdminNameandHotelName(p.getUserAdminName().getEmail(), userRoom.getHotelName())
				.orElseThrow(() -> new EntityNotFoundException(AdminEnum.ROOMNOTFOUND.toString(),
						HttpStatus.INTERNAL_SERVER_ERROR));
		Double roomCost = room.getPrice();
		Double total = roomCost + foodPrice + pckgCost;
		ConfirmationPackage confirmationPackage = new ConfirmationPackage(p.getName(), p.getPlace(), pckgCost, roomCost,
				foodPrice, total, balance);
		p.setActive(false);
		p.setConfirmationPackage(confirmationPackage);

		String subject = "Confirmation Mail for Your Payment";
		String body1 = "Thank You! Visit Again!";
		String body2 = "Package Name: "+p.getName()+"\t"+"Package Cost: "+pckgCost+"\n";
		String body3 = "Hotel Name: "+userRoom.getHotelName()+"\t"+"Room Cost: "+roomCost+"\n";
		String body4 = "Food Name: "+userFood.getName()+"\t"+"Food Cost: "+foodPrice+"\n";
		String body = "You Have been charged a total sum of: " + total+"\n";
		MailService.sendFromGMail(userEmail, subject, body2+body3+body4+body+body1);
		return true;
	}
}
