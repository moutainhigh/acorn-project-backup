package com.chinadrtv.erp.marketing.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.model.marketing.Customers;
import com.chinadrtv.erp.smsapi.model.SmsSendVar;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 短信发送管理_静态变量</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 短信发送管理_静态变量</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface SmsSendVarService {

	public void save(SmsSendVar smsVar);

	public List<SmsSendVar> query(String batchId);

	public List<Map<String, String>> getVarByContent(String sms);

	public List<String> getVarByObject(String smsContent, Customers customer);
}
