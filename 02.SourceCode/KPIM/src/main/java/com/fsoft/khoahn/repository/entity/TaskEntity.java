package com.fsoft.khoahn.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class TaskEntity extends Auditable<String> {

	@Id
//	@GeneratedValue(generator = "generator")
//	@GenericGenerator(name = "generator", parameters = @Parameter(name = Constants.PREFIX_PARAM, value = Constants.TASK_KEY), strategy = "com.fsoft.khoahn.common.support.GeneratorPrimaryKeySupport")
//	@Column(name = "id", nullable = false)
//	private String id;
	@GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
	@Column(name = "id", nullable = false)
    private Integer id;

	@Column(name = "task_code")
	private String taskCode;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

	@Column(name = "product_id", insertable = false, updatable = false)
	private Integer productId;

    @ManyToOne
    @JoinColumn(name = "phase_id", referencedColumnName = "id")
    private PhaseEntity phase;

	@Column(name = "phase_id", insertable = false, updatable = false)
	private Integer phaseId;

    @ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;

	@Column(name = "user_id", insertable = false, updatable = false)
	private String userId;

	@ManyToOne
	@JoinColumn(name = "status_id", referencedColumnName = "id")
	private StatusEntity status;

	@Column(name = "status_id", insertable = false, updatable = false)
	private Integer statusId;

}
