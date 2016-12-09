package com.chinadrtv.erp.tc.core.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.SqlDao;
import com.chinadrtv.erp.tc.core.service.PvService;

/**
 * 
 * <dl>
 *    <dt><b>Title:积分服务Service</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author TaoYaWei
 * @version 1.0
 * @since 2013-3-29 上午10:25:50 
 * Updated by WangJian on March 29, 2013
 *
 */
@Service
public class PvServiceImpl extends GenericServiceImpl<OrderDetail, String> implements PvService {
	
	private static final Logger logger = LoggerFactory.getLogger(PvServiceImpl.class);
	
	@Autowired
	private SchemaNames schemaNames;
	@Autowired
	private SqlDao sqlDao;

	protected GenericDao<OrderDetail, String> getGenericDao() {
		return null;
	}

	/*
	 * 校正积分
	* <p>Title: 校正积分</p>
	* <p>Description: </p>
	* @param params
	* @throws SQLException
	* @see com.chinadrtv.erp.tc.service.PvService#getJifenproc(java.util.Map)
	 */
	public void getJifenproc(Map<String, Object> params) throws SQLException {
		
		/*List<ParameterString> parmList = new ArrayList<ParameterString>();
		
		parmList.add(new ParameterString("sorderid", mapval.get("sorderid").toString()));
		parmList.add(new ParameterString("scrusr", mapval.get("scrusr").toString()));
		
		jifenReviseDao.callProcedure("call " + schemaNames.getAgentSchema() + "P_N_Conpointfeedback(:sorderid,:scrusr)", parmList);
		
		logger.debug("Call " + schemaNames.getAgentSchema() + "P_N_Conpointfeedback", mapval.get("scrusr").toString());*/
		
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(params.get("sorderid"));
		paramList.add(params.get("scrusr"));
		
		sqlDao.exeStoredProcedure(schemaNames.getAgentSchema() + "P_N_Conpointfeedback", paramList, 0);
		
	}

    public void getMemberType(Map<String, Object> params) throws SQLException{

        List<Object> paramList = new ArrayList<Object>();

        paramList.add(params.get("sorderid"));
        paramList.add(params.get("scontactid"));
        paramList.add(params.get("sdnis"));

        sqlDao.exeStoredProcedure(schemaNames.getAgentSchema() + "p_updatemembertype", paramList, 0);

    }

	/*
	 * 下单时新增积分
	* <p>Title: addPvByOrder</p>
	* <p>Description: </p>
	* @param params
	* @return
	* @see com.chinadrtv.erp.tc.service.PvService#addPvByOrder(java.util.Map)
	 */
	public String addPvByOrder(Map<String, Object> params) {
     /*   Connection conn=null;
        String getstr="";
        conn =getSession().connection();
        CallableStatement proc =conn.prepareCall("{call "+ schemaNames.getAgentSchema()+"P_N_USECONPOINT(?,?,?,?,?,?)}");
        proc.setString(1,mapval.get("stype").toString());
        proc.setString(2,mapval.get("sorderid").toString());
        proc.setString(3,mapval.get("scontactid").toString());
        proc.setDouble(4,Double.valueOf(mapval.get("npoint").toString()));
        proc.setString(5,mapval.get("scrusr").toString());
        proc.registerOutParameter(6, Types.NVARCHAR);
        proc.execute();
        if(proc.getObject(6)!=null){
            getstr=proc.getString(6);
           }
            if(conn!=null){
               conn.close();
            }
        return getstr;*/
		
		String result = "";
		
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(params.get("stype"));
		paramList.add(params.get("sorderid"));
		paramList.add(params.get("scontactid"));
		paramList.add(params.get("npoint"));
		paramList.add(params.get("scrusr"));
		
		List<Object> rsList = null;
		try {
			rsList = sqlDao.exeStoredProcedure(schemaNames.getAgentSchema() + "P_N_USECONPOINT", paramList, 1);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("订单新增调用积分服务失败:", e);
            throw new  RuntimeException(e.getMessage());
		}
		if(null!= rsList && rsList.size()>0){
			result = rsList.get(0).toString();
		}
		
		return result;
	}
}