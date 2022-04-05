package com.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.UserFood;
import com.entity.model.keys.UserFoodID;
@Repository
public interface UserFoodRepo extends JpaRepository<UserFood, UserFoodID>{
	@Query(
			value="select * from select_food as f where f.select_package_name=:package",
			nativeQuery = true)
	Optional<UserFood> findFoodByPackage(@Param("package") String packageName);
	
	@Query(
			value="Select * from select_food as f join select_package as p on f.id=p.select_food_id where p.active=true and f.user_email=:email",
			nativeQuery = true)
	Optional<UserFood> findByActivePackageForConfirmation(@Param("email") String email);
}
