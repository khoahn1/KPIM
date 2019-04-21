package com.fsoft.khoahn.repository.entity.pk;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleOperationId implements Serializable {
    private static final long serialVersionUID = 1L;
	private Integer id;
	private String roleId;
    private String operationId;
}
