package com.fsoft.khoahn.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fsoft.khoahn.repository.entity.pk.ProjectUserId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(ProjectUserId.class)
@Table(name = "project_user")
public class ProjectUserEntity extends Auditable<String> {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Integer id;

	@Id
	@Column(name = "user_id")
	private String userId;

	@Id
	@Column(name = "project_id")
	private String projectId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
	private ProjectEntity project;

}
