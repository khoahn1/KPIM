package com.fsoft.khoahn.repository.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name="projects")
public class ProjectEntity extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", parameters = @Parameter(name = Constants.PREFIX_PARAM, value = Constants.PROJECT_KEY), strategy = "com.fsoft.khoahn.common.support.GeneratorPrimaryKeySupport")
	@Column(name = "id", nullable = false)
	private String id;
	
	@Column(name="project_code")
	private String projectCode;
	
	@Column(name="project_name")
	private String projectName;
	
	@Column(name="status")
	private Integer status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private DepartmentEntity department;
	
	@Column(name = "department_id", insertable = false, updatable = false)
	private String departmentId;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<ProjectUserEntity> projectUsers = new ArrayList<ProjectUserEntity>();
}
