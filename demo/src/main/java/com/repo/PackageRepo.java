package com.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.Package;
import com.entity.model.keys.PackageID;
@Repository
public interface PackageRepo extends JpaRepository<Package, PackageID>{
	@Query(
			value="select * from package as f where f.name=:packageName",
			nativeQuery = true)
	Optional<Package> findPackageByName(@Param("packageName") String packageName);
	
	@Query(
			value="select * from package as f where f.name=:packageName and f.admin_name=:adminName",
			nativeQuery = true)
	Optional<Package> findPackageByNameAndAdminName(@Param("packageName") String packageName, @Param("adminName") String adminEmail);
	
	@Query(
			value="select * from package as f where f.admin_name=:adminName",
			nativeQuery = true)
	Optional<List<Package>> findPackageByAdmin(@Param("adminName") String adminEmail);
}
