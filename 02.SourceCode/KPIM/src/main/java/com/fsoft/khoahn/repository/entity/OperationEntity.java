package com.fsoft.khoahn.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fsoft.khoahn.common.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "operations")
public class OperationEntity extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", parameters = @Parameter(name = Constants.PREFIX_PARAM, value = Constants.OPERATIONS_KEY), strategy = "com.fsoft.khoahn.common.support.GeneratorPrimaryKeySupport")
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "ops_name")
	private String opsName;

	@Column(name = "function_id")
	private String functionId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "function_id", insertable = false, updatable = false)
	private FunctionEntity function;

}