package com.fsoft.khoahn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fsoft.khoahn.common.Constants;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
		return new ResourceUrlEncodingFilter();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/vendor/**").addResourceLocations("/resources/vendor/").setCachePeriod(0)
				.resourceChain(true).addResolver(new GzipResourceResolver()).addResolver(new PathResourceResolver());
		registry.addResourceHandler("/" + Constants.PATH_PROFILE_PICS + "/**")
				.addResourceLocations("file:C://" + Constants.PATH_PROFILE_PICS + "/");
		registry.addResourceHandler("/" + Constants.PATH_EXPORT_DATA_USERS + "/**")
				.addResourceLocations("file:C://" + Constants.PATH_EXPORT_DATA_USERS + "/");
		registry.addResourceHandler("/" + Constants.PATH_EXPORT_DATA_CUSTOMERS + "/**")
				.addResourceLocations("file:C://" + Constants.PATH_EXPORT_DATA_CUSTOMERS + "/");
		registry.addResourceHandler("/" + Constants.PATH_EXPORT_DATA_SUPPLIERS + "/**")
				.addResourceLocations("file:C://" + Constants.PATH_EXPORT_DATA_SUPPLIERS + "/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:messages/messages");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}

	@Bean
	public InternalResourceViewResolver htmlViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/html/");
		bean.setSuffix(".html");
		return bean;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		return multipartResolver;
	}

	// @Override
	// public void extendMessageConverters(List<HttpMessageConverter<?>>
	// converters) {
	// converters.add(new CsvMessageConverter<>());
	// }

}
