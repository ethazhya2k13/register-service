package com.register.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.register.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>, CrudRepository<Users, Integer> {

	Optional<Users> findByUserName(String userName);

	@Query(value = "select * from users", nativeQuery = true)
	List<Users> findAllByUserId();

	Users save(Users newUser);

}
