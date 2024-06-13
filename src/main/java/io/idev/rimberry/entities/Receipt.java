package io.idev.rimberry.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	@Column(name = "pb")
	private int pb;

	@Column(name = "tp")
	private int tp;

	@Column(name = "pp")
	private int pp;
	
	@Column(name = "te")
	private int te;
	
	@Column(name = "total_to")
	private int totalTo;
	
	@Column(name = "tb")
	private int tb;
	
	@Column(name = "tc")
	private int tc;
	
	@Column(name = "tarep")
	private int tarep;

	@Column(name = "tare")
	private int tare;

	@Column(name = "tn")
	private int tn;

	  
}
