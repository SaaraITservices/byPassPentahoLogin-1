package com.pentaho.customFilter;



import java.io.IOException;
import java.util.HashMap;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pentaho.customFilter.dao.DaoConfiguration;
import com.pentaho.customFilter.singelton.LoadConfigurationSingelton;
import com.pentaho.customFilter.util.ExcepcionPentahoFilterSearch;
import com.pentaho.customFilter.util.UtilsPentahoFilter;

public class FilterSecurity	 extends HttpServlet {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public FilterSecurity() {
        super();
  }
    
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    String dst = (String) request.getParameter("dst");
    String token = (String) request.getParameter("token");
    String destination = null;
        
    HashMap<String, DaoConfiguration> _configurationList = LoadConfigurationSingelton.getInstance();
    UtilsPentahoFilter utils = new UtilsPentahoFilter();
        
    try {
    	System.out.println (">>>>"+_configurationList.containsKey(token));
      if (_configurationList.containsKey(token)){
        System.out.println("The" + token + "is in a list");
        destination = utils.getObjectByToken(token, _configurationList).getDst().get(dst);
      } else {
        throw new ExcepcionPentahoFilterSearch("This token is not on the list"+ token);
      }
                       
            
    } catch (ExcepcionPentahoFilterSearch e) {
      e.printStackTrace();
      try {
		throw new ExcepcionPentahoFilterSearch(e + "###" + token);
	} catch (ExcepcionPentahoFilterSearch e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    }
        
    if (destination.indexOf("?")>0) {
      destination = destination  +  request.getQueryString();
    } else{
      destination = destination  + "?" +  request.getQueryString();
    }
        
    response.sendRedirect(destination);
        
  }
    
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
    
}
