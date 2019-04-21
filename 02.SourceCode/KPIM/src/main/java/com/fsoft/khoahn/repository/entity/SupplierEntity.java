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
 * The persistent class for the suppliers database table.
 * 
 */
@Getter
@Setter
@Entity
@Table(name="suppliers")
public class SupplierEntity extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", parameters = @Parameter(name = Constants.PREFIX_PARAM, value = Constants.SUPPLIER_KEY), strategy = "com.fsoft.khoahn.common.support.GeneratorPrimaryKeySupport")
	@Column(name = "id", nullable = false)
	private String id;

	private String address;

	private String email;

	@Column(name="last_trading_day")
	private Date lastTradingDay;

	@Column(name="location_id")
	private String locationId;

	private String note;

	private String organization;

	private String phone;

	private Integer status;

	@Column(name="supplier_name")
	private String supplierName;

	@Column(name="tax_code")
	private String taxCode;

	@Column(name="ward_id")
	private String wardId;
}