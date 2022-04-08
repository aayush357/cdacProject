package com.service.interfaces;

import java.sql.Date;
import java.util.List;

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
import com.entity.model.classes.UserFood;
import com.entity.model.classes.UserRoom;
import com.response.dto.FoodsResponse;
import com.response.dto.PackagesResponse;
import com.response.dto.RoomsResponse;

public interface UserServices {
	
	public boolean getLink(String email);
	public boolean resetPassword(ResetDTO resetData);

	void addUserRoom(UserRoom r);

	List<UserPackageResponseDTO> getUserPackages(String userName);

	public boolean updateUserPackage(String userEmail, UserPackageDTO pckg);

	public List<UserRoomResponseDTO> getUserRooms(String userName);

	public boolean updateUserRoom(String userEmail, UserRoomDTO room);

	public List<UserFoodResponseDTO> getUserFoods(String userName);

	public boolean updateUserFood(String userEmail, UserFoodDTO userFood);

	void addUserFood(UserFood food);

	public boolean saveUser(UserDTO userDto);

	public AppUser getUser(String username);

	public List<AppUser> getUsers();

	public void addRoleToUser(String username, String roleName);

	public void addUserToRole(String roleName, String username);

	void addUserPackagewithAdminandAppUser(UserPackageDTO pckg, String userName, Date date);

	void addRoomToUser(UserRoomDTO room, String userName);

	void addFoodToUser(UserFoodDTO food, String userName);

	boolean addConfirmationToUser(String userName, Double balance);
	public UserConfirmationResponseDTO calculateBill(String userEmail, Double balance);

	public List<PackagesResponse> packages();

	public List<RoomsResponse> rooms();

	public List<FoodsResponse> foods();

}