package com.sap.lbh.util;

import javax.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

public class JPAServiceFactory extends CustomOdataServiceFactory {

	public static final String DEFAULT_ENTITY_UNIT_NAME = "Model";
	public static final String ENTITY_MANAGER_FACTORY_ID = "entityManagerFactory";

	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
		ODataJPAContext oDataJPAContext = getODataJPAContext();

		EntityManagerFactory factory = (EntityManagerFactory) SpringContextsUtil.getBean(ENTITY_MANAGER_FACTORY_ID);

		oDataJPAContext.setEntityManagerFactory(factory);
		oDataJPAContext.setPersistenceUnitName(DEFAULT_ENTITY_UNIT_NAME);
		//oDataJPAContext.setJPAEdmExtension(new JPAEdmExtension());
		//oDataJPAContext.setODataProcessor(new CustomJPAOdataProcessor(oDataJPAContext));
		ODataContextUtil.setODataContext(oDataJPAContext.getODataContext());
		
		return oDataJPAContext;
	}
}