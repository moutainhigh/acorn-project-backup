/**
 * 
 */
package com.chinadrtv.erp.admin.util;

import java.util.List;
import java.util.Map;

/**
 *  
 * @author haoleitao
 * @date 2013-3-13 下午2:36:56
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MyConstantUtils {
	  public static MyConstantUtils obj;
      public MyConstantUtils (String string) {
                obj = this;        
      }

    public List channelType;

    
	public List getChannelType() {
		return channelType;
	}
	public void setChannelType(List channelType) {
		this.channelType = channelType;
	}

       
      

}
