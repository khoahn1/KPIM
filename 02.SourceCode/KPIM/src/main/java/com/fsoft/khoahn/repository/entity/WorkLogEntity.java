package com.fsoft.khoahn.repository.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "worklogs")
public class WorkLogEntity extends Auditable<String> {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;

    @Column(name = "plan_effort")
    private Double planEffort;

    @Column(name = "acctual_effort")
    private Double actualEffort;

    @Temporal(TemporalType.DATE)
    @Column(name = "log_date")
    private Date logDate;

    @Column(name = "anken")
    private String anken;

    @Column(name = "screen")
    private String screen;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "issue")
    private String issue;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private UnitEntity unit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private TaskEntity task;

    @ManyToOne
    @JoinColumn(name = "type_of_work_id", referencedColumnName = "id")
    private TypeOfWorkEntity typeOfWork;

    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "id")
    private StatusEntity status;

    @Column(name = "status", insertable = false, updatable = false)
    private Integer statusId;

}
