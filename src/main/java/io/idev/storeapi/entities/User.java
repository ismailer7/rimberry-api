package io.idev.storeapi.entities;

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
@Table(name = "STORE_USER")
@Data
public class User {
	
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

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
	
	@Lob
	@Column(name = "ICON")
	private String icon;
	
	@Column(name = "LOGGED")
	private boolean isLogged;
	
}
