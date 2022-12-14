package com.register.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.register.entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer>, CrudRepository<Roles, Integer> {

	Optional<Roles> findByRoleName(String roleName);

}
