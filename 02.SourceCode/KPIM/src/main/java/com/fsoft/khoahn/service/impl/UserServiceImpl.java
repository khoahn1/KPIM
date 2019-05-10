package com.fsoft.khoahn.service.impl;

import java.io.IOException;
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

import com.fsoft.khoahn.common.Constants;
import com.fsoft.khoahn.common.download.ExcelGenerator;
import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.common.utils.FileUploadUtils;
import com.fsoft.khoahn.common.utils.PageRequestUtils;
import com.fsoft.khoahn.dto.req.AuthorityUpdateReqDto;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.req.UserCreateReqDto;
import com.fsoft.khoahn.dto.req.UserDeleteReqDto;
import com.fsoft.khoahn.dto.req.UserReadReqDto;
import com.fsoft.khoahn.dto.req.UserSearchReqDto;
import com.fsoft.khoahn.dto.req.UserUpdateReqDto;
import com.fsoft.khoahn.dto.res.AuthorityDetailResDto;
import com.fsoft.khoahn.dto.res.DataExportResDto;
import com.fsoft.khoahn.dto.res.UserDetailResDto;
import com.fsoft.khoahn.dto.res.UserReadResDto;
import com.fsoft.khoahn.model.UserImportExportContent;
import com.fsoft.khoahn.repository.PrivilegeRepo;
import com.fsoft.khoahn.repository.RoleRepo;
import com.fsoft.khoahn.repository.UserAuthorityRepo;
import com.fsoft.khoahn.repository.UserRepo;
import com.fsoft.khoahn.repository.entity.FunctionEntity;
import com.fsoft.khoahn.repository.entity.OperationEntity;
import com.fsoft.khoahn.repository.entity.PrivilegeEntity;
import com.fsoft.khoahn.repository.entity.RoleAuthorityEntity;
import com.fsoft.khoahn.repository.entity.RoleEntity;
import com.fsoft.khoahn.repository.entity.UserAuthorityEntity;
import com.fsoft.khoahn.repository.entity.UserEntity;
import com.fsoft.khoahn.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PrivilegeRepo privilegeRepo;
	@Autowired
	private UserAuthorityRepo userAuthorityRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserReadResDto findByUsername(String username) {
		UserReadResDto userResDto = new UserReadResDto();
		UserEntity userEntity = userRepo.findByUsername(username);
		if (userEntity != null) {
			userResDto = modelMapper.map(userEntity, UserReadResDto.class);
		}
		return userResDto;
	}

	@Override
	public UserReadResDto findOne(String id) {
		UserReadResDto userReadResDto = new UserReadResDto();
		UserEntity userEntity = userRepo.findById(id).get();
		if (userEntity != null) {
			userReadResDto = modelMapper.map(userEntity, UserReadResDto.class);
		}
		return userReadResDto;
	}

	@Override
	public Page<UserDetailResDto> findAll(UserReadReqDto userReadReqDto) {
		PaginationReqDto paginationRequest = userReadReqDto.getPaginationRequest();
		UserSearchReqDto userSearchRequest = userReadReqDto.getUserSearchRequest();

		List<SortReqDto> sortDtoList = userReadReqDto.getSortRequest();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<UserEntity> userEntitys = userRepo.findByUsernameContainingAndEmailContaining(
				userSearchRequest.getUsername(), userSearchRequest.getEmail(), pageable);

		List<UserDetailResDto> userDtoList = new ArrayList<>();
		for (Iterator<UserEntity> iterator = userEntitys.getContent().iterator(); iterator
				.hasNext();) {
			UserEntity userEntity = iterator.next();
			UserDetailResDto userDetailResDto = modelMapper.map(userEntity, UserDetailResDto.class);
			List<AuthorityDetailResDto> privileges = mappingAuthorities(userEntity.getAuthorities());
			userDetailResDto.setItems(privileges);
			userDtoList.add(userDetailResDto);
		}

		Page<UserDetailResDto> page = new PageImpl<>(userDtoList, pageable, userEntitys.getTotalElements());
		return page;
	}

	@Override
	public void delete(UserDeleteReqDto userDeleteReqDto) throws IOException {
		FileUploadUtils.deleteFileByUrl(userDeleteReqDto.getAvatar());
		userRepo.deleteById(userDeleteReqDto.getId());
	}

	@Override
	public DataExportResDto exportExcelData() throws Exception {
		List<UserEntity> userEntitys = userRepo.findAll();
		Type typeUsers = new TypeToken<List<UserImportExportContent>>() {
		}.getType();
		List<UserImportExportContent> userImportExportContents = modelMapper.map(userEntitys, typeUsers);
		DataExportResDto resDto = ExcelGenerator.map(new UserImportExportContent(), userImportExportContents,
				"Users_DataExport",
				Constants.PATH_EXPORT_DATA_USERS);
		

		return resDto;
	}

	@Override
	public void save(UserCreateReqDto userCreateReqDto) throws InvalidFileException, IOException {
		String fileDownloadUri = null;
		if (userCreateReqDto.getAvatarFile() == null) {
			fileDownloadUri = Constants.PATH_DEFAULT_PROFILE_PICS;
		} else {
			fileDownloadUri = FileUploadUtils.uploadAvatar(userCreateReqDto.getAvatarFile(),
					userCreateReqDto.getUsername(), Constants.PATH_PROFILE_PICS);
		}
		userCreateReqDto.setAvatar(fileDownloadUri);

		UserEntity userEntity = modelMapper.map(userCreateReqDto, UserEntity.class);
		userEntity = userRepo.save(userEntity);

		RoleEntity roleEntity = roleRepo.findById(userCreateReqDto.getRole().getId()).get();
		List<UserAuthorityEntity> authorities = new ArrayList<UserAuthorityEntity>();
		if (roleEntity != null) {
			for (RoleAuthorityEntity roleAuthorityEntity : roleEntity.getAuthorities()) {
				UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();
				userAuthorityEntity.setUserId(userEntity.getId());
				userAuthorityEntity.setOperationId(roleAuthorityEntity.getOperation().getId());
				userAuthorityEntity.setAuthority(1);
				authorities.add(userAuthorityEntity);
			}
		}
		userEntity.setAuthorities(authorities);
		userEntity = userRepo.save(userEntity);
	}

	@Override
	public boolean isExitUsername(String username) {
		UserEntity userEntity = userRepo.findByUsername(username);
		return (userEntity != null);
	}

	@Override
	public boolean isExitUserId(String id) {
		UserEntity userEntity = userRepo.findById(id).get();
		return (userEntity != null);
	}

	@Override
	public void update(UserUpdateReqDto userUpdateReqDto) throws IOException {
		String fileDownloadUri = userUpdateReqDto.getAvatar();
		if (userUpdateReqDto.getAvatarFile() == null && fileDownloadUri == null) {
			fileDownloadUri = Constants.PATH_DEFAULT_PROFILE_PICS;
		} else if (userUpdateReqDto.getAvatarFile() != null) {
			FileUploadUtils.deleteFileByUrl(userUpdateReqDto.getAvatar());
			fileDownloadUri = FileUploadUtils.uploadAvatar(userUpdateReqDto.getAvatarFile(),
					userUpdateReqDto.getUsername(), Constants.PATH_PROFILE_PICS);
		}
		userUpdateReqDto.setAvatar(fileDownloadUri);
		UserEntity userEntity = modelMapper.map(userUpdateReqDto, UserEntity.class);
		RoleEntity roleEntity = roleRepo.findById(userUpdateReqDto.getRole().getId()).get();
		List<UserAuthorityEntity> authorities = new ArrayList<UserAuthorityEntity>();
		if (roleEntity != null) {
			for (RoleAuthorityEntity roleAuthorityEntity : roleEntity.getAuthorities()) {
				UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();
				userAuthorityEntity.setUserId(userEntity.getId());
				userAuthorityEntity.setOperationId(roleAuthorityEntity.getOperation().getId());
				userAuthorityEntity.setAuthority(roleAuthorityEntity.getAuthority());
				authorities.add(userAuthorityEntity);
			}
		}
		userEntity.setAuthorities(authorities);
		userEntity.setRole(roleEntity);

		userAuthorityRepo.deleteByUserId(userEntity.getId());
		userRepo.save(userEntity);
	}

	@Override
	public void updateUserAuthority(UserUpdateReqDto userUpdateReqDto) {
		UserEntity userEntity = modelMapper.map(userUpdateReqDto, UserEntity.class);
		List<UserAuthorityEntity> authorityEntities = new ArrayList<UserAuthorityEntity>();
		for (AuthorityUpdateReqDto authorityUpdateReqDto : userUpdateReqDto.getItems()) {
			UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();
			if (authorityUpdateReqDto.getUserAuthorityId() != null) {
				userAuthorityEntity.setId(authorityUpdateReqDto.getUserAuthorityId());
			}
			userAuthorityEntity.setUserId(userUpdateReqDto.getId());
			userAuthorityEntity.setOperationId(authorityUpdateReqDto.getId());
			userAuthorityEntity.setAuthority(authorityUpdateReqDto.isChecked() ? 1 : 0);
			authorityEntities.add(userAuthorityEntity);
		}
		userEntity.setAuthorities(authorityEntities);
		userRepo.save(userEntity);
	}

	private List<AuthorityDetailResDto> mappingAuthorities(List<UserAuthorityEntity> authorities) {
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
							UserAuthorityEntity userAuthority = authorities.stream()
									.filter(userAuthorityEntity -> operationDetailResDto.getId()
											.equals(userAuthorityEntity.getOperation().getId()))
									.findAny().orElse(null);
							operation.setId(operationDetailResDto.getId());
							if (userAuthority != null) {
								operation.setChecked(userAuthority.getAuthority() == 1 ? true : false);
								operation.setUserAuthorityId(userAuthority.getId());
								operation.setUserId(userAuthority.getUser().getId());
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
