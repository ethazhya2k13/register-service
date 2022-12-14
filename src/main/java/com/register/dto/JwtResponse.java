package com.register.dto;

import com.register.entity.Roles;
import com.register.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

	private Users users;
	private String jwtToken;
	private String userName;
	private Long expirationDate;
	private String roleName;
	private Roles roles;
}
