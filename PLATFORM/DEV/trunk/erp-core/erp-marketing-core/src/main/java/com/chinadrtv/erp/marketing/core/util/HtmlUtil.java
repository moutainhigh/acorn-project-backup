/*
 * @(#)HtmlUtil.java 1.0 2013-4-9下午2:30:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.chinadrtv.erp.smsapi.util.PropertiesUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-9 下午2:30:31
 * 
 */
public class HtmlUtil {

	public static String newHtml(String content) {
		StringBuilder path = new StringBuilder();
		String newPath = "";
		path.append(PropertiesUtil.getHtml());// 保存生成Html文件的目录
		File file1;
		String str = null;
		Date todaytime = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat ttime = new SimpleDateFormat("yyyyMMddhhMMSS");
		String name = ttime.format(todaytime);// 生成当前时间作为文件名
		String folder = date.format(todaytime);// 生成当前日期作为文件夹名
		File file = new File(path.toString());
		File[] files = file.listFiles();// 得到根目录下的所有文件名目录
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {// 判断是否为目录
				if (files[i].getName().equals(folder)) {
					str = files[i].getName();
				}
			}
		}
		if (str == null) {
			file1 = new File(path.append("/" + folder + "/").toString());
			file1.mkdir();// 如果str等于空.则在根目录下创建folder目录.
			System.out.println("1:" + path);
		} else {
			path.append("/" + str + "/");// 如果str不为空,则用原目录
			System.out.println("2:" + path);
		}
		File file2 = new File(path.append(name + ".html").toString());
		System.out.println(path);
		StringBuilder sb = new StringBuilder();
		try {
			file2.createNewFile();// 创建文件
			sb.append("<html><head><title>Html Test</title><meta http-equiv='Content-Type' content='text/html;charset=utf-8'/></head><body>");
			sb.append("<div align='center'>");
			sb.append(content);
			sb.append("</div>");
			sb.append("</body></html>");
			PrintStream printStream = new PrintStream(new FileOutputStream(
					file2));
			printStream.println(sb.toString());// 将字符串写入文件
			newPath = path.toString().replace("\\", "/");
			newPath = PropertiesUtil.getHtmlUrl() + newPath;
			newPath = newPath.replace(PropertiesUtil.getHtml(), "");
			System.out.println(newPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newPath;
	}

	/**
	 * @Description: TODO
	 * @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// HtmlUtil.newHtml("123");
		// String temp = "d:\\abcd\\abc.jpg";
		// String dirs = "";
		// dirs = temp.substring(0, temp.lastIndexOf("\\"));
		// System.out.println(dirs);
		// File file = new File(dirs);
		// if (!file.exists()) {
		// file.mkdir();
		// System.out.println("===");
		// }
		// targetFile.mkdir();

		String newPath = "http://html/d://201305/201305081105549.html";
		System.out.println(newPath.replace("d://", ""));
	}
}
