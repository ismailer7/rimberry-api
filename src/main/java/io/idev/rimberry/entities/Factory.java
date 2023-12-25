package io.idev.rimberry.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "FACTORY")
public class Factory {

	@Id
	@Column(name = "PRODUCT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "FACTORY_NAME")
	private String name;
	
	@Column(name = "FACTORY_LOCATION")
	private String location;
	
	@Column(name = "CREATED")
	private Date created;
	
	@Column(name = "UPDATED")
	private Date updated;
	
	@Column(name = "DELETED")
	private boolean isDeleted;
}
