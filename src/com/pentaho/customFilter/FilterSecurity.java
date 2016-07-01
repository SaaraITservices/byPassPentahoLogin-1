package com.pentaho.customFilter;



import java.io.IOException;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pentaho.customFilter.dao.DaoConfiguration;
import com.pentaho.customFilter.singelton.LoadConfigurationSingelton;
import com.pentaho.customFilter.util.ExcepcionPentahoFilterSearch;
import com.pentaho.customFilter.util.UtilsPentahoFilter;

public class FilterSecurity extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public FilterSecurity() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String dst = (String) request.getParameter("dst");
		String token = (String) request.getParameter("token");
		String destination = null;
		
		List<DaoConfiguration>_configurationList = LoadConfigurationSingelton.getInstance();
		UtilsPentahoFilter utils = new UtilsPentahoFilter();
		
		try {
			
			if (token.equals( utils.getObjectByToken(token, _configurationList).getTokenMD5())){
				destination = utils.getObjectByToken(token, _configurationList).getDst().get(Integer.parseInt(dst.substring(3)));
			}
			
			System.out.println("Token: "+utils.getObjectByToken(token, _configurationList).getToken());
			System.out.println("TokenMD5: "+utils.getObjectByToken(token, _configurationList).getTokenMD5());
			System.out.println("dst: "+utils.getObjectByToken(token, _configurationList).getDst());
			
			
		} catch (ExcepcionPentahoFilterSearch e) {
			
		}
	
		if( destination.indexOf("?")> 0){
			destination = destination  +  request.getQueryString();
		}else{
			destination = destination  + "?" +  request.getQueryString();
		}
		 
		response.sendRedirect(destination);
        
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
