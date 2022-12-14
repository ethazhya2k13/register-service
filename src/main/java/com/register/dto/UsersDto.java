package com.register.dto;

import java.time.Instant;
//help hiding implementation details of domain objects (aka. entities). Exposing entities through endpoints can become a security issue if we do not carefully handle what properties can be changed through what operations.

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

	public Integer userId;
	public String firstName;
	public String lastName;
	public String userName;
	public String userPassword;
	public String userEmail;
	public String userGender;
	public Instant created;
	public Instant updatedAt;
	public Integer roleId;
	public String roleName;
	public String roleDescription;
}
