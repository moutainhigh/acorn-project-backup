/**
 * 
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.io.ObjectInputStream;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.marketing.constants.SmsConstant;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.dao.SmsTemplateDao;
import com.chinadrtv.erp.marketing.dto.SmsTemplateDto;
import com.chinadrtv.erp.marketing.service.SmsTemplateService;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dto.KeywordSeg;
import com.chinadrtv.erp.smsapi.dto.Result;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.ctc.ctcoss.webservice.service.IKeywordServiceProxy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
 * @since 2013-1-22 下午2:07:45
 * 
 */
@Service("smsTemplateService")
public class SmsTemplateServiceImpl implements SmsTemplateService {
	private static final Logger logger = LoggerFactory
			.getLogger(SmsTemplateServiceImpl.class);
	@Autowired
	private SmsTemplateDao smsTemplateDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.SmsTemplateService#loadDeptList(java
	 * .lang.Integer)
	 */
	public List<Map<String, Object>> loadDeptList(Integer uid) {
		return smsTemplateDao.loadDeptList(uid);
	}

	/**
	 * 模板分页查询 (non-Javadoc)
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsTemplateService#loadPageList(com
	 *      .chinadrtv.erp.marketing.dto.SmsTemplateDto,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public Map<String, Object> findPageList(SmsTemplateDto smsTemplateDto,
			DataGridModel dataGridModel) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SmsTemplateDto> list = smsTemplateDao.query(smsTemplateDto,
				dataGridModel);
		int count = smsTemplateDao.queryCount(smsTemplateDto);

		result.put("total", count);
		result.put("rows", list);
		return result;
	}

	/**
	 * 模板保存 (non-Javadoc)
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsTemplateService#save(com.chinadrtv
	 *      .erp.marketing.dto.SmsTemplateDto)
	 */
	public void saveSmsTemplate(SmsTemplate smsTemplate) throws Exception {
		smsTemplate.setIsdel(SmsConstant.IS_DEL_FALSE);
		smsTemplateDao.saveSmsTemplate(smsTemplate);
	}

	/**
	 * 生成客户群组编号
	 */
	public String getNextVal() {
		String nextVal = "T";
		Long val = 1000000000l;
		Long next = smsTemplateDao.getSeqNextValue();
		if (next < val) {
			return nextVal + (String.valueOf(val + next).substring(1));
		} else {
			return nextVal + next;
		}
	}

	/**
	 * 删除模板 (non-Javadoc)
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsTemplateService#deleteSmsTemplate
	 *      (java.lang.Long)
	 */
	@Transactional
	public void deleteSmsTemplate(String ids) throws Exception {
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			SmsTemplate st = smsTemplateDao.findById(Long.parseLong(id[i]));
			st.setIsdel(SmsConstant.IS_DEL_TRUE);
			smsTemplateDao.saveSmsTemplate(st);
		}
	}

	/**
	 * @return the smsTemplateDao
	 */
	public SmsTemplateDao getSmsTemplateDao() {
		return smsTemplateDao;
	}

	/**
	 * @param smsTemplateDao
	 *            the smsTemplateDao to set
	 */
	public void setSmsTemplateDao(SmsTemplateDao smsTemplateDao) {
		this.smsTemplateDao = smsTemplateDao;
	}

	/*
	 * (非 Javadoc) <p>Title: queryList</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsTemplateService#queryList()
	 */
	public List<SmsTemplate> queryList() {
		// TODO Auto-generated method stub
		return smsTemplateDao.querList();

	}

	public SmsTemplate getById(Long id) {
		return smsTemplateDao.findById(id);
	}

	/**
	 * 
	 * (非 Javadoc)
	 * <p>
	 * Title: templateFilter
	 * </p>
	 * <p>
	 * Description: 敏感词过滤
	 * </p>
	 * 
	 * @param template
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsTemplateService#templateFilter
	 *      (com.chinadrtv.erp.smsapi.model.SmsTemplate)
	 */
	public List<String> templateFilter(SmsTemplate template) {
		List<String> lists = new ArrayList<String>();
		List<KeywordSeg> list = new ArrayList<KeywordSeg>();
		try {
			if (Constants.result == null) {
				XStream xstream = new XStream(new DomDriver());
				xstream.autodetectAnnotations(true);
				xstream.alias("result", Result.class);
				try {
					String endpoint = PropertiesUtil.getWordFilterUrl();
					IKeywordServiceProxy ywsnp = new IKeywordServiceProxy();
					String xml = ywsnp.getIKeywordService().getKeywords("");
					ObjectInputStream in = null;
					xstream.addImplicitCollection(Result.class, "keywordSegs");
					StringReader reader = new StringReader(xml);
					Constants.result = (Result) xstream.fromXML(xml);
					logger.debug("定时同步更新敏感词" + xml);
					list = Constants.result.getKeywordSeg();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					logger.error("定时同步更新敏感词 失败" + e);
				}
			} else {
				list = Constants.result.getKeywordSeg();
			}

			for (int i = 0; i < list.size(); i++) {
				if (template.getContent().contains(
						Constants.result.getKeywordSeg().get(i).getKeyword())) {
					logger.info("template"
							+ template.getContent()
							+ "敏感词"
							+ Constants.result.getKeywordSeg().get(i)
									.getKeyword());
					lists.add(Constants.result.getKeywordSeg().get(i)
							.getKeyword());
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("敏感词过滤失败" + e);
			e.printStackTrace();
		}
		return lists;
	}

}
