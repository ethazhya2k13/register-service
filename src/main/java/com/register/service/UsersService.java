package com.register.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;

import com.register.dto.UsersDto;
import com.register.entity.Users;

public interface UsersService {

	public ResponseEntity<Object> registerUser(UsersDto usersDto);

	public Users getById(int id);

	public ResponseEntity<Object> updateUser(int roleId, UsersDto usersDto);

	public ResponseEntity<Object> deleteUser(int id);

	public List<Users> getAll();//

	public void deleteAll();

	public void initRoleAndUser();

	public List<UsersDto> getAllUsersDto();

	public long getUsersCountByRole(String roleName);

	public UsersDto convertEntityToDto(Users users);

	long totalUsers();

}
