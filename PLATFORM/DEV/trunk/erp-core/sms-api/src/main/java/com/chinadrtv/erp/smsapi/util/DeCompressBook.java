/*
 * @(#)DeCompressBook.java 1.0 2013-7-17下午4:30:27
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-7-17 下午4:30:27
 * 
 */
public class DeCompressBook {
	private static final Logger logger = LoggerFactory
			.getLogger(DeCompressBook.class);

	public DeCompressBook() {
	}

	private void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if (subDirectory == "" && fl.exists() != true)
				fl.mkdir();
			else if (subDirectory != "") {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public Boolean unZip(String zipFileName, String outputDirectory)
			throws Exception {
		Boolean flag = false;
		try {
			org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(
					zipFileName);
			java.util.Enumeration e = zipFile.getEntries();
			org.apache.tools.zip.ZipEntry zipEntry = null;
			createDirectory(outputDirectory, "");
			logger.info("进入解压" + zipFileName + "" + e.hasMoreElements());
			while (e.hasMoreElements()) {
				zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
				logger.info("unziping " + zipEntry.getName());
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDirectory + File.separator + name);
					f.mkdir();
					logger.info("创建目录：" + outputDirectory + File.separator
							+ name);
				} else {
					String fileName = zipEntry.getName();
					fileName = fileName.replace('\\', '/');
					if (fileName.indexOf("/") != -1) {
						createDirectory(outputDirectory, fileName.substring(0,
								fileName.lastIndexOf("/")));
						fileName = fileName.substring(
								fileName.lastIndexOf("/") + 1,
								fileName.length());
					}

					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());

					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);

					byte[] by = new byte[1024];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
				flag = true;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return flag;
	}

	public static void main(String[] args) {
		DeCompressBook dCompressBook = new DeCompressBook();
		try {
			dCompressBook
					.unZip("d:\\acorn\\sms\\stopsms\\20130726241408102013072624135748000000033.zip",
							"d:\\201304\\");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
