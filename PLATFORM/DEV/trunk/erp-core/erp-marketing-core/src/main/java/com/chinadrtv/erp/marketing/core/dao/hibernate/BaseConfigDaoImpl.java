package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dao.BaseConfigDao;
import com.chinadrtv.erp.model.marketing.BaseConfig;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 基本参数配置表DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 基本参数配置表DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:19:43
 * 
 */
@Repository
public class BaseConfigDaoImpl extends
		GenericDaoHibernate<BaseConfig, java.lang.Long> implements
		BaseConfigDao {

	public BaseConfigDaoImpl() {
		super(BaseConfig.class);
	}

	public List<BaseConfig> query(String code) {
		StringBuffer sql = new StringBuffer(
				"from BaseConfig where code=:code order by postion");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter codePara = new ParameterString("code", code);
		params.put("code", codePara);

		List<BaseConfig> objList = findList(sql.toString(), params);
		return objList;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: queryByValue
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param value
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.BaseConfigDao#queryByValue(java.
	 *      lang.String)
	 */
	public List<BaseConfig> queryByValue(String value) {
		StringBuffer sql = new StringBuffer(
				"from BaseConfig where paraValue=:paraValue order by postion");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter codePara = new ParameterString("paraValue", value);
		params.put("paraValue", codePara);

		List<BaseConfig> objList = findList(sql.toString(), params);
		return objList;
	}

}
