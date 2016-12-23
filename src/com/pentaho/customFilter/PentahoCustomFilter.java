package com.pentaho.customFilter;

import java.io.IOException;
import java.util.HashMap;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.pentaho.customFilter.singelton.LoadConfigurationSingelton;
import com.pentaho.customFilter.dao.DaoConfiguration;
import com.pentaho.customFilter.util.UtilsPentahoFilter;

public class PentahoCustomFilter implements Filter {
  public void doFilter(ServletRequest request, ServletResponse response,
  FilterChain chain) throws IOException, ServletException {
        
    HashMap<String, DaoConfiguration> _configurationList = LoadConfigurationSingelton.getInstance();
    UtilsPentahoFilter utils = new UtilsPentahoFilter();
        
        
    String dst = (String) request.getParameter("dst");
    String URLtoken = (String) request.getParameter("token");
        
      if ( _configurationList.containsKey(URLtoken) ){
        chain.doFilter(new InjectUser(request,utils.getObjectByToken(URLtoken, _configurationList)), response);
      }else {
        chain.doFilter(request, response);
      }
        
  }
    
    
  public void destroy() {
  }
    
  public void init(FilterConfig filterConfig) {
  }
    
    
}