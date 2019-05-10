package com.fsoft.khoahn.repository.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fsoft.khoahn.common.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parentDepartments")
public class ParentDepartmentEntity extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", parameters = @Parameter(name = Constants.PREFIX_PARAM, value = Constants.PARENT_DEPARTMENT_KEY), strategy = "com.fsoft.khoahn.common.support.GeneratorPrimaryKeySupport")
	@Column(name = "id", nullable = false)
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id", insertable = false, updatable = false)
	private CompanyEntity company;

	@Column(name = "parentDepartment_code")
	private String parentDepartmentCode;
	
	@Column(name = "parentDepartment_name")
	private String parentDepartmentName;
	
	@Column(name="status")
	private Integer status;
	
	@OneToMany(mappedBy = "parentDepartment", fetch = FetchType.EAGER)
	private List<DepartmentEntity> departments;
}
