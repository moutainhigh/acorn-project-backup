/**
 * 
 */
package com.chinadrtv.erp.marketing.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.dto.SmsTemplateDto;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;

/**
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
 * @author andrew
 * @version 1.0
 * @since 2013-1-22 下午2:06:45
 * 
 */
public interface SmsTemplateDao extends GenericDao<SmsTemplate, java.lang.Long> {

	/**
	 * @param uid
	 * @return
	 */
	List<Map<String, Object>> loadDeptList(Integer uid);

	/**
	 * 分页查询
	 * 
	 * @param smsTemplateDto
	 * @param dataGridModel
	 * @return
	 */
	List<SmsTemplateDto> query(SmsTemplateDto smsTemplateDto,
			DataGridModel dataGridModel);

	/**
	 * 分页查询记录数
	 * 
	 * @param smsTemplateDto
	 * @return
	 */
	int queryCount(SmsTemplateDto smsTemplateDto);

	/**
	 * 保存
	 * 
	 * @param smsTemplateDto
	 */
	void saveSmsTemplate(SmsTemplate smsTemplate) throws Exception;

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 */
	SmsTemplate findById(Long id);

	/***
	 * 查出所有模板
	 * 
	 * @Description: TODO
	 * @return
	 * @return List<SmsTemplate>
	 * @throws
	 */
	List<SmsTemplate> querList();

	/**
	 * 取得序列号
	 * 
	 * @Description: TODO
	 * @return
	 * @return Long
	 * @throws
	 */
	public Long getSeqNextValue();

}
