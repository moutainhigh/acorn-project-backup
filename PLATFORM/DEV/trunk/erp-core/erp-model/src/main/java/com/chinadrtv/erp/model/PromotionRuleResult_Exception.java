/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.chinadrtv.erp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Shipping at free/discount
 * 
 * @author richard.mao
 *
 */

@Entity
@DiscriminatorValue("Exception")
public class PromotionRuleResult_Exception extends PromotionRuleResult implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1519461678990196951L;

    private String errCode;
    private String errMsg;

	public PromotionRuleResult_Exception(){

	}

	/**
	 *
	 * @param resultDescription
	 * @param errCode
	 * @param errMsg if the discount is percentage discount
	 */
	public PromotionRuleResult_Exception(String resultDescription, String errCode, String errMsg){
		super(resultDescription);
		this.errCode=errCode;
		this.errMsg=errMsg;
	}

	@Override
	public boolean canConsume() {
    	return false;
    }

    @Column(name = "ERR_CODE")
    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    @Column(name = "ERR_MSG")
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
	
	public String toString(){
		StringBuffer buff=new StringBuffer();
		buff.append(super.toString());
		buff.append(",errCode:").append(errCode);
		buff.append(",errMsg:").append(errMsg);
		return buff.toString();
	}

}
