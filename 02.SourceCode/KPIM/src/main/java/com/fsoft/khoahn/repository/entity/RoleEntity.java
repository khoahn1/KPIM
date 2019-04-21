package com.fsoft.khoahn.repository.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name="roles")
public class RoleEntity extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", parameters = @Parameter(name = Constants.PREFIX_PARAM, value = Constants.ROLE_KEY), strategy = "com.fsoft.khoahn.common.support.GeneratorPrimaryKeySupport")
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "description")
	private String description;

	@Column(name="role_name")
	private String roleName;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<RoleAuthorityEntity> authorities = new ArrayList<RoleAuthorityEntity>();

}