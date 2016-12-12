package com.sap.lbh.util;
import java.util.Map;
import java.util.HashMap;
import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.eclipse.persistence.config.BatchWriting;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.context.annotation.Configuration;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.cce.repository")
public class CustomJPAConfig extends JpaBaseConfiguration {

	protected CustomJPAConfig(DataSource dataSource, JpaProperties properties,
			ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider) {
		super(dataSource, properties, jtaTransactionManagerProvider);
		// TODO Auto-generated constructor stub
	}


	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder, final DataSource ds) {
		final LocalContainerEntityManagerFactoryBean ret = builder.dataSource(ds).packages("com.sap.lbh.entities").persistenceUnit("Model").properties(getVendorProperties()).build();
		return ret;
	}


	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}


	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		return new EclipseLinkJpaVendorAdapter();
	}


	@Override
	protected Map<String, Object> getVendorProperties() {
		
		final Map<String, Object> ret = new HashMap<>();
		
		// Use in testing environment - drop existing database on every execution. 
//		ret.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE);
		
		// Use in production environment - reuse existing database.
		ret.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
		
		ret.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
		ret.put(PersistenceUnitProperties.WEAVING, "FALSE");
		return ret;
	}


}