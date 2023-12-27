package io.idev.rimberry.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "FACTORY_OWNER")
public class FactoryOwner {

	@Id
	@Column(name = "OWNER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "FULLNAME")
	private String fullname;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "EMAIL")
	private String email;
	
	@OneToOne(mappedBy = "owner")
	private Factory factory;

}
