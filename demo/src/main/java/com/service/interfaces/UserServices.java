package com.service.interfaces;

import java.sql.Date;
import java.util.List;

import com.enitity.dto.UserConfirmationResponseDTO;
import com.enitity.dto.UserDTO;
import com.enitity.dto.UserFoodDTO;
import com.enitity.dto.UserFoodResponseDTO;
import com.enitity.dto.UserPackageDTO;
import com.enitity.dto.UserPackageResponseDTO;
import com.enitity.dto.UserRoomDTO;
import com.enitity.dto.UserRoomResponseDTO;
import com.entity.model.classes.AppUser;
import com.entity.model.classes.UserFood;
import com.entity.model.classes.UserRoom;
import com.response.dto.FoodsResponse;
import com.response.dto.PackagesResponse;
import com.response.dto.RoomsResponse;

public interface UserServices {

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