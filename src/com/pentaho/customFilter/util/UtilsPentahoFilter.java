package com.pentaho.customFilter.util;


import java.util.List;


import com.pentaho.customFilter.dao.DaoConfiguration;

/**
 * 
 * @author sowe 
 *
 */
public class UtilsPentahoFilter {

	public DaoConfiguration getObjectByToken(String password, List<DaoConfiguration> list) throws ExcepcionPentahoFilterSearch{
		List<DaoConfiguration> daoList = list;
		DaoConfiguration value = null;
	    for (DaoConfiguration configuration : daoList) {
	        if (configuration.getTokenMD5().equals(password)) {
	            value = configuration;
	            break;
	        }else {
	        	throw new ExcepcionPentahoFilterSearch("This token don't in a list: "+ password);
	        }
	    }
	    
	    return value;
	}
	
	public Long dynamicSecurity (){
		return System.currentTimeMillis() / 1000L;
	}
	
	public String getDstByToken(String dst, List<String> list) throws ExcepcionPentahoFilterSearch{
		List<String> daoList = list;
		String value = null;
	    for (String configuration : daoList) {
	        if (configuration.equals(dst)) {
	            value = dst;
	            break;
	        }else {
	        	throw new ExcepcionPentahoFilterSearch("The destination is not on the list");
	        }
	    }
	    
	    return value.trim();
	}

		 
}
