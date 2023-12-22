package io.idev.rimberry.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "RIMBERRY_USER")
@Data
public class User {
	
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Column(name = "FIRSTNAME")
	private String firstName;
	
	@Column(name = "LASTNAME")
	private String lastName;
	
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(name = "USER_EMAIL")
	private String email;

	@Column(name = "USER_PASS")
	private String password;

	@Column(name = "TOKEN")
	private String token;

	@Column(name = "ACTIVE")
	private Boolean isActive;

	@Column(name = "DELETED")
	private Boolean isDeleted;

	@Column(name = "UPDATED")
	private Date updated;

	@Column(name = "CREATED")
	private Date created;

	@Column(name = "ROLES")
	private String roles;
	
	@Column(name = "GENDER")
	private String gender;
	
    @Column(name = "AVATAR", length = 1000)
    private String avatar;
	
	@Column(name = "LOGGED")
	private Boolean isLogged;
	
}
