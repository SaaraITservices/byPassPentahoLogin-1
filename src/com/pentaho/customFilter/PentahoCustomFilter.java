package com.pentaho.customFilter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.pentaho.customFilter.dao.DaoConfiguration;
import com.pentaho.customFilter.singelton.*;
import com.pentaho.customFilter.util.ExcepcionPentahoFilterSearch;
import com.pentaho.customFilter.util.UtilsPentahoFilter;

public class PentahoCustomFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response,
    		FilterChain chain) throws IOException, ServletException {
    	
    		List<DaoConfiguration>_configurationList = LoadConfigurationSingelton.getInstance();
    		UtilsPentahoFilter utils = new UtilsPentahoFilter();
    		

    	String dst = (String) request.getParameter("dst");
    	String URLtoken = (String) request.getParameter("token");
 	
    	try {
    		
			if ( URLtoken.equals( utils.getObjectByToken(URLtoken, _configurationList).getTokenMD5()) ){
				chain.doFilter(new InjectUser(request,utils.getObjectByToken(URLtoken, _configurationList)), response);
			}else{
				chain.doFilter(request, response);
			}
		} catch (ExcepcionPentahoFilterSearch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

    
    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
    }
    
    
}