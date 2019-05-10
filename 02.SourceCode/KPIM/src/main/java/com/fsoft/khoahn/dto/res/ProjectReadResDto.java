package com.fsoft.khoahn.dto.res;

import lombok.Data;

@Data
public class ProjectReadResDto {

    private String id;
	
	private String projectCode;
	
	private String projectName;
	
	private Integer status;
	
	private String createDate;
	
	private String updateDate;
	
	private String createBy;
	
	private String updateBy;
	
	private DepartmentDetailResDto department;
}
