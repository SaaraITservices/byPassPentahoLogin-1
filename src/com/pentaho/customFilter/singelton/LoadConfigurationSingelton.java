package com.pentaho.customFilter.singelton;


import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.pentaho.customFilter.dao.DaoConfiguration;

/**
 * load the configuration 
 * @author sowe <ravamo@gmail.com>
 */
public class LoadConfigurationSingelton {
	
	private static LoadConfigurationSingelton loadConfiguration ;
	private static List<DaoConfiguration> listConfiguration ;
	
	private List<DaoConfiguration> getListConfiguration() {
		return listConfiguration;
	}

	private LoadConfigurationSingelton() {
		try {
			
			YamlReader reader = new YamlReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("/configuration.yml")));
			listConfiguration = new ArrayList<DaoConfiguration>();
			 while (true) {
				 DaoConfiguration _configuration = new DaoConfiguration();
			        Map contact = (Map) reader.read();
			        if (contact == null) break;
			        _configuration.setUsername((String)contact.get("username"));
			        _configuration.setPassword((String)contact.get("password"));
			        _configuration.setToken((String)contact.get("token"));
			        _configuration.setTokenMD5((String)contact.get("token"));
			        _configuration.setDst((List<String>) contact.get("dst"));
			        listConfiguration.add(_configuration);
			    }
		} catch (YamlException e) {
			e.printStackTrace();
		} 
	}

	
	public static List<DaoConfiguration> getInstance() { 
		if (loadConfiguration == null){
			loadConfiguration = new LoadConfigurationSingelton();
		}
		
		return  loadConfiguration.getListConfiguration();
	}

}
