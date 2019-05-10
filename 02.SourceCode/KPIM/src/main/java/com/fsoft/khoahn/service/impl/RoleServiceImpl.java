package com.fsoft.khoahn.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.khoahn.common.utils.PageRequestUtils;
import com.fsoft.khoahn.dto.req.AuthorityCreateReqDto;
import com.fsoft.khoahn.dto.req.AuthorityUpdateReqDto;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.RoleCreateReqDto;
import com.fsoft.khoahn.dto.req.RoleDeleteReqDto;
import com.fsoft.khoahn.dto.req.RoleReadReqDto;
import com.fsoft.khoahn.dto.req.RoleSearchReqDto;
import com.fsoft.khoahn.dto.req.RoleUpdateReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.res.AuthorityDetailResDto;
import com.fsoft.khoahn.dto.res.RoleDetailResDto;
import com.fsoft.khoahn.repository.PrivilegeRepo;
import com.fsoft.khoahn.repository.RoleRepo;
import com.fsoft.khoahn.repository.entity.FunctionEntity;
import com.fsoft.khoahn.repository.entity.OperationEntity;
import com.fsoft.khoahn.repository.entity.PrivilegeEntity;
import com.fsoft.khoahn.repository.entity.RoleAuthorityEntity;
import com.fsoft.khoahn.repository.entity.RoleEntity;
import com.fsoft.khoahn.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PrivilegeRepo privilegeRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public RoleDetailResDto findOne(String id) {
		RoleDetailResDto roleDetailResDto = new RoleDetailResDto();
		RoleEntity roleEntity = roleRepo.findById(id).get();
		if (roleEntity != null) {
			roleDetailResDto = modelMapper.map(roleEntity, RoleDetailResDto.class);
		}
		List<AuthorityDetailResDto> roleAuthorities = mappingAuthorities(roleEntity.getAuthorities(), "02");
		roleDetailResDto.setItems(roleAuthorities);
		return roleDetailResDto;
	}

	@Override
	public List<RoleDetailResDto> findAll() {
		List<RoleEntity> privilegeEntitys = roleRepo.findAll();
		Type typeRoles = new TypeToken<List<RoleDetailResDto>>() {
		}.getType();
		List<RoleDetailResDto> roleReadResDtos = modelMapper.map(privilegeEntitys, typeRoles);
		return roleReadResDtos;
	}

	@Override
	public Page<RoleDetailResDto> findAll(RoleReadReqDto roleReadReqDto) {
		PaginationReqDto paginationRequest = roleReadReqDto.getPaginationRequest();
		RoleSearchReqDto roleSearchRequest = roleReadReqDto.getRoleSearchRequest();

		List<SortReqDto> sortDtoList = roleReadReqDto.getSortRequest();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<RoleEntity> roleEntitys = roleRepo.findByIdContainingAndRoleNameContaining(
				roleSearchRequest.getId(), roleSearchRequest.getRoleName(), pageable);

		Type typeRoles = new TypeToken<List<RoleDetailResDto>>() {
		}.getType();
		List<RoleDetailResDto> roleDetailResDtos = modelMapper.map(roleEntitys.getContent(), typeRoles);

		Page<RoleDetailResDto> page = new PageImpl<>(roleDetailResDtos, pageable,
				roleEntitys.getTotalElements());
		return page;
	}

	@Override
	public List<AuthorityDetailResDto> getRoleAuthority() {
		List<AuthorityDetailResDto> privileges = mappingAuthorities(null, null);
		return privileges;
	}

	@Override
	public boolean isExitRoleName(String roleName) {
		RoleEntity roleEntity = roleRepo.findByRoleName(roleName);
		return (roleEntity != null);
	}

	@Override
	public void save(RoleCreateReqDto roleCreateReqDto) {

		RoleEntity roleEntity = modelMapper.map(roleCreateReqDto, RoleEntity.class);
		roleEntity = roleRepo.save(roleEntity);
		List<RoleAuthorityEntity> authorities = new ArrayList<RoleAuthorityEntity>();
		if (roleCreateReqDto.getItems() != null) {
			for (AuthorityCreateReqDto authorityCreateReqDto : roleCreateReqDto.getItems()) {
				RoleAuthorityEntity roleAuthorityEntity = new RoleAuthorityEntity();
				roleAuthorityEntity.setRoleId(roleEntity.getId());
				roleAuthorityEntity.setOperationId(authorityCreateReqDto.getId());
				roleAuthorityEntity.setAuthority(authorityCreateReqDto.isChecked() ? 1 : 0);
				authorities.add(roleAuthorityEntity);
			}
		}
		roleEntity.setAuthorities(authorities);
		roleRepo.save(roleEntity);
	}

	@Override
	public void update(RoleUpdateReqDto roleUpdateReqDto) {
		RoleEntity roleEntity = modelMapper.map(roleUpdateReqDto, RoleEntity.class);
		List<RoleAuthorityEntity> authorities = new ArrayList<RoleAuthorityEntity>();
		if (roleUpdateReqDto.getItems() != null) {
			for (AuthorityUpdateReqDto authorityUpdateReqDto : roleUpdateReqDto.getItems()) {
				RoleAuthorityEntity roleAuthorityEntity = new RoleAuthorityEntity();
				if (authorityUpdateReqDto.getRoleAuthorityId() != null) {
					roleAuthorityEntity.setId(authorityUpdateReqDto.getRoleAuthorityId());
				}
				roleAuthorityEntity.setRoleId(roleEntity.getId());
				roleAuthorityEntity.setOperationId(authorityUpdateReqDto.getId());
				roleAuthorityEntity.setAuthority(authorityUpdateReqDto.isChecked() ? 1 : 0);
				authorities.add(roleAuthorityEntity);
			}
		}
		roleEntity.setAuthorities(authorities);
		roleRepo.save(roleEntity);

	}

	@Override
	public void delete(RoleDeleteReqDto roleDeleteReqDto) {
		roleRepo.deleteById(roleDeleteReqDto.getId());
	}

	private List<AuthorityDetailResDto> mappingAuthorities(List<RoleAuthorityEntity> authorities, String typeEvent) {
		List<PrivilegeEntity> privilegeEntitys = privilegeRepo.findAll();
		List<AuthorityDetailResDto> privileges = new ArrayList<>();
		for (Iterator<PrivilegeEntity> privilegeDto = privilegeEntitys.iterator(); privilegeDto.hasNext();) {
			PrivilegeEntity privilegeReadResDto = privilegeDto.next();

			AuthorityDetailResDto privilege = new AuthorityDetailResDto();
			privilege.setId(privilegeReadResDto.getId());
			privilege.setText(privilegeReadResDto.getPrivilegeName());

			List<AuthorityDetailResDto> functions = new ArrayList<>();
			if (!privilegeReadResDto.getFunctions().isEmpty()) {
				List<FunctionEntity> functionDetailResDtos = privilegeReadResDto.getFunctions();
				for (Iterator<FunctionEntity> functionDto = functionDetailResDtos.iterator(); functionDto.hasNext();) {
					FunctionEntity functionDetailResDto = functionDto.next();

					AuthorityDetailResDto function = new AuthorityDetailResDto();
					function.setId(functionDetailResDto.getId());
					function.setText(functionDetailResDto.getFunctionName());

					List<AuthorityDetailResDto> operations = new ArrayList<>();
					if (!functionDetailResDto.getOperations().isEmpty()) {
						List<OperationEntity> operationDetailResDtos = functionDetailResDto.getOperations();
						for (Iterator<OperationEntity> operationDto = operationDetailResDtos.iterator(); operationDto
								.hasNext();) {
							OperationEntity operationDetailResDto = operationDto.next();
							AuthorityDetailResDto operation = new AuthorityDetailResDto();
							operation.setId(operationDetailResDto.getId());
							if (("02").equals(typeEvent)) {
								RoleAuthorityEntity roleAuthority = authorities.stream()
										.filter(roleAuthorityEntity -> operationDetailResDto.getId()
												.equals(roleAuthorityEntity.getOperation().getId()))
										.findAny().orElse(null);
								if (roleAuthority != null) {
									operation.setChecked(roleAuthority.getAuthority() == 1 ? true : false);
									operation.setRoleAuthorityId(roleAuthority.getId());
									operation.setRoleId(roleAuthority.getRole().getId());
								}
							}
							operation.setText(operationDetailResDto.getOpsName());
							operation.setOperation(true);
							operations.add(operation);
						}
						function.setItems(operations);
					}
					if (!operations.isEmpty()) {
						functions.add(function);
					}
				}
				privilege.setItems(functions);
			}
			if (!functions.isEmpty()) {
				privileges.add(privilege);
			}
		}
		return privileges;
	}

}
