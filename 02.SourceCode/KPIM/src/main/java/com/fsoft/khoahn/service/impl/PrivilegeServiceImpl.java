package com.fsoft.khoahn.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.khoahn.model.response.dto.AuthorityDetailResDto;
import com.fsoft.khoahn.repository.PrivilegeRepo;
import com.fsoft.khoahn.repository.entity.FunctionEntity;
import com.fsoft.khoahn.repository.entity.OperationEntity;
import com.fsoft.khoahn.repository.entity.PrivilegeEntity;
import com.fsoft.khoahn.service.PrivilegeService;

@Service("privilegeReadService")
public class PrivilegeServiceImpl implements PrivilegeService {
	@Autowired
	private PrivilegeRepo privilegeRepo;

	@Override
	public List<AuthorityDetailResDto> findAll() {

		List<PrivilegeEntity> privilegeEntitys = privilegeRepo.findAll();
		List<AuthorityDetailResDto> privileges = new ArrayList<>();
		for (Iterator<PrivilegeEntity> privilegeDto = privilegeEntitys.iterator(); privilegeDto
				.hasNext();) {
			PrivilegeEntity privilegeReadResDto = privilegeDto.next();

			AuthorityDetailResDto privilege = new AuthorityDetailResDto();
			privilege.setId(privilegeReadResDto.getId());
			privilege.setText(privilegeReadResDto.getPrivilegeName());
			if (!privilegeReadResDto.getFunctions().isEmpty()) {
				List<FunctionEntity> functionReadResDtos = privilegeReadResDto.getFunctions();
				List<AuthorityDetailResDto> functions = new ArrayList<>();
				for (Iterator<FunctionEntity> functionDto = functionReadResDtos.iterator(); functionDto
						.hasNext();) {
					FunctionEntity functionReadResDto = functionDto.next();

					AuthorityDetailResDto function = new AuthorityDetailResDto();

					function.setId(functionReadResDto.getId());
					function.setText(functionReadResDto.getFunctionName());
					if (!functionReadResDto.getOperations().isEmpty()) {
						List<OperationEntity> operationReadResDtos = functionReadResDto.getOperations();
						List<AuthorityDetailResDto> operations = new ArrayList<>();
						for (Iterator<OperationEntity> operationDto = operationReadResDtos
								.iterator(); operationDto.hasNext();) {
							OperationEntity operationReadResDto = operationDto.next();
							AuthorityDetailResDto operation = new AuthorityDetailResDto();
							operation.setId(operationReadResDto.getId());
							operation.setText(operationReadResDto.getOpsName());
							operation.setOperation(true);
							operations.add(operation);
						}
						function.setItems(operations);
					}
					functions.add(function);
				}
				privilege.setItems(functions);
			}
			privileges.add(privilege);
		}

		return privileges;
	}

}
