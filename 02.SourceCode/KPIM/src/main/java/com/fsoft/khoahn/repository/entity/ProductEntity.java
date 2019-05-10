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
@Table(name = "products")
public class ProductEntity extends Auditable<String> {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "id")
    private StatusEntity status;

    @Column(name = "status", insertable = false, updatable = false)
    private Integer statusId;

    @ManyToOne
    @JoinColumn(name = "component_id", referencedColumnName = "id")
    private ComponentEntity component;

    @ManyToOne
    @JoinColumn(name = "scope_id", referencedColumnName = "id")
    private ScopeEntity scope;

}
