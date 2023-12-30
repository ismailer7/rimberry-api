package io.idev.storapi.enums;

public enum FactoryStatus {

	INACTIVE(0), OPEN(1), CLOSED(2);

	private int status;

	private FactoryStatus(int status) {
		this.status = status;
	}
}
