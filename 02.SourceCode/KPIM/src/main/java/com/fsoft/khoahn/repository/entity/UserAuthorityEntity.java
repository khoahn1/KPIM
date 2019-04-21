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

import com.fsoft.khoahn.repository.entity.pk.UserOperationId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(UserOperationId.class)
@Table(name = "user_authority")
public class UserAuthorityEntity extends Auditable<String> {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Integer id;

	@Id
	@Column(name = "user_id")
	private String userId;

	@Id
	@Column(name = "operation_id")
	private String operationId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "operation_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OperationEntity operation;

	@Column(name = "authority")
	private Integer authority;

}
