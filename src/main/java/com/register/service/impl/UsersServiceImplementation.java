package com.register.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.register.dto.UsersDto;
import com.register.entity.Roles;
import com.register.entity.Users;
import com.register.repository.RolesRepository;
import com.register.repository.UsersRepository;
import com.register.service.UsersService;

@Service
public class UsersServiceImplementation implements UsersService {
	@Autowired
	private UsersRepository usersRepo;

	@Autowired
	private RolesRepository rolesRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void initRoleAndUser() {

		Roles adminRole = new Roles();
		adminRole.setRoleId(1);
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin role");
		rolesRepo.save(adminRole);

		Roles userRole = new Roles();
		userRole.setRoleId(2);
		userRole.setRoleName("User");
		userRole.setRoleDescription("Default role for newly created record");
		rolesRepo.save(userRole);

		Users adminUser = new Users();
		adminUser.setUserId(1);
		adminUser.setUserName("admin123");
		adminUser.setFirstName("admin");
		adminUser.setLastName("admin");
		adminUser.setUserPassword(getEncodedPassword("admin@pass"));
		adminUser.setUserEmail("admin@gmail.com");
		adminUser.setUserGender("Male");
		adminUser.setUpdatedAt(Instant.now());
		adminUser.setCreated(Instant.now());
		adminUser.setRole(adminRole);
		Set<Roles> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRoles(adminRoles);
		usersRepo.save(adminUser);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> registerUser(UsersDto usersDto) {
		Users newUser = new Users();
		if (usersRepo.findByUserName(usersDto.getUserName()).isPresent()) {
			return ResponseEntity.badRequest().body("User name is already present, failed to create new user");
		} else {

			newUser.setUserName(usersDto.getUserName());
			newUser.setFirstName(usersDto.getFirstName());
			newUser.setLastName(usersDto.getLastName());
			newUser.setUserEmail(usersDto.getUserEmail());
			newUser.setUserPassword(getEncodedPassword(usersDto.getUserPassword()));
			newUser.setUserGender(usersDto.getUserGender());
			newUser.setCreated(Instant.now());
			newUser.setUpdatedAt(Instant.now());
			Roles roles = rolesRepo.findByRoleName("User").get();
			newUser.setRole(roles);
			Set<Roles> userRoles = new HashSet<>();
			userRoles.add(roles);
			newUser.setRoles(new HashSet<>(userRoles));
			Users savedUser = usersRepo.save(newUser);

			if (usersRepo.findById(savedUser.getUserId()).isPresent())
				return ResponseEntity.ok("User created successfully");
			else
				return ResponseEntity.unprocessableEntity().body("Failed Creating User as Specified");
		}

	}

	@Override
	public Users getById(int id) {
		return usersRepo.findById(id).get();
	}

	@Override
	@Transactional
	public ResponseEntity<Object> updateUser(int roleId, UsersDto usersDto) {
		if (usersRepo.findById(roleId).isPresent()) {
			Users user = usersRepo.findById(roleId).get();
			user.setFirstName(usersDto.getFirstName());
			user.setLastName(usersDto.getLastName());
			user.setUserEmail(usersDto.getUserEmail());
			user.setUserPassword(getEncodedPassword(usersDto.getUserPassword()));
			user.setUserGender(usersDto.getUserGender());
			user.setUpdatedAt(usersDto.getUpdatedAt());

			List<Roles> roleProxies = new ArrayList<>();
			int id = usersDto.getRoleId();

			Roles tempRole = rolesRepo.getOne(id);
			roleProxies.add(tempRole);
			user.setRole(tempRole);
			user.setRoles(new HashSet<>(roleProxies));
			Users updatedUser = usersRepo.save(user);
			if (usersRepo.findById(updatedUser.getUserId()).isPresent())
				return ResponseEntity.accepted().body("User updated successfully");
			else
				return ResponseEntity.unprocessableEntity().body("Failed to update the user");
		} else
			return ResponseEntity.unprocessableEntity().body("Cannot find the specified user");
	}

	@Override
	public ResponseEntity<Object> deleteUser(int id) {
		if (usersRepo.findById(id).isPresent()) {
			usersRepo.deleteById(id);
			if (usersRepo.findById(id).isPresent())
				return ResponseEntity.unprocessableEntity().body("Failed to delete user");
			else
				return ResponseEntity.ok().body("Successfully deleted the specified user");
		} else
			return ResponseEntity.badRequest().body("Cannot find the specified user");
	}

	public String getEncodedPassword(String userPassword) {
		return passwordEncoder.encode(userPassword);
	}

	@Override
	public List<Users> getAll() {
		List<Users> usersList = usersRepo.findAll();
		return usersList;
	}

	@Override
	public void deleteAll() {
		usersRepo.deleteAll();

	}

	@Override
	public List<UsersDto> getAllUsersDto() {
		return usersRepo.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	@Override
	public UsersDto convertEntityToDto(Users users) {
		UsersDto usersDto = new UsersDto();
		usersDto.setUserId(users.getUserId());
		usersDto.setFirstName(users.getFirstName());
		usersDto.setLastName(users.getLastName());
		usersDto.setUserName(users.getUserName());
		usersDto.setUserEmail(users.getUserEmail());
		usersDto.setUserPassword(getEncodedPassword(users.getUserPassword()));
		usersDto.setUserGender(users.getUserGender());
		usersDto.setUpdatedAt(users.getUpdatedAt());
		usersDto.setCreated(users.getCreated());
		usersDto.setRoleId(users.getRole().getRoleId());
		usersDto.setRoleName(users.getRole().getRoleName());
		usersDto.setRoleDescription(users.getRole().getRoleDescription());
		return usersDto;
	}

	@Override
	public long getUsersCountByRole(String roleName) {
		long usersList = usersRepo.findAll().stream()
				.filter(users -> users.getRole().getRoleName().equalsIgnoreCase(roleName)).count();
		return usersList;
	}

	@Override
	public long totalUsers() {
		long count = usersRepo.count();
		return count;
	}
}
