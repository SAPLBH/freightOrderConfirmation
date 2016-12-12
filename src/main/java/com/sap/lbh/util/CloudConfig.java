package com.sap.lbh.util;

import javax.sql.DataSource;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig

{
  @Bean
  public DataSource dataSource(){
  DataSource retVal = null;
  try{
	  return connectionFactory().dataSource("hana-db");
  }
  catch (CloudException ex){
	  ex.printStackTrace();
 }
 return retVal;
  }
}