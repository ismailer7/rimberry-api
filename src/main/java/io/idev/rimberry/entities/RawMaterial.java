package io.idev.rimberry.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "RAW_MATERIAL")
public class RawMaterial {

	@Id
	@Column(name = "RM_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	
}
