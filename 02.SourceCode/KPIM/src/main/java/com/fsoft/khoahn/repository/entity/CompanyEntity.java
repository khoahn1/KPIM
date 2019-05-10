package com.fsoft.khoahn.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fsoft.khoahn.common.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class CompanyEntity extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", parameters = @Parameter(name = Constants.PREFIX_PARAM, value = Constants.COMPANY_KEY), strategy = "com.fsoft.khoahn.common.support.GeneratorPrimaryKeySupport")
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "company_code")
	private String companyCode;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "tax_code")
	private String taxCode;

	@Column(name = "address")
	private String address;

	@Column(name = "province")
	private String province;
	
	@Column(name = "district")
	private String district;

	@Column(name = "phone")
	private String phone;

	@Column(name = "status")
	private Integer status;

}
