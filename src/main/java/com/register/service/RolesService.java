package com.register.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.register.dto.RolesDto;
import com.register.entity.Roles;

public interface RolesService {

	public Roles getOne(int roleId);

	public Roles saveRoles(RolesDto rolesDto);

	public List<Roles> roles();

	ResponseEntity<Object> deleteRole(int roleId);

	ResponseEntity<Object> updateRole(int roleId, RolesDto rolesDto);

	long totalRoles();
}
