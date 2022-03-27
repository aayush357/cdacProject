package com.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.ConfirmationPackage;
import com.entity.model.keys.ConfirmationPackageID;
@Repository
public interface ConfirmationPackageRepo extends JpaRepository<ConfirmationPackage, ConfirmationPackageID>{
	@Query(
			value="select * from Confirmation as f where f.packageName=:pckgName",
			nativeQuery = true)
	Optional<ConfirmationPackage> findConfirmationByName(@Param("pckgName") String pckgName);
}

