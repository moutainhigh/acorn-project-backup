package com.chinadrtv.erp.task.jobs.chama;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Hibernate代理对象剔除
 * @author haoleitao
 * @date 2012-12-28 下午4:36:58
 */
public class ExcludeUnionResource implements ExclusionStrategy {
	
	private String[] fileds;

	public ExcludeUnionResource(String[] fileds){
		this.fileds = fileds;
	}
	 public boolean shouldSkipClass(Class<?> arg0) {
         return false;
     }

     public boolean shouldSkipField(FieldAttributes f) {
    	 boolean r =false;
          for(String ff : fileds){
        	  r = r || f.getName().equals(ff);
          }
         return r ;
     }

}
