package com.chinadrtv.erp.uc.service;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Cases;
import com.chinadrtv.erp.model.agent.Cmpfback;
import com.chinadrtv.erp.model.agent.Conpointcr;

import java.util.*;
/**
 * API-UC-32.	查询服务历史
 *  
 * @author haoleitao
 * @date 2013-5-14 下午12:58:05
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface CasesService  extends GenericService<Cases,String>{
   /**
	* 用户交互历史查询,默认时间限制1年之内
	* 
	* <p>Title: </p>
	* <p>Description: </p>
	* @param contactId 联系人ID 
	* @param sdt 开始时间
	* @param edt 结束时间
	* @param index 第几页
	* @param size 每页多少条
	* @return 符合条件的结果集 
    */
    List<Cases> getAllCasesByContactId(String contactId,int index, int size);
    
   /**
	* 用户交互历史查询的数量,默认时间限制1年之内
	* 
	* <p>Title: </p>
	* <p>Description: </p>
	* @param contactId 联系人ID 
	* @param sdt 开始时间
	* @param edt 结束时间 
	* @return 符合条件数据的数量
	*/
    int getAllCasesByContactIdCount(String contactId);

    /**
     *是否协办的判断
     * @param caseId
     * @return
     */
    Cmpfback getCmpfbackByCaseId(String caseId);


}
