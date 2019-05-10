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
@Table(name = "components")
public class ComponentEntity extends Auditable<String> {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;

    @Column(name = "component_name")
    private String componentName;

    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "id")
    private StatusEntity status;

    @Column(name = "status", insertable = false, updatable = false)
    private Integer statusId;

}
