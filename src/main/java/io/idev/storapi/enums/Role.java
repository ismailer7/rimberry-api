package io.idev.storapi.enums;

import lombok.Getter;

@Getter
public enum Role {
	
	RECEPTION(0),
	ADMIN(1),
	SUPERVISOR(2),
	HR(3);
	
	
	private int role;
	
	private Role(int role) {
		this.role = role;
	}
	
}
