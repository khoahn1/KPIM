package com.fsoft.khoahn.repository.entity;

import java.util.Date;

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


/**
 * The persistent class for the customers database table.
 * 
 */
@Getter
@Setter
@Entity
@Table(name="customers")
public class CustomerEntity extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", parameters = @Parameter(name = Constants.PREFIX_PARAM, value = Constants.CUSTOMER_KEY), strategy = "com.fsoft.khoahn.common.support.GeneratorPrimaryKeySupport")
	@Column(name = "id", nullable = false)
	private String id;

	private String address;

	private Date birthday;

	@Column(name="customer_type")
	private Integer customerType;

	private String email;

	@Column(name="full_name")
	private String fullName;

	private String avatar;

	private Integer gender;

	@Column(name="last_trading_day")
	private Date lastTradingDay;

	@Column(name="location_id")
	private String locationId;

	private String note;

	private String organization;

	private String phone;

	private Integer status;

	@Column(name="tax_code")
	private String taxCode;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Date updatedDate;

	@Column(name="ward_id")
	private String wardId;

}