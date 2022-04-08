package com.service.interfaces;

import java.util.List;

import com.entity.dto.FoodDTO;
import com.entity.dto.PackageDTO;
import com.entity.dto.ResetDTO;
import com.entity.dto.RoomDTO;
import com.entity.model.classes.Admin;
import com.entity.model.classes.Food;
import com.entity.model.classes.Package;
import com.entity.model.classes.Room;

public interface AdminService {

	public boolean getLink(String email);
	public boolean resetPassword(ResetDTO resetData);
	void addRoomToAdmin(String adminName, String hotelName);

	void addPackageToAdmin(String adminName, String packageName);

	List<Package> getPackages(String adminEmail);
	
	public boolean updatePackage(String adminEmail, PackageDTO pckg);
	public boolean updateFood(String adminEmail, FoodDTO food);
	public boolean updateRoom(String adminEmail, RoomDTO room);

	List<Food> getFood(String adminEmail);

	List<Room> getRoom(String adminEmail);

	public Package getPackage(String packageName, String adminEmail);

	public Room getRoom(String hotelName, String adminEmail);

	public Food getFood(String FoodName, String adminEmail);

	void addFoodToAdmin(String adminName, String foodName);

	public Admin saveAdmin(Admin admin);

	public Admin getAdmin(String username);

	public void addRoleToAdmin(String adminname, String roleName);

	public void addAdminToRole(String roleName, String adminname);

	void addPackage(PackageDTO pckg, String adminName);

	void addRoom(RoomDTO room, String adminName);

	void addFood(FoodDTO food, String adminName);

}