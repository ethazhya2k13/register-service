package com.register.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.register.dto.RolesDto;
import com.register.entity.Roles;
import com.register.repository.RolesRepository;
import com.register.service.RolesService;

@Service
public class RolesServiceImplementation implements RolesService {

	@Autowired
	private RolesRepository rolesRepo;

	@Override
	public Roles saveRoles(RolesDto rolesDto) {
		Roles newRole = new Roles();
		newRole.setRoleName(rolesDto.getRoleName());
		newRole.setRoleDescription(rolesDto.getRoleDescription());
		return rolesRepo.save(newRole);
	}

	@Override
	public List<Roles> roles() {
		return rolesRepo.findAll();
	}

	@Override
	public Roles getOne(int roleId) {
		return rolesRepo.getOne(roleId);
	}

	@Override
	public ResponseEntity<Object> deleteRole(int roleId) {
		if (rolesRepo.findById(roleId).isPresent()) {
			if (rolesRepo.getOne(roleId).getUsers().size() == 0) {
				rolesRepo.deleteById(roleId);
				if (rolesRepo.findById(roleId).isPresent()) {
					return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
				} else
					return ResponseEntity.ok().body("Successfully deleted specified record");
			} else
				return ResponseEntity.unprocessableEntity()
						.body("Failed to delete, please delete the users associated with this role");
		} else
			return ResponseEntity.unprocessableEntity().body("No records found");
	}

	public ResponseEntity<Object> updateRole(int roleId, RolesDto rolesDto) {
		if (rolesRepo.findById(roleId).isPresent()) {
			Roles newRole = rolesRepo.findById(roleId).get();
			newRole.setRoleName(rolesDto.getRoleName());
			newRole.setRoleDescription(rolesDto.getRoleDescription());
			Roles savedRole = rolesRepo.save(newRole);
			if (rolesRepo.findById(savedRole.getRoleId()).isPresent())
				return ResponseEntity.accepted().body("Role Updated successfully");
			else
				return ResponseEntity.badRequest().body("Failed to update Role");

		} else
			return ResponseEntity.unprocessableEntity().body("Specified Role not found");
	}

	@Override
	public long totalRoles() {
		long count = rolesRepo.count();
		return count;
	}

}
