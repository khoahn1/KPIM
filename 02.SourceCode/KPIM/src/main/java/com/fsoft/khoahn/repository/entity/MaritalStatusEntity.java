package com.fsoft.khoahn.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "marital_status")
public class MaritalStatusEntity extends Auditable<String> {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Integer id;

	@Column(name = "marital_status_name")
	private String maritalStatusName;

	@Column(name = "description")
	private String description;

}