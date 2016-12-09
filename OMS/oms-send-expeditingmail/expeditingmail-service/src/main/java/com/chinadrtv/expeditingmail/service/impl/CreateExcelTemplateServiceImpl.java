package com.chinadrtv.expeditingmail.service.impl;

import com.chinadrtv.expeditingmail.common.dal.model.DeliveryMailDetail;
import com.chinadrtv.expeditingmail.service.CreateExcelTemplateService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-21
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
@Service("CreateExcelTemplateService")
public class CreateExcelTemplateServiceImpl implements CreateExcelTemplateService {
    @Override
    public byte[] createExcel(List<DeliveryMailDetail> list) throws Exception {
        if(list.size() > 0){
            HSSFWorkbook wb = new HSSFWorkbook();   //创建工作蒲对象
            HSSFSheet sheet = wb.createSheet("超时效信息");     //创建工作薄对象并命名
            HSSFRow rowT = sheet.createRow(0);  //创建Excel数据第一行标题
            HSSFCell cell = rowT.createCell(0);
            cell.setCellValue("订单号");
            cell = rowT.createCell(1);
            cell.setCellValue("包裹号");
            cell = rowT.createCell(2);
            cell.setCellValue("交际日期");
            cell = rowT.createCell(3);
            cell.setCellValue("标准天数");
            cell = rowT.createCell(4);
            cell.setCellValue("超时效天数");
            cell = rowT.createCell(5);
            cell.setCellValue("发货仓库");
            //创建内容列
            for(int i=0;i<list.size();i++){
                DeliveryMailDetail detail = list.get(i);
                rowT = sheet.createRow(i+1);
                cell = rowT.createCell(0);
                cell.setCellValue(detail.getOrderid());
                cell = rowT.createCell(1);
                cell.setCellValue(detail.getMailid());
                cell = rowT.createCell(2);
                cell.setCellValue(detail.getRfoutdat());
                cell = rowT.createCell(3);
                cell.setCellValue(detail.getRoadday());
                cell = rowT.createCell(4);
                cell.setCellValue(detail.getOutDay());
                cell = rowT.createCell(5);
                cell.setCellValue(detail.getWarehouse());
            }
            //生成文件流
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            byte[] bufferBytes;
            try{
                wb.write(fos);
                bufferBytes = fos.toByteArray();
            }catch (Exception ex){
                bufferBytes = new byte[0];
                throw new Exception(ex);
            } finally {
                try{
                    fos.close();
                }catch (IOException ex){
                    throw new Exception(ex);
                }
            }
            return bufferBytes;
        }
        return new byte[0];
    }
}
