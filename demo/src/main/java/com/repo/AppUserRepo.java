package com.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.AppUser;
import com.entity.model.keys.AppUserID;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, AppUserID>{
	Optional<AppUser> findByEmail(String username);
	
	@Query(
			value="Select u from all_users u where u.aadharcard=:aadhar",
			nativeQuery = true)
	Optional<AppUser> findByAadharcard(@Param("aadhar") String aadhar);
	
}
