package com.chinadrtv.erp.oms.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;

import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.model.PreTrade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 字符串操作
 * 
 * @author haoleitao
 * @date 2013-1-6 上午9:26:00
 *
 */
public class StringUtil {
	/**
	 * @param str
	 * @return
	 * @author haoleitao
	 * @date 2013-1-8 上午11:48:10
	 */
	public static String nullToBlank(String str){
		return str == null?"":str;
	}
	
	public static Boolean nullToFalse(Boolean obj){
		return obj == null ? false : obj;
	}
	
	public static Integer nullTo0(Integer obj){
		return obj == null ? 0 : obj;
	}
	
	public static Object objnullToBlank(Object obj){
		return obj == null? "":obj;
	}
	public static boolean isNullOrBank(String str){
		return (str == null || str.trim().equals("")) ? true : false ;
		
	}
	
	 public static String replaceBlank(String str) {
		 	        String dest = "";
		 	        if (str!=null) {
		 	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		 	            Matcher m = p.matcher(str);
		 	            dest = m.replaceAll("");
		 	        }
		 	        return dest;
		 	    }
	 
	 public static String stringToJson(String s){    
         StringBuffer sb = new StringBuffer();     
         if (s == null){
        	 return "";
         }else{
         for(int i=0; i<s.length(); i++){     
       
             char c =s.charAt(i);     
             switch(c){     
             case'\"':     
                 sb.append("\\\"");     
                 break;     
//             case'\\':   //如果不处理单引号，可以释放此段代码，若结合下面的方法处理单引号就必须注释掉该段代码
//                 sb.append("\\\\");     
//                 break;     
             case'/':     
                 sb.append("\\/");     
                 break;     
             case'\b':      //退格
                 sb.append("\\b");     
                 break;     
             case'\f':      //走纸换页
                 sb.append("\\f");     
                 break;     
             case'\n':     
                 sb.append("\\n");//换行    
                 break;     
             case'\r':      //回车
                 sb.append("\\r");     
                 break;     
             case'\t':      //横向跳格
                 sb.append("\\t");     
                 break;  
             case'{':      //横向跳格
                 sb.append("(");     
                 break;  
             case'}':      //横向跳格
                 sb.append(")");     
                 break;  
             default:     
                 sb.append(c);    
             }}
         return sb.toString(); 
         }
      }


	 
public static void main(String[] args){
	String a = "aa{}bb";
	

	System.out.println(StringUtil.stringToJson(a));
}
}
