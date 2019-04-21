package com.fsoft.khoahn.common.support;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import com.fsoft.khoahn.common.Constants;

public class GeneratorPrimaryKeySupport implements IdentifierGenerator, Configurable {

	private String prefix;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.id.Configurable#configure(org.hibernate.type.Type,
	 * java.util.Properties, org.hibernate.service.ServiceRegistry)
	 */
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		prefix = params.getProperty(Constants.PREFIX_PARAM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.spi.
	 * SharedSessionContractImplementor, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

		String query = String.format("select %s from %s",
				session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),
				obj.getClass().getSimpleName());

		Stream<String> stream = session.createQuery(query).stream();

		Long max = stream.map(id -> id.replaceAll("([a-zA-Z]+)(0*)", "")).mapToLong(Long::parseLong).max().orElse(0L);

		int maxLength = String.valueOf(max + 1).length();
		int numOfZero = Constants.PREFIX_COUNT - maxLength;

		String prefixZero = StringUtils.repeat("0", numOfZero);

		return prefix + prefixZero + (max + 1);
	}
}