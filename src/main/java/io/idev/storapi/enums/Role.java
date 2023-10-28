package io.idev.storapi.enums;

import lombok.Getter;

@Getter
public enum Role {
	
	STAFF(0),
	ADMIN(1),
	SUPERVISOR(2);
	
	private int role;
	
	private Role(int role) {
		this.role = role;
	}
	
}
