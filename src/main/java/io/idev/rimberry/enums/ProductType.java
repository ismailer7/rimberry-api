package io.idev.rimberry.enums;

import lombok.Getter;

@Getter
public enum ProductType {

	FRAAMBOISE(0),
	MURE(1),
	MYRTILLE(2),
	FRAISE(3),
	POIVRON_GREEN(4),
	POIVRON_RED(5),
	POIVRON_YELLOW(6);
	
	private int productType;
	
	private ProductType(int productType) {
		this.productType = productType;
	}
	
}
