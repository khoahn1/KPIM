package com.fsoft.khoahn.repository.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "token")
public class TokenEntity {

    @Id
	@Column(name = "series")
    private String series;

	@Column(name = "value")
    private String value;

	@Column(name = "date")
    private Date date;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "user_login")
    private String userLogin;

}
