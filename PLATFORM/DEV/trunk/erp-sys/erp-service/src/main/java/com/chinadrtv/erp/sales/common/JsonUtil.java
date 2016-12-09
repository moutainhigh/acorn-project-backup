package com.chinadrtv.erp.sales.common;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class JsonUtil {
	public static  String jsonArrayWithFilterObject(List<?> list,final String...attributes) {
		if(list==null){
			return "";
		}
		if(attributes ==null){
			return JSONArray.fromObject(list).toString();
		}
		
		JsonConfig config = new JsonConfig();
        config.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object arg0, String arg1, Object arg2) {
            	for (String obj :attributes) {
            		if (arg1.equals(obj)) {
                        return true;
                     }
				}
                    return false;
            }
        });
		JSONArray  jsonArray = JSONArray.fromObject(list,config);
		return jsonArray.toString();
	}
	
	public static  String jsonObjectWithFilterObject(Object object,final String...attributes) {
		if(object==null){
			return "";
		}
		if(attributes ==null){
			return JSONObject.fromObject(object).toString();
		}
		
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object arg0, String arg1, Object arg2) {
				for (String obj :attributes) {
					if (arg1.equals(obj)) {
						return true;
					}
				}
				return false;
			}
		});
		JSONObject  jsonObject = JSONObject.fromObject(object,config);
		return jsonObject.toString();
	}
	
}
