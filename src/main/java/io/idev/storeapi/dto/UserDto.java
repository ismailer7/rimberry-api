package io.idev.storeapi.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDto {

	@JsonProperty("id")
	private int id;

	@JsonProperty("email")
	private String email;

	@JsonProperty("passowrd")
	private String password;

	@JsonProperty("api_token")
	private String token;

	@JsonProperty("isActive")
	private boolean isActive;

	@JsonProperty("isDeleted")
	private boolean isDeleted;

	@JsonProperty("updated")
	private Date updated;

	@JsonProperty("created")
	private Date created;

	@JsonProperty("roles")
	private String roles;

}
