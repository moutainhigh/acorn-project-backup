package com.chinadrtv.erp.marketing.service;


import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.marketing.model.bi.IdlCrmPhoneRank;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 电话号码清晰同步-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 电话号码清晰同步-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface IdlCrmPhoneRankService {
	/**
	 * 
	* @Description: 查询未同步列表
	* @param startNm
	* @param endNum
	* @return
	* @return List<IdlCrmPhoneRank>
	* @throws
	 */
	public List<IdlCrmPhoneRank> queryListByJdbc(Integer startNm,Integer endNum);
	
	/**
	 * 
	* @Description: 更新同步结果
	* @param phoneid
	* @param phoneRank
	* @param lastDate
	* @param newFlag
	* @param origFlag
	* @return
	* @return int
	* @throws
	 */
	public int updateSyncFlag(String phoneid,String phoneRank,Date lastDate,String newFlag,String origFlag);
}
