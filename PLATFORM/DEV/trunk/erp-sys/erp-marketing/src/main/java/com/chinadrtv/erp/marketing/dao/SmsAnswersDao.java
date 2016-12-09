package com.chinadrtv.erp.marketing.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.dto.SmsAnswerDto;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsAnswer;
import com.chinadrtv.erp.smsapi.model.SmsBlackList;

/***
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
 * @since 2013-1-21 下午3:06:09
 * 
 */
public interface SmsAnswersDao extends GenericDao<SmsAnswer, java.lang.String> {

	public SmsAnswer getSmsAnswerById(Long id);

	public List<SmsAnswerDto> query(SmsAnswerDto smsAnswerDto,
			DataGridModel dataModel);

	public int deleteObject(SmsAnswer smsAnswer);

	public Integer queryCount(SmsAnswerDto smsAnswerDto);

	public void insertBlcakList(SmsBlackList smsBlackList);

	public List<SmssnedDto> query(SmssnedDto smsSend, DataGridModel dataModel);

	public Integer queryCounts(SmssnedDto smsSend);

}
