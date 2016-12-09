/*
 * @(#)BloomFilter.java 1.0 2013-3-8下午4:48:03
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.smsapi.model.SmsBlackList;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-3-8 下午4:48:03
 * 
 */
public class BloomFilter {
	private int defaultSize = 2 << 24;
	private int basic = defaultSize - 1;
	private BitSet bits;

	public BloomFilter() {
		bits = new BitSet(defaultSize);
	}

	public boolean contains(String mobile) {
		if (mobile == null) {
			return true;
		}
		int pos1 = hash1(mobile);
		int pos2 = hash2(mobile);
		int pos3 = hash3(mobile);
		if (bits.get(pos1) && bits.get(pos2) && bits.get(pos3)) {
			return true;
		}
		return false;
	}

	public boolean contains2(String mobile) {
		if (mobile == null) {
			return true;
		}
		int pos1 = hash1(mobile);
		int pos2 = hash2(mobile);
		int pos3 = hash3(mobile);
		if (bits.get(pos1) && bits.get(pos2) && bits.get(pos3)) {
			return true;
		}
		bits.set(pos1);
		bits.set(pos2);
		bits.set(pos3);
		return false;
	}

	public void add(String mobile) {
		if (mobile == null) {
			return;
		}
		int pos1 = hash1(mobile);
		int pos2 = hash2(mobile);
		int pos3 = hash3(mobile);
		bits.set(pos1);
		bits.set(pos2);
		bits.set(pos3);
	}

	private int hash3(String line) {
		int h = 0;
		int len = line.length();
		for (int i = 0; i < len; i++) {
			h = 37 * h + line.charAt(i);
		}
		return check(h);
	}

	private int hash2(String line) {
		int h = 0;
		int len = line.length();
		for (int i = 0; i < len; i++) {
			h = 33 * h + line.charAt(i);
		}
		return check(h);
	}

	private int hash1(String line) {
		int h = 0;
		int len = line.length();
		for (int i = 0; i < len; i++) {
			h = 31 * h + line.charAt(i);
		}
		return check(h);
	}

	private int check(int h) {
		return basic & h;
	}

	/***
	 * list1 为黑名单 list2 为要过滤的黑名单
	 * 
	 * @Description: TODO
	 * @param list1
	 * @param list2
	 * @return
	 * @return List
	 * @throws
	 */
	public static List<Map<String, Object>> bloomFilters(
			List<SmsBlackList> list1, List<Map<String, Object>> list2,
			String key) {
		BloomFilter bloomFilter = new BloomFilter();
		for (int i = 0; i < list1.size(); i++) {
			bloomFilter.add(list1.get(i).getPhoneNum());
		}
		Iterator<Map<String, Object>> iter = list2.iterator();
		while (iter.hasNext()) {
			String s = iter.next().get(key).toString();
			if (bloomFilter.contains(s) == true) {
				iter.remove();
			}
		}
		return list2;
	}

	public static void main(String[] args) {
		String mobile = "13918389281";
		String mobile1 = "13918389282";
		String mobile2 = "13918389283";
		String mobile3 = "13918389284";
		String mobile4 = "13918389285";
		String mobile5 = "13918389286";
		String mobile6 = "13918389287";
		String mobile7 = "13918389288";
		List list = new ArrayList();
		list.add(mobile);
		list.add(mobile1);
		list.add(mobile2);
		list.add(mobile3);
		list.add(mobile4);

		List list2 = new ArrayList();
		list2.add(mobile1);
		list2.add(mobile2);
		list2.add(mobile7);
		list2.add(mobile6);
		// List list3 = BloomFilter.bloomFilters(list, list2);
		// for (int i = 0; i < list3.size(); i++) {
		// System.out.println(list3.get(i).toString());
		// }
	}
}
