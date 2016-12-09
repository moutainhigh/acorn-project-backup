/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.dto;

import com.chinadrtv.erp.model.agent.Grp;

/**
 * 2013-9-6 上午10:08:44
 * @version 1.0.0
 * @author yangfei
 *
 */
public class GrpTaskNumExt extends Grp{
	private static final long serialVersionUID = 1L;
	private int overplusNum = 0;
	private int obtainQty4use = 0;
	private int obtainQty = 0;

	public int getOverplusNum() {
		return overplusNum;
	}

	public void setOverplusNum(int overplusNum) {
		this.overplusNum = overplusNum;
	}

	public int getObtainQty4use() {
		return obtainQty4use;
	}

	public void setObtainQty4use(int obtainQty4use) {
		this.obtainQty4use = obtainQty4use;
	}

	public int getObtainQty() {
		return obtainQty;
	}

	public void setObtainQty(int obtainQty) {
		this.obtainQty = obtainQty;
	}
}
