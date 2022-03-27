package com.service.interfaces;

import java.util.List;

import com.enitity.dto.FoodDTO;
import com.enitity.dto.PackageDTO;
import com.enitity.dto.RoomDTO;
import com.entity.model.classes.Admin;
import com.entity.model.classes.Food;
import com.entity.model.classes.Package;
import com.entity.model.classes.Room;

public interface AdminService {

	void addRoomToAdmin(String adminName, String hotelName);

	void addPackageToAdmin(String adminName, String packageName);

	List<Package> getPackages(String adminEmail);

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