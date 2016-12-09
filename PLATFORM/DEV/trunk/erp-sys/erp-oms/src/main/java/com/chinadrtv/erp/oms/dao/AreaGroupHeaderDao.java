package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.AddressDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-14
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 * 地址组维护
 */
public interface AreaGroupHeaderDao extends GenericDao<AreaGroup,Long> {

    //获取数据
    List<AddressDto> getAddressDtoList(AddressDto addressDto,DataGridModel dataModel);
    //获取信息总条数
    Integer queryCount(AddressDto addressDto);
    //修改启用或停用状态
    void updateAreaGroupHeader(Long id,String status);
    //新增
    void saveAreaGroupHeader(AreaGroup areaGroupHeader);
    //判断areaGroupId是否存在
    AreaGroup getIsAreaGroup(Long areaGroupId);
}
