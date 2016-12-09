/*
 * @(#)SmsApiServiceImpl.java 1.0 2013-5-15下午5:30:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
import com.chinadrtv.erp.marketing.core.service.SmsApiService;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.OrderTypeTemplateRelationDao;
import com.chinadrtv.erp.smsapi.dao.SmsAnswerDao;
import com.chinadrtv.erp.smsapi.dao.SmsSendDao;
import com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao;
import com.chinadrtv.erp.smsapi.dao.SmsTemplatesDao;
import com.chinadrtv.erp.smsapi.model.OrderTypeTemplateRelation;
import com.chinadrtv.erp.smsapi.model.SmsAnswer;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;
import com.chinadrtv.erp.smsapi.service.SingleSmsSendService;
import com.chinadrtv.erp.smsapi.service.SmsTemplatesService;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.UUidUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-15 下午5:30:31
 * 
 */
@Service("smsApiService")
public class SmsApiServiceImpl implements SmsApiService {

	@Autowired
	SingleSmsSendService singleSmsSendService;
	private static final Logger logger = LoggerFactory
			.getLogger(SmsApiServiceImpl.class);
	@Autowired
	SmsTemplatesService smsTemplatesService;
	@Autowired
	private OrderTypeTemplateRelationDao orderTypeTemplateRelationDao;

	@Autowired
	private SmsTemplatesDao smsTemplatesDao;
	@Autowired
	private SmsSendDetailDao smsSendDetailDao;
	@Autowired
	private SmsAnswerDao smsAnswerDao;
	@Autowired
	private SmsSendDao smsSendDao;

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getSmsTemplateList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param department
	 *            部门
	 * 
	 * @param theme
	 *            主题
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.SmsApiService#getSmsTemplateList
	 *      (java.lang.String, java.lang.String)
	 */
	public List<SmsTemplate> getSmsTemplateList(String department, String theme)
			throws ServiceException {
		// TODO Auto-generated method stub
		List<com.chinadrtv.erp.smsapi.model.SmsTemplate> list = null;
		try {
			list = smsTemplatesService.getSmsTemplateList(department, theme);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询模板失败" + e);
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: singleSmsSend
	 * </p>
	 * <p>
	 * Description:单条短信发送
	 * </p>
	 * 
	 * @param smsSendDto
	 * 
	 * @return
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.SmsApiService#singleSmsSend(com.chinadrtv.erp.marketing.core.dto.SmsSendDto)
	 */
	public String singleSmsSend(SmsSendDto smsSend) throws ServiceException {
		// TODO Auto-generated method stub
		// 生成uuid
		if (StringUtil.isNullOrBank(smsSend.getDepartment())
				|| StringUtil.isNullOrBank(smsSend.getMobile())
				|| StringUtil.isNullOrBank(smsSend.getSmsContent())) {
			logger.error("必要参数缺失");
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "必要参数缺失");
		}
		String uuid = UUidUtil.getUUid();
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		List list = new ArrayList();
		logger.info("uuid=" + uuid + "=creator=" + smsSend.getCreator()
				+ "=customerId=" + smsSend.getCustomerId() + "=department="
				+ smsSend.getDepartment() + "=mobile=" + smsSend.getMobile()
				+ "=smsContent=" + smsSend.getSmsContent() + "=source="
				+ smsSend.getSource());
		try {
			singleSmsSendService.singleSend("Y", smsSend.getStartTime(),
					smsSend.getEndTime(), listmap, 9l, "N", "Y", "橡果国际",
					smsSend.getCreator(), smsSend.getSmsContent(), list,
					smsSend.getSource(), smsSend.getDepartment(),
					smsSend.getMobile(), smsSend.getCustomerId(),
					DateTimeUtil.sim3.format(new Date()), uuid,
					smsSend.getOrderType(), smsSend.getTemplateTheme(),
					smsSend.getSmsName());
			return uuid;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("短信发送失败" + e);
			throw new ServiceException(
					ExceptionConstant.SERVICE_SMS_SEND_EXCEPTION, e);
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getSmsByUuid
	 * </p>
	 * <p>
	 * Description:根据uuid查询短信 信息
	 * </p>
	 * 
	 * @param uuid
	 *            短信唯一标示
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.SmsApiService#getSmsByUuid(java
	 *      .lang.String)
	 */
	public SmsSendDto getSmsByUuid(String uuid) throws ServiceException {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrBank(uuid)) {
			logger.error("uuid:" + uuid + "为空");
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "uuid为空");
		}
		SmsSendDto result = new SmsSendDto();
		SmsSendDetail sendDetail = null;
		SmsSend smsSend = null;
		try {
			sendDetail = singleSmsSendService.getByUuid(uuid);
			smsSend = smsSendDao.getByUuid(uuid);
			if (sendDetail.getReceiveStatus() != null
					&& !("10").equals(sendDetail.getReceiveStatus())) {
				if (sendDetail.getReceiveStatus().equals("1")) {
					result.setSmsStatus("提交成功");
				} else {
					result.setSmsStatus("提交失败");
					result.setSmsErrorCode("0");
				}
			}
			if (sendDetail.getFeedbackStatus() != null
					&& !("10").equals(sendDetail.getFeedbackStatus())) {
				if (sendDetail.getFeedbackStatus().equals("1")) {
					result.setSmsStatus("发送成功");
				} else {
					result.setSmsStatus("发送失败");
					result.setSmsErrorCode("0");
				}
			}
			if (("10").equals(sendDetail.getSmsStopStatus())
					&& ("10").equals(sendDetail.getFeedbackStatus())
					&& ("10").equals(sendDetail.getReceiveStatus())) {
				result.setSmsStatus("发送中");
			}
			result.setSmsContent(sendDetail.getContent());
			result.setMobile(sendDetail.getMobile());
			result.setSmsName(smsSend.getSmsName());
			// result.setTemplateTheme(smsSend.getTemplateTheme());
			return result;
		} catch (Exception e) {
			logger.error("uuid:" + uuid + "不存在" + e);
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, e);
		}
	}

	/**
	 * @throws ServiceException
	 *             *
	 * 
	 * @Description: 根据部门 和主题查模板 easyui 分页
	 * @param department
	 *            部门
	 * @param theme
	 *            主题
	 * @return
	 * @return List<SmsTemplate>
	 * @throws
	 */
	public Map<String, Object> findSmsTemplatePageList(String department,
			String theme, DataGridModel dataGridModel) throws ServiceException {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = smsTemplatesService.findSmsTemplatePageList(department,
					theme, dataGridModel);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询模板失败" + e);
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: SmsSendByOrderType
	 * </p>
	 * <p>
	 * Description:根据订单类型发送短信
	 * </p>
	 * 
	 * @param orderType
	 * @return
	 * @throws ServiceException
	 * @see com.chinadrtv.erp.marketing.core.service.SmsApiService#SmsSendByOrderType(java.lang.String)
	 */
	public String SmsSendByOrderType(SmsSendDto smsSendDto)
			throws ServiceException {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrBank(smsSendDto.getOrderType())) {
			logger.error("订单类型为空");
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "订单类型为空");
		}
		OrderTypeTemplateRelation orderTypeTemplateRelation = orderTypeTemplateRelationDao
				.getTemplateByOrderType(smsSendDto.getOrderType());
		if (orderTypeTemplateRelation == null) {
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION,
					"没有查询到数据");
		}
		if (orderTypeTemplateRelation.getSmsTemplate() == null) {
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION,
					"没有查询到模板");
		}
		smsSendDto.setSmsContent(getVarByObject(orderTypeTemplateRelation
				.getSmsTemplate().getContent(), smsSendDto));
		try {
			// 延时检验
			if (!StringUtil.isNullOrBank(orderTypeTemplateRelation
					.getDelayFlag())
					&& ("1").equals(orderTypeTemplateRelation.getDelayFlag()
							.trim())) {
				if (!StringUtil.isNullOrBank(orderTypeTemplateRelation
						.getDelayTime())) {
					Date date = new Date();
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.MINUTE, Integer
							.valueOf(orderTypeTemplateRelation.getDelayTime()));
					smsSendDto.setStartTime(DateTimeUtil.sim3.format(c
							.getTime()));
					smsSendDto
							.setEndTime(DateTimeUtil.sim3.format(c.getTime()));
				}
			}
		} catch (Exception e) {
			logger.error("延时时间设置异常");
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}
		// 调用发送短信接口
		String uuid = singleSmsSend(smsSendDto);
		logger.info("生成uuid：" + uuid);
		return uuid;
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
	public String getVarByObject(String smsContent, SmsSendDto smsSendDto) {
		List<String> varUserList = StringUtil.getVar(smsContent);
		String varUser = "";
		for (int i = 0; i < varUserList.size(); i++) {
			varUser = varUserList.get(i).replace("{", "").replace("}", "");
			varUser = varUser.substring(varUser.indexOf(".") + 1,
					varUser.length());
			try {
				smsContent = smsContent.replace("{sms." + varUser + "}",
						BeanUtils.getProperty(smsSendDto, varUser));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return smsContent.trim();
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getCheckCode
	 * </p>
	 * <p>
	 * Description: 查询验证码
	 * </p>
	 * 
	 * @return
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.SmsApiService#getCheckCode()
	 */
	public String getCheckCode(String orderId) throws ServiceException {
		// TODO Auto-generated method stub
		String checkcode = "";
		try {
			checkcode = "" + orderTypeTemplateRelationDao.getchechCode();
			checkcode = checkcode.substring(0, 6);
			orderTypeTemplateRelationDao.insertCode(checkcode, orderId);
			return checkcode;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("生成验证码错误");
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getById
	 * </p>
	 * <p>
	 * Description:根据模板id查询
	 * </p>
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.SmsApiService#getById(java.lang
	 *      .String)
	 */
	@Override
	public SmsTemplate getSmsTemplateById(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrBank("id")) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "短信模板为空");
		}
		try {
			// return smsTemplatesDao.queryByTemplateId(id);
			return smsTemplatesDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: findSmsSendDetailPageList
	 * </p>
	 * <p>
	 * Description:根据contactId easyui 分页
	 * </p>
	 * 
	 * @param contactId
	 * 
	 * @param dataGridModel
	 * 
	 * @return
	 * 
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.SmsApiService#
	 *      findSmsSendDetailPageList(java.lang.String,
	 *      com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */
	public Map<String, Object> findSmsSendDetailPageList(String contactId,
			DataGridModel dataGridModel) throws ServiceException {
		// TODO Auto-generated method stub
            if (StringUtil.isNullOrBank(contactId)) {
                throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "联系人为空");
		}
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			List<SmsSendDetail> list = smsSendDetailDao
					.getSmsSendDetailsByContactId(contactId, dataGridModel);
			Long count = smsSendDetailDao.getCountsByContactId(contactId);
			result.put("total", count);
			result.put("rows", list);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: findSmsAnswerPageList
	 * </p>
	 * <p>
	 * Description:根据 easyui 分页
	 * </p>
	 * 
	 * @param mobile
	 * 
	 * @param dataGridModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.SmsApiService#findSmsAnswerPageList
	 *      (java.lang.String, com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */
	@Override
	public Map<String, Object> findSmsAnswerPageList(String mobile,
			DataGridModel dataGridModel) throws ServiceException {
		if (StringUtil.isNullOrBank(mobile)) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "手机号码为空");
		}
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			/*List<SmsAnswer> list = smsAnswerDao.getSmsAnswerByMobile(mobile,
					dataGridModel);

			Long count = smsAnswerDao.getCountsByMobile(mobile);
			result.put("total", count);
			result.put("rows", list);*/
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}
	}
}
