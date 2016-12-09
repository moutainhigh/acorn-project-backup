/*
 * @(#)HtmlUtil.java 1.0 2013-4-9下午2:30:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.distribution.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-9 下午2:30:31
 * 
 */
public class HtmlUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(HtmlUtil.class);

	public static String newHtml(String content, String htmlFile,
			String htmlUrl, String oldFile) {
		StringBuilder path = new StringBuilder();
		String newPath = "";
		path.append(htmlFile);// 保存生成Html文件的目录
		File file1;
		String str = null;
		Date todaytime = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat ttime = new SimpleDateFormat("yyyyMMddhhMMSS");
		String name = ttime.format(todaytime);// 生成当前时间作为文件名
		String folder = date.format(todaytime);// 生成当前日期作为文件夹名
		File file = new File(path.toString());
		File[] files = file.listFiles();// 得到根目录下的所有文件名目录
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {// 判断是否为目录
					if (files[i].getName().equals(folder)) {
						str = files[i].getName();
					}
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
					file2), false, "utf-8");
			printStream.println(sb.toString());// 将字符串写入文件
			newPath = path.toString().replace("\\", "/");
			newPath = htmlUrl + newPath;
			newPath = newPath.replace(htmlFile, "");
			if (!StringUtil.isNullOrBank(oldFile)) {
				File isFile = new File(htmlFile
						+ oldFile.substring(oldFile.indexOf("html") + 4,
								oldFile.length()));
				isFile.delete();
			}
		} catch (IOException e) {
			logger.error("生成文件失败" + e.getMessage());
			e.printStackTrace();
		}
		return newPath;
	}

	/**
	 * 正则表达式匹配图片
	 * 
	 * @Description: TODO
	 * @param htmlStr
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getImgStr(String htmlStr) {
		String img = "", tmp = "";
		java.util.regex.Pattern p_image;
		java.util.regex.Matcher m_image;

		String regEx_img = "<img.*src=(.*?)[^>]*?>";// 图片链接地址
		p_image = java.util.regex.Pattern.compile(regEx_img,
				java.util.regex.Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
		}
		if (img.indexOf(",") >= 0)
			return img.substring(1);
		else
			return img;
	}

	/**
	 * @Description: TODO
	 * @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) {
		// li.list-cn-1-84{background-image:url(http://bs.baidu.com/listicon/list-cn-1-84.gif)}
		// li.list-cn-1-85{background-image:url(http://bs.baidu.com/listicon/list-cn-1-85.gif)}
		// li.list-cn-1-86{background-image:url(http://bs.baidu.com/listicon/list-cn-1-86.gif)}
		// li.list-cn-1-87{background-image:url(http://bs.baidu.com/listicon/list-cn-1-87.gif)}
		// li.list-cn-1-88{background-image:url(http://bs.baidu.com/listicon/list-cn-1-88.gif)}
		// li.list-cn-1-89{background-image:url(http://bs.baidu.com/listicon/list-cn-1-89.gif)}
		// li.list-cn-1-90{background-image:url(http://bs.baidu.com/listicon/list-cn-1-90.gif)}
		// li.list-cn-1-91{background-image:url(http://bs.baidu.com/listicon/list-cn-1-91.gif)}
		// li.list-cn-1-92{background-image:url(http://bs.baidu.com/listicon/list-cn-1-92.gif)}
		// li.list-cn-1-93{background-image:url(http://bs.baidu.com/listicon/list-cn-1-93.gif)}
		// li.list-cn-1-94{background-image:url(http://bs.baidu.com/listicon/list-cn-1-94.gif)}
		// li.list-cn-1-95{background-image:url(http://bs.baidu.com/listicon/list-cn-1-95.gif)}
		String oldFile = "http://bs.baidu.com/listicon/list-cn-1-84.gif/>)";
		// System.out.print(oldFile.substring(oldFile.indexOf("html") + 4,
		// oldFile.length()));
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
		String s = "<p>111111/p>";
		System.out.println(HtmlUtil.getImgStr(oldFile));
		// Pattern pattern = Pattern
		// .compile("src=\"(.{1,150})\"\s*/> ");
		// Matcher matcher = pattern.matcher(s);
		// while (matcher.find()) {
		// for (int j = 0; j <= matcher.groupCount(); j++)
		// System.out.print("[" + matcher.group(j) + "]");
		// System.out.println();
		// }
		// String newPath = "http://html/d://201305/201305081105549.html";
		// System.out.println(newPath.replace("d://", ""));
	}
}
