package com.pentaho.customFilter.util;


import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


import com.pentaho.customFilter.dao.DaoConfiguration;


public class UtilsPentahoFilter {
    
  public DaoConfiguration getObjectByToken(String password,HashMap<String, DaoConfiguration> list) {
    return list.get(password);
  }
  
  public String getRandomKey(){
	return  String.valueOf(5 + (int)(Math.random() * 10));
  }
}
