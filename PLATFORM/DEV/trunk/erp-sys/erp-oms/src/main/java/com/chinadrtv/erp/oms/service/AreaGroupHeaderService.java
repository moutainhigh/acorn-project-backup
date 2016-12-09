package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.AddressDto;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-15
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 */
public interface AreaGroupHeaderService {

    //分页查询
    public Map<String, Object> getAddressDtoList(AddressDto addressDto,DataGridModel dataModel);
    //分页显示地址组明细
    public Map<String, Object> getAreaGroupDetailDtoList(Long areaId,DataGridModel dataModel);
    //获取渠道
    public List<OrderChannel> getAllChannel();
    //修改状态
    public void updateIsActive(Long id,String status);
    //新增保存
    public void saveAreGroupHeader(String apName,String apDesc,Long channelId);
    //处理上传文件
    public HSSFWorkbook upload(InputStream is) throws Exception;
    //地址组明细导出
    public HSSFWorkbook getAreaGroupDetailDtoForDownload(Long areId);
}
