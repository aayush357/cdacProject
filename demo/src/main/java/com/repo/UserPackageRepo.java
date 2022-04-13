package com.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.UserPackage;
import com.entity.model.keys.UserPackageID;
@Repository
public interface UserPackageRepo extends JpaRepository<UserPackage, UserPackageID>{
	@Query(
			value="Select * from select_package where user_email=:email and admin_name=:adminName and active=true",
			nativeQuery = true)
	Optional<UserPackage> findByActivePackageForRoom(@Param("email") String email, @Param("adminName") String adminName);
	
	@Query(
			value="Select * from select_package where user_email=:email and active=true",
			nativeQuery = true)
	Optional<UserPackage> findByActivePackageForConfirmation(@Param("email") String email);
	
	@Query(
			value="Select * from select_package where user_email=:email and active=true",
			nativeQuery = true)
	Optional<UserPackage> findByActivePackageForFood(@Param("email") String email);
	
	@Query(
			value="Select * from select_package where admin_name=:email and package_name=:pckg",
			nativeQuery = true)
	Optional<List<UserPackage>> findByAdminNameAndPackageName(@Param("email") String email, @Param("pckg") String pckgName);
	
	@Query(
			value="Select * from select_package where admin_name=:email and select_food_name=:food",
			nativeQuery = true)
	Optional<List<UserPackage>> findByAdminNameAndFoodName(@Param("email") String email, @Param("food") String foodName);
	
	@Query(
			value="Select * from select_package where admin_name=:email and select_hotel_name=:room",
			nativeQuery = true)
	Optional<List<UserPackage>> findByAdminNameAndRoomName(@Param("email") String email, @Param("room") String roomName);
	
	@Query(
			value="Select * from select_package where admin_name=:email",
			nativeQuery = true)
	Optional<List<UserPackage>> findByAdminName(@Param("email") String email);
	
	@Query(
			value="Select * from select_package where user_email=:email",
			nativeQuery = true)
	Optional<List<UserPackage>> findByUserEmail(@Param("email") String email);
	
}
