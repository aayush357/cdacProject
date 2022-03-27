package com.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.Admin;
@Repository
public interface AdminRepo extends JpaRepository<Admin, String>{
	Optional<Admin> findByEmail(String username);
}
