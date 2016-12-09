package com.chinadrtv.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SerialNumberBuilder {
	private static int maxNo = 9999999;
	private static int no = 0;

	/**
	 * 生成系统流水号
	 */
	public synchronized static String buildJournalSeqNo() {
		SimpleDateFormat fmt = new SimpleDateFormat();
		fmt.applyPattern("mmss");
		Date now = new Date();
		fmt.applyPattern("yyyyMMddHHmmss");
		StringBuffer sb = new StringBuffer();
		sb.append(fmt.format(now));
		String n = getNo() + "";
		int nSize = n.length();
		if (nSize < 7) {
			for (int i = 0; i < 7 - nSize; i++) {
				sb.append("0");
			}
		}
		sb.append(n);
		return sb.toString();

	}

	public synchronized static String buildSeqNo(String s) {

		SimpleDateFormat fmt = new SimpleDateFormat();
		Date now = new Date();
		fmt.applyPattern("yyyyMMddHHmmss");
		StringBuffer sb = new StringBuffer(s);
		sb.append(fmt.format(now));
		String n = getNo() + "";
		int nSize = n.length();
		if (nSize < 7) {
			for (int i = 0; i < 7 - nSize; i++) {
				sb.append("0");
			}
		}
		sb.append(n);
		return sb.toString();

	}

	public static int getNo() {
		if (no >= maxNo) {
			no = 0;
		}
		no++;
		return no;
	}

}
