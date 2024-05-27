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
	
	@Column(name = "DRIVER")
	private String driver;
	
	@Column(name = "PB")
	private int pb;

	@Column(name = "TP")
	private int tp;

	@Column(name = "PP")
	private int pp;

	@Column(name = "TC")
	private int tc;
	
	@Column(name = "TARE")
	private int tare;
	
	@Column(name = "TAREP")
	private int tarep;

	@Column(name = "TN")
	private int tn;

	
}
