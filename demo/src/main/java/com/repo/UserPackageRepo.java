package com.repo;

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
}
