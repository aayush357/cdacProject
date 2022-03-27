package com.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.model.classes.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(String name);
}
