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

import com.fsoft.khoahn.repository.entity.pk.RoleOperationId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(RoleOperationId.class)
@Table(name = "role_authority")
public class RoleAuthorityEntity extends Auditable<String> {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Integer id;

	@Id
	@Column(name = "role_id")
	private String roleId;

	@Id
	@Column(name = "operation_id")
	private String operationId;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
	private RoleEntity role;

	@ManyToOne
	@JoinColumn(name = "operation_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OperationEntity operation;

	@Column(name = "authority")
	private Integer authority;

}
