package com.register.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Roles {

	@Id
	@GeneratedValue
	@Column(name = "role_id")
	private int roleId;

	@Pattern(regexp = "^[A-Za-z0-9 '/&#,.-]*$", message = "The only special characters allowed are: +@'/&#,.-")
	@NotBlank(message = "Please provide a Role name")
	@Size(max = 50, message = "Please enter a Role name between 1 and 50 characters")
	@Column(name = "role_name")
	private String roleName;

	@Pattern(regexp = "^[A-Za-z0-9 '/&#,.-]*$", message = "The only special characters allowed are: +@'/&#,.-")
	@NotBlank(message = "Please provide a Role Description")
	@Size(max = 50, message = "Please enter a Role Description between 1 and 50 characters")
	@Column(name = "role_description")
	private String roleDescription;

	@ManyToMany(mappedBy = "roles")
	@JsonBackReference
	private Set<Users> users = new HashSet<Users>();

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public Set<Users> getUsers() {
		return users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}

}
