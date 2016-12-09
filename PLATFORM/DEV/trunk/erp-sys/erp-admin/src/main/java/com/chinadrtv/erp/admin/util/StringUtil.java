/**
 * 
 */
package com.chinadrtv.erp.admin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  
 * @author haoleitao
 * @date 2013-3-8 下午4:18:33
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
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
	 
	 public static void BubbleSort(){
		          int score[] = {67, 69, 75, 87, 99, 99, 99, 100};
		          for (int i = 0; i < score.length -1; i++){    //最多做n-1趟排序
		               for(int j = 0 ;j < score.length - i - 1; j++){    //对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
		                   if(score[j] < score[j + 1]){    //把小的值交换到后面
		                       int temp = score[j];
		                       score[j] = score[j + 1];
		                       score[j + 1] = temp;
		                  }
		              }            
		             System.out.print("第" + (i + 1) + "次排序结果：");
		              for(int a = 0; a < score.length; a++){
		                  System.out.print(score[a] + "\t");
		              }
		             System.out.println("");
		          }
		              System.out.print("最终排序结果：");
		              for(int a = 0; a < score.length; a++){
		                  System.out.print(score[a] + "\t");
		         }
		      }
	 
	 public static void main(String[] args){
		 StringUtil.BubbleSort();
	 }
	 
}
