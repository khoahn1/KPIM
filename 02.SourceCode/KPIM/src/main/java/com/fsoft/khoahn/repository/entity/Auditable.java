package com.fsoft.khoahn.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable<U> {

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	protected U createdBy;

	@Column(name = "created_date", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	protected Date createdDate;

	@LastModifiedBy
	@Column(name = "updated_by")
	protected U updatedBy;

	@Column(name = "updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	protected Date updatedDate;
}
