/*
 * @(#)FileUtil.java 1.0 2013-2-26下午2:12:45
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-26 下午2:12:45
 * 
 */
public class FileUtil {
	public static void write(String path, String content) {
		String s = new String();
		String s1 = new String();
		try {
			File f = new File(path);
			if (f.exists()) {
				System.out.println("文件存在");
			} else {
				System.out.println("文件不存在，正在创建...");
				if (f.createNewFile()) {
					System.out.println("文件创建成功！");
				} else {
					System.out.println("文件创建失败！");
				}

			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			while ((s = input.readLine()) != null) {
				s1 += s + "\n";
			}
			System.out.println("文件内容：" + s1);
			input.close();
			s1 += content;

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String read(String file) {
		String s = null;
		StringBuffer sb = new StringBuffer();
		File f = new File(file);
		if (f.exists()) {
			System.out.println("文件存在");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(f)));
				while ((s = br.readLine()) != null) {
					sb.append(s);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("文件不存在!");
		}
		return sb.toString();
	}

	public static String readBatchid(String file) {
		String s = null;
		StringBuffer sb = new StringBuffer();
		File f = new File(file);
		String batchid = "";
		String bs[];
		if (f.exists()) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(f)));
				while ((s = br.readLine()) != null) {
					br.mark(1);
					sb.append(s);
					bs = sb.toString().split("\t");
					batchid = bs[1];
					// System.out.println(batchid);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("文件不存在!");
		}
		return batchid;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public synchronized static boolean deleteFile(String sPath) {
		Boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @Description: 多次写入一个文件
	 * @param path
	 * @param content
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean randomAccessFile(String path, String content) {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(path, "rw");
			raf.seek(raf.length());
			raf.write(content.getBytes());
			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public static void main(String[] args) {
		try {
			// FileUtil fileUtil = new FileUtil();
			// fileUtil.getList("D://201305", "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// for (int i = 0; i < 10; i++) {
		// FileUtil.randomAccessFile("d://work.txt", "你好" + i + "\n");
		// }
	}

	// public static void getList(String dirPath, String type) {
	// File myDir = new File(dirPath);
	// File[] contents = myDir.listFiles();
	// String batchid = "";
	// String filename = "";
	// SmsSendStatusServiceImpl smsSendStatusService = new
	// SmsSendStatusServiceImpl();
	//
	// for (File file : contents) {
	// batchid = FileUtil.readBatchid(file.getAbsolutePath());
	// filename = file.getName();
	// System.out.println(filename + batchid);
	// smsSendStatusService.groupSendStatusByFile(filename, batchid, type);
	// System.out.println(contents.clone() + "" + (contents.length - 1));
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// // FileUtil.deleteFile(filename);
	// }
	// }
}
