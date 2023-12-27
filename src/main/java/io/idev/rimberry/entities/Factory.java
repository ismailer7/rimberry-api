package io.idev.rimberry.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "FACTORY")
public class Factory {

	@Id
	@Column(name = "FACTORY_ID")
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
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="owner_id")
	private FactoryOwner owner;
}
