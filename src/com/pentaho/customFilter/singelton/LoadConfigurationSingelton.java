package com.pentaho.customFilter.singelton;


import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.pentaho.customFilter.dao.DaoConfiguration;
import com.pentaho.customFilter.util.ExcepcionPentahoFilterSearch;
import com.pentaho.customFilter.util.UtilsPentahoFilter;


public class LoadConfigurationSingelton {
    
  private static LoadConfigurationSingelton loadConfiguration ;
  private static HashMap<String, DaoConfiguration> listConfiguration ;
    
    
  private HashMap<String, DaoConfiguration> getListConfiguration() {
    return listConfiguration;
  }
    
  @SuppressWarnings("unchecked")
  private LoadConfigurationSingelton() {
        
    try {
            
      YamlReader reader = new YamlReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("/configuration.yml")));
      listConfiguration = new HashMap<>();
      UtilsPentahoFilter randomPassword = new UtilsPentahoFilter();
      while (true) {
        DaoConfiguration configuration = new DaoConfiguration();
	    Map configurationFile = (Map) reader.read();
	    if (configurationFile == null) {
	     break;
	        }
	        configuration.setUsername((String)configurationFile.get("username"));
	        configuration.setPassword((String)configurationFile.get("password"));
	        configuration.setType(Boolean.parseBoolean((String) configurationFile.get("typeSecurty")));
	        if (configuration.getType() == true) {
	        	System.out.println ("dynamic token ");
	            configuration.setToken((String)configurationFile.get("token")+randomPassword.getRandomKey());
	            configuration.setTokenMD5((String)configurationFile.get("token")+randomPassword.getRandomKey());
			} else {
		        configuration.setToken((String)configurationFile.get("token"));
		        configuration.setTokenMD5((String)configurationFile.get("token"));
			}
	        configuration.setDst((HashMap<String, String>) configurationFile.get("dst"));
	        DaoConfiguration value = listConfiguration.put(configuration.getTokenMD5().trim(), configuration);
	        if (value != null) {
	        	throw new ExcepcionPentahoFilterSearch("the token is duplicated");
	        }
	      }
    } catch (YamlException e) {
       e.printStackTrace();
    }
  }
    
    
  public static HashMap<String, DaoConfiguration> getInstance() {
    if (loadConfiguration == null) {
      loadConfiguration = new LoadConfigurationSingelton();
    }
        
    return  loadConfiguration.getListConfiguration();
  }
    
}
