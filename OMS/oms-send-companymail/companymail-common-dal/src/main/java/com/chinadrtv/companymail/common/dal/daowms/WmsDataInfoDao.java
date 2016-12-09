package com.chinadrtv.companymail.common.dal.daowms;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.companymail.common.dal.model.ShippingLoad;
import com.chinadrtv.companymail.common.dal.model.ZMMRPTEMSMailList;
import com.chinadrtv.companymail.common.dal.model.ZMMRPTReceivableslist;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-15
 * Time: 下午5:26
 * To change this template use File | Settings | File Templates.
 */
public interface WmsDataInfoDao extends BaseDao<ShippingLoad> {
    //获取符合条件的wms承运商信息
    List<ShippingLoad> findShippingLoad();

    //修改状态属性
    int updateShippingLoad(int internalLoadNum);

    //邮件54Excel数据
    List<ZMMRPTReceivableslist> findZMMRPTReceivableslist(int internalLoadNum);

    //邮件R56Excel数据
    List<ZMMRPTEMSMailList> findZMMRPTEMSMailList(int internalLoadNum);

}
