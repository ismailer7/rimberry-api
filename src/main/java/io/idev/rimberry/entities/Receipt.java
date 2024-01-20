package io.idev.rimberry.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RECEIPT")
public class Receipt {

	@Id
	@Column(name = "RECEIPT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "DATE")
	private String date;

	@Column(name = "SUPPLIER_ID")
	private int supplierId;

	@Column(name = "PRODUCT_ID")
	private int productId;

	@Column(name = "GROSSMASS1")
	private int grossMass1;

	@Column(name = "TARE")
	private int tare;

	@Column(name = "NATMASS1")
	private int netmass1;

	@Column(name = "NCS")
	private String ncs;

	@Column(name = "TOTAL")
	private int total;

	@Column(name = "GROSSMASS2")
	private int grossMass2;

	@Column(name = "NATMASS2")
	private int netMass2;
}
