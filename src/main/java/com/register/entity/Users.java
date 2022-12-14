package com.register.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Pattern(regexp = "^[A-Za-z0-9 '/&#,.-]*$", message = "The only special characters allowed are: +@'/&#,.-")
	@NotBlank(message = "Please provide a User name")
	@Size(max = 50, message = "Please enter a User name between 1 and 50 characters")
	@Column(name = "user_name")
	private String userName;

	@Pattern(regexp = "^[A-Za-z0-9 '/&#,.-]*$", message = "The only special characters allowed are: +@'/&#,.-")
	@NotBlank(message = "Please provide a first name")
	@Size(max = 50, message = "Please enter a first name between 1 and 50 characters")
	@Column(name = "first_name")
	private String firstName;

	@Pattern(regexp = "^[A-Za-z0-9 '/&#,.-]*$", message = "The only special characters allowed are: +@'/&#,.-")
	@NotBlank(message = "Please provide a first name")
	@Size(max = 50, message = "Please enter a first name between 1 and 50 characters")
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "user_password")
	private String userPassword;

	@NotBlank(message = "Please provide an email address")
	@Email(message = "Please provide a valid email address")
	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "user_gender")
	private String userGender;

	@Column(name = "user_created")
	private Instant created;

	@Column(name = "updated_at")
	private Instant updatedAt;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	@JsonManagedReference
	Set<Roles> roles = new HashSet<Roles>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	private Roles role;

}
