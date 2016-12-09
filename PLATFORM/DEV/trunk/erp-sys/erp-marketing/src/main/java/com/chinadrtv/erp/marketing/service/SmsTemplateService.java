/**
 * 
 */
package com.chinadrtv.erp.marketing.service;

import java.util.List;
import java.util.Map;

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
 * @since 2013-1-22 下午2:07:36
 * 
 */
public interface SmsTemplateService {

	/**
	 * @param uid
	 * @return
	 */
	@Deprecated
	List<Map<String, Object>> loadDeptList(Integer uid);

	/**
	 * 分页查询
	 * 
	 * @param smsTemplateDto
	 * @param dataGridModel
	 * @return
	 */
	Map<String, Object> findPageList(SmsTemplateDto smsTemplateDto,
			DataGridModel dataGridModel);

	/**
	 * 保存
	 * 
	 * @param smsTemplateDto
	 */
	void saveSmsTemplate(SmsTemplate smsTemplate) throws Exception;

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void deleteSmsTemplate(String id) throws Exception;

	/**
	 * 查出所有模板
	 */
	public List<SmsTemplate> queryList();

	public SmsTemplate getById(Long id);

	public String getNextVal();

	/**
	 * 敏感词过滤
	 * 
	 * @Description: TODO
	 * @param template
	 * @return void
	 * @throws
	 */
	public List<String> templateFilter(SmsTemplate template);

}
