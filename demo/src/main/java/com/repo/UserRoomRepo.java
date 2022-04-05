package com.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.UserRoom;
import com.entity.model.keys.UserRoomID;
@Repository
public interface UserRoomRepo extends JpaRepository<UserRoom, UserRoomID>{
	@Query(
			value="select * from select_room as f where f.select_package_name=:package",
			nativeQuery = true)
	Optional<UserRoom> findRoomByPackage(@Param("package") String packageName);
	
	@Query(
			value="Select * from select_room as r join select_package as p on r.id=p.select_room_id where p.active=true and r.user_email=:email",
			nativeQuery = true)
	Optional<UserRoom> findByActivePackageForConfirmation(@Param("email") String email);
	
	@Query(
			value="select * from select_room as f where f.hotel_name=:hotelName",
			nativeQuery = true)
	Optional<UserRoom> findUserRoomByName(@Param("hotelName") String hotelName);
}
