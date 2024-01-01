package io.idev.rimberry.entities;

import java.util.Date;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SUPPLIER")
public class Supplier {

	@Id
	@Column(name = "SUPPLIER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Column(name = "SUPPLIER_NAME")
	private String name;
	
	@Column(name = "SUPPLIER_ADDRESS")
	private String address;
	
	@Column(name = "SUPPLIER_EMAIL")
	private String email;
	
	@Column(name = "SUPPLIER_PHONE")
	private String phone;
	
	@Column(name = "SUPPLIER_CIN")
	private String cin;
	
	@Column(name = "SUPPLIER_RIB")
	private String rib;
	
	@Column(name = "SUPPLIER_SOURCE")
	private String source;
	
	@Column(name = "CREATED")
	private Date created;
	
	@Column(name = "UPDATED")
	private Date updated;
	
	@Column(name = "ISDELETED")
	private boolean isDeleted;
	
}
