package com.chinadrtv.erp.marketing.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.dto.SmsAnswerDto;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsBlackList;
import com.chinadrtv.erp.smsapi.model.SmsSend;

/****
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-1-18 下午5:28:05
 * 
 */
public interface SmsAnswersService {
	/***
	 * 逻辑删除
	 * 
	 * @Description: TODO
	 * @param id
	 * @return void
	 * @throws
	 */
	void removeSmsAnswer(Long id);

	/***
	 * 插入黑名单
	 * 
	 * @Description: TODO
	 * @param id
	 * @return void
	 * @throws
	 */
	public int insertBlack(Long id, String userid);

	/**
	 * 获得所有黑名单
	 * 
	 * @Description: TODO
	 * @return
	 * @return List<SmsBlackList>
	 * @throws
	 */
	public List<SmsBlackList> queryList();

	/***
	 * 返回短信回复数据
	 * 
	 * @Description: TODO
	 * @param smsAnswerDto
	 * @param dataModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getSmsAnswer(SmsAnswerDto smsAnswerDto,
			DataGridModel dataModel);

	public List<Map<String, Object>> getPhoneList(String groupCode,
			String flag, String blackflag, Integer begin, Integer end);

	public boolean insertBatchList(List<Map<String, Object>> list,
			String batchId, String department, String creator, String template);

	public Map mapList(String phoneFlag, String blackFlag, String customers);

	public Map<String, Object> getSmsSend(SmssnedDto smsAnswerDto,
			DataGridModel dataModel);

	public Long getCounts(String batchid, String status);

	public List<Map<String, Object>> getTimeList(String batchid, String status);

	public Long getTimeCount(String batchid, String date, String statusTime);

	public SmsSend getById(String batchid);

	public Boolean saveByBatchId(String userId, SmssnedDto smssnedDto,
			String department);

	public Boolean newSmsSend(String batchid);

	public List queryContactidByPhone(String phone);

	public List<Map<String, String>> getTimeScopeList(String starttime,
			String endTime, String maxSend);

	public Map<String, Object> getSmsSendByCampaignId(Long campaignId,
			DataGridModel dataModel);

	public Map mapListForCampaign(String phoneFlag, String blackFlag,
			String customers, Long campaignId);

}
