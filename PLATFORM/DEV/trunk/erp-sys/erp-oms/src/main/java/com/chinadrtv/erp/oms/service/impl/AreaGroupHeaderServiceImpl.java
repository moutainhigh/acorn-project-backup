package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.model.AreaGroupDetail;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dao.AreaGroupDetailDao;
import com.chinadrtv.erp.oms.dao.AreaGroupHeaderDao;
import com.chinadrtv.erp.oms.dao.ChannelDao;
import com.chinadrtv.erp.oms.dto.AddressDto;
import com.chinadrtv.erp.oms.dto.AreaGroupDetailDto;
import com.chinadrtv.erp.oms.service.AreaGroupHeaderService;

import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.IntegerSyntax;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-15
 * Time: 下午12:20
 * To change this template use File | Settings | File Templates.
 * 地址组维护
 */
@Service("areaGroupHeaderService")
public class AreaGroupHeaderServiceImpl implements AreaGroupHeaderService {
    @Autowired
    private AreaGroupHeaderDao areaGroupHeaderDao;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private AreaGroupDetailDao areaGroupDetailDao;


    /**
     * 分页查询
     * @param addressDto
     * @param dataModel
     * @return
     */
    public Map<String, Object> getAddressDtoList(AddressDto addressDto,DataGridModel dataModel)
    {
        Map<String,Object> result = new HashMap<String, Object>();
        List<AddressDto> list = areaGroupHeaderDao.getAddressDtoList(addressDto,dataModel);
        int count = areaGroupHeaderDao.queryCount(addressDto);

        result.put("total", count);
        result.put("rows", list);

        return result;
    }
    //分页显示地址组明细
    public Map<String, Object> getAreaGroupDetailDtoList(Long areaId,DataGridModel dataModel)
    {
        Map<String,Object> result = new HashMap<String, Object>();
        List<AreaGroupDetailDto> list = areaGroupDetailDao.getAreaGroupDetail(areaId,dataModel);
        int count = areaGroupDetailDao.queryCount(areaId);

        result.put("total", count);
        result.put("rows", list);

        return result;
    }
    /**
     * 获取渠道
     * @return
     */
    public List<OrderChannel> getAllChannel() {
        return channelDao.getAllChannel();
    }

    /**
     * 更改停用或启用状态
     * @param id
     * @param status
     */
    public void updateIsActive(Long id, String status) {
        this.areaGroupHeaderDao.updateAreaGroupHeader(id,status);
    }

    /**
     * 新增保存
     * @param apName
     * @param apDesc
     * @param channelId
     */
    public void saveAreGroupHeader(String apName, String apDesc, Long channelId) {
        OrderChannel ch = channelDao.getChannelByChannelId(channelId);
        AreaGroup ap = new AreaGroup();
        String name = SecurityHelper.getLoginUser().getName();     //创建人
        ap.setName(apName);
        ap.setOrderChannel(ch);
        ap.setNote(apDesc);
        ap.setActive(true);
        ap.setCrUser(name);
        ap.setCrDT(new Date());
        areaGroupHeaderDao.saveAreaGroupHeader(ap);
    }

    /**
     * 处理上传Excel文件
     * @return
     * @throws Exception
     */
    public HSSFWorkbook upload(InputStream is) throws Exception {
        //创建Excel工作蒲
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        int sheetNum = workbook.getNumberOfSheets();
        boolean flag = false;
        List<AreaGroupDetailDto> dtoList = new ArrayList<AreaGroupDetailDto>();
        String temp = "";
        AreaGroupDetailDto tempAd = null;

        for(int i=0;i<sheetNum;i++)
        {
            HSSFSheet sheet = workbook.getSheetAt(i);
            for(int rownum=1;rownum < sheet.getPhysicalNumberOfRows();rownum++)
            {
                try
                {
                    Row row = sheet.getRow(rownum);       //获取行
                    if(row != null)
                    {
                        Row rowCheck = sheet.getRow(1); //取出表格第一行第一列的值进行校验
                        temp = getCellValueFromIndex(rowCheck,0);
                        //获取每行的列值
                        String areaGroupId = getCellValueFromIndex(row,0);
                        String provinceId = getCellValueFromIndex(row,1);
                        String provID = provinceId.substring(1);
                        String cityId = getCellValueFromIndex(row,3);
                        String countyId = getCellValueFromIndex(row,5);
                        String areaId = getCellValueFromIndex(row,7);
                        String sendTime = getCellValueFromIndex(row,9);
                        if(areaGroupId.equals("") || areaId.equals("") || sendTime.equals("") || !temp.equals(areaGroupId))
                        {   //数据不完整,或数据有误，跳出放弃处理
                            flag = false;
                            break;
                        }else {   //记录值
                            tempAd = new AreaGroupDetailDto();
                            tempAd.setAreaGroupId(areaGroupId);
                            tempAd.setProvinceId(provID);
                            tempAd.setCityId(cityId);
                            tempAd.setCountyId(countyId);
                            tempAd.setAreaId(areaId);
                            tempAd.setSendTime(sendTime);
                            dtoList.add(tempAd);
                            flag = true;
                        }
                    }
                }catch (Exception ex){
                       System.out.println("异常："+ex.getMessage());
                }
            }
        }
        //方法调用
        flag = this.insertAreaDetail(flag,dtoList,temp);
        if(!flag){
           workbook = null;
        }
        return workbook;
    }
    //Excel数据导入 明细表
    private boolean insertAreaDetail(boolean flag,List<AreaGroupDetailDto> dtoList,String areaGroupId)
    {
        if(flag)
        {
            AreaGroup areaGroup = areaGroupHeaderDao.getIsAreaGroup(Long.parseLong(areaGroupId));
            //校验地址组编号是否存在表头并且为停用状态
            if(areaGroup != null && !areaGroup.isActive())
            {
                if(dtoList.size() > 0)
                {
                    try{
                        String id = dtoList.get(0).getAreaGroupId();
                        areaGroupDetailDao.deleteByAreaGroupId(Long.parseLong(id));   //删除原有明细
                        //数据导入
                        String name = SecurityHelper.getLoginUser().getName();     //创建人
                        for(int i=0;i<dtoList.size();i++)
                        {
                            AreaGroupDetail agd = new AreaGroupDetail();
                            agd.setAreaGroup(areaGroup);
                            agd.setProvinceId(Integer.parseInt(dtoList.get(i).getProvinceId()));
                            agd.setCityId(Integer.parseInt(dtoList.get(i).getCityId()));
                            agd.setCountyId(Integer.parseInt(dtoList.get(i).getCountyId()));
                            agd.setAreaId(Integer.parseInt(dtoList.get(i).getAreaId()));
                            agd.setUserDef1(dtoList.get(i).getSendTime());
                            agd.setCrDT(new Date());
                            agd.setCrUser(name);
                            areaGroupDetailDao.saveAreaGroupDetail(agd);
                        }
                        return true;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 地址组明细导出
     * @param areId
     * @return
     */
    public HSSFWorkbook getAreaGroupDetailDtoForDownload(Long areId)
    {
        //创建新的Excel工作蒲
        HSSFWorkbook workbook = new HSSFWorkbook();
        //Excel名称
        HSSFSheet sheet = workbook.createSheet("地址组明细");
        //创建第一行
        HSSFRow row = sheet.createRow(0);
        //创建第一列
        HSSFCell cell = row.createCell((short)0);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("AREGROUPID");
        //创建第二列
        cell = row.createCell((short)1);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("PROVINCEID");
        //创建第三列
        cell = row.createCell((short)2);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("PROVINCENAME");
        //创建第四列
        cell = row.createCell((short)3);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("CITYID");
        //创建第五列
        cell = row.createCell((short)4);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("CITYNAME");
        //创建第六列
        cell = row.createCell((short)5);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("COUNTYID");
        //创建第七列
        cell = row.createCell((short)6);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("COUNTYNAME");
        //创建第八列
        cell = row.createCell((short)7);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("AREAID");
        //创建第九列
        cell = row.createCell((short)8);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("AREANAME");
        //创建第十列
        cell = row.createCell((short)9);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("sendtime");
        List list = areaGroupDetailDao.getAreaGroupDetail(areId);
        if(list.size()> 0 && !list.isEmpty())
        {
            Object[] obj = null;
            for(int i=0;i<list.size();i++)
            {
                obj = (Object[])list.get(i);

                //创建第一行
                row = sheet.createRow(i+1);
                //创建第一列
                cell = row.createCell((short)0);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[0]!=null?((BigDecimal)obj[0]).toString():"");
                //创建第二列
                cell = row.createCell((short)1);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[1]!=null?"0"+((BigDecimal)obj[1]).toString():"");
                //创建第三列
                cell = row.createCell((short)2);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[2]!=null?((String)obj[2]).toString():"");
                //创建第四列
                cell = row.createCell((short)3);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[3]!=null?((BigDecimal)obj[3]).toString():"");
                //创建第五列
                cell = row.createCell((short)4);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[4]!=null?((String)obj[4]).toString():"");
                //创建第六列
                cell = row.createCell((short)5);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[5]!=null?((BigDecimal)obj[5]).toString():"");
                //创建第七列
                cell = row.createCell((short)6);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[6]!=null?((String)obj[6]).toString():"");
                //创建第八列
                cell = row.createCell((short)7);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[7]!=null?((BigDecimal)obj[7]).toString():"");
                //创建第九列
                cell = row.createCell((short)8);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[8]!=null?((String)obj[8]).toString():"");
                //创建第十列
                cell = row.createCell((short)9);
                //定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                //在单元格中输入一些内容
                cell.setCellValue(obj[9]!=null?((String)obj[9]).toString():"");
            }
        }
        return workbook;
    }
    //Excel单元格值验证
    private String getCellValueFromIndex(Row row, int colIndex){
        String result = "";
        Cell cell = row.getCell(colIndex);
        if(cell != null){
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:{
                    result = cell.getStringCellValue().trim();
                }  break;
                case Cell.CELL_TYPE_NUMERIC:{
                    if(DateUtil.isCellDateFormatted(cell))
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d hh:mm");
                        result = sdf.format(cell.getDateCellValue());
                    }
                    else
                    {
                        result = String.valueOf(cell.getNumericCellValue());
                        if(result.contains("E")){
                            DecimalFormat df = new DecimalFormat("0");
                            result = df.format(cell.getNumericCellValue());
                        }
                        else if(result.endsWith(".0"))
                        {
                            result = result.substring(0, result.length() - 2);
                        }
                    }
                } break;
                default: break;
            }
        }
        return result;
    }
}
