package com.fsoft.khoahn.repository.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "users")
public class UserEntity extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", parameters = @Parameter(name = Constants.PREFIX_PARAM, value = Constants.USER_KEY), strategy = "com.fsoft.khoahn.common.support.GeneratorPrimaryKeySupport")
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "address")
	private String address;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "email")
	private String email;

	@Column(name = "full_name")
	private String fullName;

	@ManyToOne
	@JoinColumn(name = "gender_id", referencedColumnName = "id")
	private GenderEntity gender;

	@Column(name = "phone")
	private String phone;

	@Column(name = "avatar")
	private String avatar;

	@ManyToOne
	@JoinColumn(name = "language_id", referencedColumnName = "id")
	private LanguageEntity language;

	@ManyToOne
	@JoinColumn(name = "marital_status_id", referencedColumnName = "id")
	private MaritalStatusEntity maritalStatus;

	@Column(name = "password")
	private String password;

	@ManyToOne
	@JoinColumn(name = "status_id", referencedColumnName = "id")
	private StatusEntity status;

	@Column(name = "username")
	private String username;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private RoleEntity role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserAuthorityEntity> authorities = new ArrayList<UserAuthorityEntity>();

}
