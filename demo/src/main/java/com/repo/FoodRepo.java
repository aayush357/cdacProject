package com.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.Food;
import com.entity.model.keys.FoodID;
@Repository
public interface FoodRepo extends JpaRepository<Food, FoodID>{
	
	
	@Query(
			value="select * from Food as f where f.admin_name=:adminName",
			nativeQuery = true)
	Optional<List<Food>> findFoodByAdmin(@Param("adminName") String adminEmail);
	
	@Query(
			value="select * from Food as f where f.name=:foodName and f.admin_name=:adminName",
			nativeQuery = true)
	Optional<Food> findFoodByName(@Param("foodName") String foodName, @Param("adminName") String adminName);
	
	@Query(
			value="select * from Food as f where f.admin_name=:admin",
			nativeQuery = true)
	Optional<List<Food>> findFoodByAdminName(@Param("admin") String adminName);
	

	@Query(
			value="select * from Food as f where f.admin_name=:admin and f.name=:foodName",
			nativeQuery = true)
	Optional<Food> findFoodByAdminNameandFoodName(@Param("admin") String adminName, @Param("foodName") String foodName);
}
