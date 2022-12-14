package com.register.controller;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.register.dto.JwtRequest;
import com.register.dto.JwtResponse;
import com.register.dto.UsersDto;
import com.register.entity.Users;
import com.register.service.UsersService;
import com.register.service.impl.JwtService;

import io.jsonwebtoken.lang.Classes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private JwtService jwtService;

	@PostConstruct
	public void initRoleAndUser() {
		usersService.initRoleAndUser();
	}

	@PostMapping({ "/authenticate" })
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		return jwtService.createJwtToken(jwtRequest);
	}

	@ApiOperation(value = "Create new Users", notes = "Create new User")
	@PostMapping(value = "/addUser")
	public ResponseEntity<Object> createNewUser(@RequestBody UsersDto usersDto) {
		return usersService.registerUser(usersDto);
	}

	@ApiOperation(value = "Get Users", notes = "Get Users by id")
	@GetMapping(value = "/userById/{id}")
	public ResponseEntity<Users> getUsers(@PathVariable final Integer id) {

		Users user = usersService.getById(id);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "Update Users", notes = "Update Existing Users")
	@PutMapping(value = "/updateUser/{id}")
	public ResponseEntity<Object> updateUsers(@PathVariable("id") int id, @RequestBody UsersDto usersDto) {
		return usersService.updateUser(id, usersDto);
	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		return usersService.deleteUser(id);
	}

	@GetMapping({ "/admin" })
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin() {
		return "This URL is only accessible to the admin";
	}

	@GetMapping({ "/user" })
	@PreAuthorize("hasRole('User')")
	public String forUser() {
		return "This URL is only accessible to the user";
	}

	@GetMapping("/allUsers")
	public List<Users> getUsers() {
		List<Users> getAllUsers = usersService.getAll();
		return getAllUsers;
	}

	@DeleteMapping("/deleteAllUsers")
	public String deleteUsers() {
		usersService.deleteAll();
		return "Records deleted succesfully";
	}

	@GetMapping("/usersrole")
	public List<UsersDto> getAllUsersRoles() {
		return usersService.getAllUsersDto();
	}

	@GetMapping("/getCountByRole")
	public long getUsersByRole(@RequestParam("userRoles") String roleName) {
		return usersService.getUsersCountByRole(roleName);
	}

	@GetMapping("/getUsersCount")
	@ApiOperation(value = "Total Users present in the Database", response = Classes.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users count retrieved Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing resource is forbidden"),
			@ApiResponse(code = 404, message = "Record Not found") })
	public long getCounts() {
		return usersService.totalUsers();
	}

}