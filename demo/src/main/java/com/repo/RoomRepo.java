package com.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.Room;
import com.entity.model.keys.RoomID;

@Repository
public interface RoomRepo extends JpaRepository<Room, RoomID> {
	@Query(
			value="select * from room as f where f.hotel_name=:hotelName and f.admin_name=:adminName",
			nativeQuery = true)
	Optional<Room> findRoomByHotelName(@Param("hotelName") String hotelName, @Param("adminName") String adminName);
	
	@Query(value = "Select * from Room r where r.admin_name=:admin and r.hotel_name=:hotelName", nativeQuery = true)
	Optional<Room> findRoomByAdminName(@Param("admin") String adminName, @Param("hotelName") String hotelName);

	@Query(value = "select * from Room as r where r.admin_name=:admin and r.hotel_name=:hotelName", nativeQuery = true)
	Optional<Room> findRoomByAdminNameandHotelName(@Param("admin") String adminName, @Param("hotelName") String hotelName);
	
	@Query(
			value="select * from Room as f where f.admin_name=:adminName",
			nativeQuery = true)
	Optional<List<Room>> findRoomByAdmin(@Param("adminName") String adminEmail);
}
