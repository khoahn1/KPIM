package com.fsoft.khoahn.service.impl;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.fsoft.khoahn.security.SecurityUtils;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of(SecurityUtils.getCurrentLogin());
	}

}
