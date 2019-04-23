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
@Table(name = "phases")
public class PhaseEntity extends Auditable<String> {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Integer id;

	@Column(name = "phase_code")
	private String phaseCode;

	@Column(name = "phase_name")
	private String phaseName;

	@Column(name = "description")
	private String description;

}