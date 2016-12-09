package com.chinadrtv.erp.marketing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.dao.SmsSendVarDao;
import com.chinadrtv.erp.marketing.service.SmsSendVarService;
import com.chinadrtv.erp.marketing.util.StringUtil;
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
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("smsSendVarService")
public class SmsSendVarServiceImpl implements SmsSendVarService {

	@Autowired
	private SmsSendVarDao smsSendVarDao;

	/**
	 * <p>
	 * Title: save
	 * </p>
	 * <p>
	 * Description: 保存某批次的静态变量
	 * </p>
	 * 
	 * @param smsVar
	 * @see com.chinadrtv.erp.marketing.service.SmsSendVarService#save(com.chinadrtv.erp.marketing.model.SmsSendVar)
	 */
	public void save(SmsSendVar smsVar) {
		smsSendVarDao.save(smsVar);

	}

	/**
	 * <p>
	 * Title: query
	 * </p>
	 * <p>
	 * Description:根据batchid查询静态变量
	 * </p>
	 * 
	 * @param batchId
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.SmsSendVarService#query(java.lang.String)
	 */
	public List<SmsSendVar> query(String batchId) {
		return smsSendVarDao.query(batchId);
	}

	/**
	 * <p>
	 * Title: getVarByContent
	 * </p>
	 * <p>
	 * Description: 根据短信模板内容解析其中的静态变量
	 * </p>
	 * 
	 * @param sms
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.SmsSendVarService#getVarByContent(java.lang.String)
	 */
	public List<Map<String, String>> getVarByContent(String smsContent) {

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> obj = null;
		List<String> list = StringUtil.getVar(smsContent);
		for (String var : list) {
			if (var.indexOf(".") < 0) {
				obj = new HashMap<String, String>();
				obj.put("name", var);
				obj.put("value", "");

				result.add(obj);
			}
		}
		return result;
	}

	/**
	 * 通过beanuntils 获取 动态属性
	 * 
	 * @Description: TODO
	 * @param smsContent
	 * @param customer
	 * @return
	 * @return List<String>
	 * @throws
	 */
	public List<String> getVarByObject(String smsContent, Customers customer) {
		List<String> varUserList = StringUtil.getVar(smsContent);
		String varUser = "";
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < varUserList.size(); i++) {
			varUser = varUserList.get(i).replace("{", "").replace("}", "");
			varUser = varUser.substring(varUser.indexOf(".") + 1,
					varUser.length());
			try {
				list.add(BeanUtils.getProperty(customer, varUser));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
