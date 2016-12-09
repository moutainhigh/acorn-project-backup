package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.AreaGroupDetail;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.AreaGroupDetailDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-20
 * Time: 下午2:06
 * To change this template use File | Settings | File Templates.
 */
public interface AreaGroupDetailDao extends GenericDao<AreaGroupDetail,Long> {

    //分页显示地址组明细
    public List<AreaGroupDetailDto> getAreaGroupDetail(Long areaGroupId,DataGridModel dataModel);
    //数据导入
    public void saveAreaGroupDetail(AreaGroupDetail areaGroupDetail);
    //获取信息总条数
    public Integer queryCount(Long aId);
    //导出地址组明细
    public List getAreaGroupDetail(Long areaGroupId);
    //删除明细
    public boolean deleteByAreaGroupId(Long areId);

    public List<AreaGroupDetail> getDetailListByGroupId(Long areaGroupId);
}
