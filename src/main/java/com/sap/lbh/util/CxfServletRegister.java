package com.sap.lbh.util;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;


import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CxfServletRegister {
	
	@Bean
	public ServletRegistrationBean getODataServletRegistrationBean() {
		ServletRegistrationBean odataServletRegistrationBean = new ServletRegistrationBean(new CXFNonSpringJaxrsServlet(), "/odata.svc/*");
		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("javax.ws.rs.Application", "org.apache.olingo.odata2.core.rest.app.ODataApplication");
		initParameters.put("org.apache.olingo.odata2.service.factory", "com.sap.lbh.util.JPAServiceFactory");
		odataServletRegistrationBean.setInitParameters(initParameters);
		return odataServletRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean getODataFilterRegistrationBean(ServletRegistrationBean getODataServletRegistrationBean) {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		
		FilterRegistrationBean corsFilterRegistrationBean = new FilterRegistrationBean(new CorsFilter(source), getODataServletRegistrationBean);
		corsFilterRegistrationBean.addUrlPatterns("/*");
		//corsFilterRegistrationBean.addInitParameter("cors.allowed.origins", "*");
		return corsFilterRegistrationBean;
	}
}