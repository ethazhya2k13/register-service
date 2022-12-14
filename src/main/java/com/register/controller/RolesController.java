package com.register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.register.dto.RolesDto;
import com.register.entity.Roles;
import com.register.service.RolesService;

import io.jsonwebtoken.lang.Classes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class RolesController {

	@Autowired
	private RolesService rolesService;

	@ApiOperation(value = "Save Roles", notes = "Create New Roles")
	@PostMapping(value = "/addroles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Roles> addRoles(@RequestBody RolesDto rolesDto) {
		Roles role = rolesService.saveRoles(rolesDto);
		return new ResponseEntity<>(role, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Get Roles", notes = "Get all Roles")
	@GetMapping(value = "/getroles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Roles>> getRoles() {
		return new ResponseEntity<>(rolesService.roles(), HttpStatus.CREATED);
	}

	@DeleteMapping("deleterole/{roleId}")
	public ResponseEntity<Object> deleteRole(@PathVariable int roleId) {
		return rolesService.deleteRole(roleId);
	}

	@PutMapping("updaterole/{roleId}")
	public ResponseEntity<Object> updateRole(@PathVariable int roleId, @RequestBody RolesDto rolesDto) {
		return rolesService.updateRole(roleId, rolesDto);
	}

	@GetMapping("/getRolesCount")
	@ApiOperation(value = "Total Roles present in the Database", response = Classes.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Roles count retrieved Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing resource is forbidden"),
			@ApiResponse(code = 404, message = "Record Not found") })
	public long getCounts() {
		return rolesService.totalRoles();
	}

}
