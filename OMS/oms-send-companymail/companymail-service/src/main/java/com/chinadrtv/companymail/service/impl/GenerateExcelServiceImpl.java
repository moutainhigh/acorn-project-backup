package com.chinadrtv.companymail.service.impl;

import com.chinadrtv.companymail.common.dal.daowms.WmsDataInfoDao;
import com.chinadrtv.companymail.common.dal.model.ZMMRPTEMSMailList;
import com.chinadrtv.companymail.common.dal.model.ZMMRPTReceivableslist;
import com.chinadrtv.companymail.service.GenerateExcelService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-22
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 * 承运商邮件发送模
 */
@Service("es34GenerateExcelService")
public class GenerateExcelServiceImpl implements GenerateExcelService {
    private static Logger log  = LoggerFactory.getLogger(GenerateExcelServiceImpl.class);
    @Autowired
    private WmsDataInfoDao wmsDataInfoDao;
    /**
     * 根据装载号、邮件类型创建模板
     * @param internalLoadNum
     * @param mailType
     */
    public byte[] createExcelTemplate(Integer internalLoadNum,Integer mailType,String company) throws Exception{
        byte[] bufferBytes = null;
         switch (mailType){
             case 1:
                 log.info("调用模板R54方法");
                 bufferBytes = this.R54STExcel(internalLoadNum,company);
                 break;
             case 2:
                 log.info("调用模板R56方法");
                 bufferBytes = this.R56Excel(internalLoadNum);
                 break;
             case 3:
                 log.info("调用模板R54非代收方法");
                 bufferBytes = this.R56STFeiDaiShouExcel(internalLoadNum,company);
                 break;
         }
        return bufferBytes;
    }
    DecimalFormat df = new DecimalFormat("0.00");   //金额保留2位小数
    /**
     * R54.送货公司邮件交寄清单（申通专用）
     */
    private byte[] R54STExcel(Integer num,String company) throws Exception
    {
        List<ZMMRPTReceivableslist> list = wmsDataInfoDao.findZMMRPTReceivableslist(num);
        if(list.size() > 0)
        {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            HSSFWorkbook wb = new HSSFWorkbook();     //创建工作簿对象
            HSSFSheet sheet = wb.createSheet();     //创建工作表对象并命名
            HSSFRow rowT = sheet.createRow(0);  //创建标题第一行
            HSSFCell cell = rowT.createCell(0);
            cell.setCellValue("邮件交际清单");
            cell = rowT.createCell(1);
            cell.setCellValue("交际日期:");
            cell = rowT.createCell(2);
            cell.setCellValue(sdf.format(date));
            cell = rowT.createCell(3);
            cell.setCellValue("送货公司:");
            cell = rowT.createCell(4);
            cell.setCellValue(company);
            rowT = sheet.createRow(1);	//创建标题第二行
            cell = rowT.createCell(0);
            cell.setCellValue("达省市县");
            cell = rowT.createCell(1);
            cell.setCellValue("收件人");
            cell = rowT.createCell(2);
            cell.setCellValue("地址");
            cell = rowT.createCell(3);
            cell.setCellValue("订单号码");
            cell = rowT.createCell(4);
            cell.setCellValue("订购日期");
            cell = rowT.createCell(5);
            cell.setCellValue("产品简称");
            cell = rowT.createCell(6);
            cell.setCellValue("邮件编号");
            cell = rowT.createCell(7);
            cell.setCellValue("应收款");
            cell = rowT.createCell(8);
            cell.setCellValue("重量");
            int count = 2;
            Double countMoney = 0D;
            for(int j = 0;j < list.size();j++)
            {     //遍历集合对象创建行和单元格
                count++;
                ZMMRPTReceivableslist temp = list.get(j);
                countMoney += temp.getIncomeMoney();
                //创建行
                HSSFRow row = sheet.createRow(j+2);
                //创建单元格并赋值
                HSSFCell nameCell1 = row.createCell(0);
                nameCell1.setCellValue(temp.getProname());
                HSSFCell nameCell2 = row.createCell(1);
                nameCell2.setCellValue(temp.getToPerson());
                HSSFCell nameCell3 = row.createCell(2);
                nameCell3.setCellValue(temp.getAddress());
                HSSFCell nameCell4 = row.createCell(3);
                nameCell4.setCellValue(temp.getOrderNum());
                HSSFCell nameCell5 = row.createCell(4);
                nameCell5.setCellValue(temp.getOrderDate());
                HSSFCell nameCell6 = row.createCell(5);
                nameCell6.setCellValue(temp.getProductName());
                HSSFCell nameCell7 = row.createCell(6);
                nameCell7.setCellValue(temp.getEmialNum());
                HSSFCell nameCell8 = row.createCell(7);
                nameCell8.setCellValue(df.format(temp.getIncomeMoney()));
                HSSFCell nameCell9 = row.createCell(8);
                nameCell9.setCellValue(df.format(temp.getWeight()));
            }
            rowT = sheet.createRow(count);	//数据统计
            cell = rowT.createCell(0);
            cell.setCellValue("合计邮件个数：");
            cell = rowT.createCell(1);
            cell.setCellValue(list.size());
            cell = rowT.createCell(2);
            cell.setCellValue("统计金额：");
            cell = rowT.createCell(3);
            cell.setCellValue(df.format(countMoney));
            //生成文件
            ByteArrayOutputStream  fos = new ByteArrayOutputStream();
            byte[] bufferBytes;
            try {
                wb.write(fos);
                bufferBytes = fos.toByteArray();
            } catch (Exception e) {
                bufferBytes = new byte[0];
                throw new Exception("文件流R54异常："+e.getMessage());
            }finally{
                try {
                    fos.close();
                } catch (IOException e) {
                     throw new Exception("文件流R54关闭异常："+e.getMessage());
                }
            }
            return bufferBytes;
        }
        return new byte[0];
    }
    /**
     * R56.EMS中速数据报送表
     */
    private byte[] R56Excel(Integer num) throws Exception
    {
        List<ZMMRPTEMSMailList> list = wmsDataInfoDao.findZMMRPTEMSMailList(num);
        if(list.size() > 0)
        {
            HSSFWorkbook wb = new HSSFWorkbook();     //创建工作簿对象
            HSSFSheet sheet = wb.createSheet();   //创建工作表对象并命名
            HSSFRow rowH = sheet.createRow(0);	// 创建标题第一行
            HSSFCell cell = rowH.createCell(0);
            cell.setCellValue("中速数据报送");
            cell = rowH.createCell(1);
            cell.setCellValue("交际日期:");
            cell = rowH.createCell(2);
            cell.setCellValue(list.get(0).getOrderDate().substring(0,19));
            rowH = sheet.createRow(1);  //创建标题第二行
            cell = rowH.createCell(0);
            cell.setCellValue("公司代码");
            cell = rowH.createCell(1);
            cell.setCellValue("交寄日期");
            cell = rowH.createCell(2);
            cell.setCellValue("邮件号");
            cell = rowH.createCell(3);
            cell.setCellValue("订单金额");
            cell=rowH.createCell(4);
            cell.setCellValue("重量");
            cell = rowH.createCell(5);
            cell.setCellValue("保留");
            cell = rowH.createCell(6);
            cell.setCellValue("保留1");
            cell = rowH.createCell(7);
            cell.setCellValue("城市简码");
            cell = rowH.createCell(8);
            cell.setCellValue("姓名");
            cell = rowH.createCell(9);
            cell.setCellValue("地址");
            cell = rowH.createCell(10);
            cell.setCellValue("订单号");
            cell = rowH.createCell(11);
            cell.setCellValue("产品简称");
            cell = rowH.createCell(12);
            cell.setCellValue("电话号码");
            cell = rowH.createCell(13);
            cell.setCellValue("邮编");
            int count = 2;
            Double countMoney = 0D;
            for(int j = 0;j < list.size();j++)
            {
                count++;
                ZMMRPTEMSMailList temp = list.get(j);
                countMoney += temp.getOrderMoney();
                //创建行
                HSSFRow row = sheet.createRow(j+2);
                //创建单元格并赋值
                HSSFCell nameCell0 = row.createCell(0);
                nameCell0.setCellValue(temp.getCompanyId());
                HSSFCell nameCell1 = row.createCell(1);
                nameCell1.setCellValue(temp.getOrderDate().substring(0,19));

                HSSFCell nameCell2 = row.createCell(2);
                nameCell2.setCellValue(temp.getEmail());

                HSSFCell nameCell3 = row.createCell(3);
                nameCell3.setCellValue(df.format(temp.getOrderMoney()));

                HSSFCell nameCell4=row.createCell(4);
                nameCell4.setCellValue(df.format(temp.getWeight()));

                HSSFCell nameCell5 = row.createCell(5);
                nameCell5.setCellValue(temp.getBaoliu());
                HSSFCell nameCell6 = row.createCell(6);
                nameCell6.setCellValue(temp.getBaoliu1());
                HSSFCell nameCell7 = row.createCell(7);
                nameCell7.setCellValue(temp.getCityId());
                HSSFCell nameCell8 = row.createCell(8);
                nameCell8.setCellValue(temp.getName());
                HSSFCell nameCell9 = row.createCell(9);
                nameCell9.setCellValue(temp.getAddress());
                HSSFCell nameCell10 = row.createCell(10);
                nameCell10.setCellValue(temp.getOrderId());
                HSSFCell nameCell11 = row.createCell(11);
                nameCell11.setCellValue(temp.getProName());
                HSSFCell nameCell12 = row.createCell(12);
                nameCell12.setCellValue(temp.getTelephone());
                HSSFCell nameCell13 = row.createCell(13);
                nameCell13.setCellValue(temp.getPostcode());

            }
            rowH = sheet.createRow(count);	//统计包裹数
            cell = rowH.createCell(0);
            cell.setCellValue("合计包裹数：");
            cell = rowH.createCell(1);
            cell.setCellValue(list.size());
            cell = rowH.createCell(2);
            cell.setCellValue("统计金额：");
            cell = rowH.createCell(3);
            cell.setCellValue(df.format(countMoney));
            //生成文件
            ByteArrayOutputStream  fos = new ByteArrayOutputStream();
            byte[] bufferBytes;
            try {
                wb.write(fos);
                bufferBytes = fos.toByteArray();
            } catch (Exception e) {
                bufferBytes = new byte[0];
                throw new Exception("文件流R56异常："+e.getMessage());
            }finally{
                try {
                    fos.close();
                } catch (IOException e) {
                   throw new Exception("文件流R56关闭异常："+e.getMessage());
                }
            }
            return bufferBytes;
        }
        return new byte[0];
    }
    /**
     * R56.申通非代收
     */
    private byte[] R56STFeiDaiShouExcel(Integer num,String company) throws Exception
    {
        List<ZMMRPTReceivableslist> list = wmsDataInfoDao.findZMMRPTReceivableslist(num);
        if(list.size() > 0)
        {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            HSSFWorkbook wb = new HSSFWorkbook();     //创建工作簿对象
            HSSFSheet sheet = wb.createSheet();     //创建工作表对象并命名
            HSSFRow rowT = sheet.createRow(0);  //创建标题第一行
            HSSFCell cell = rowT.createCell(0);
            cell.setCellValue("邮件交际清单");
            cell = rowT.createCell(1);
            cell.setCellValue("交际日期:");
            cell = rowT.createCell(2);
            cell.setCellValue(sdf.format(date));
            cell = rowT.createCell(3);
            cell.setCellValue("送货公司:");
            cell = rowT.createCell(4);
            cell.setCellValue(company);
            rowT = sheet.createRow(1);	//创建标题第二行
            cell = rowT.createCell(0);
            cell.setCellValue("达省市县");
            cell = rowT.createCell(1);
            cell.setCellValue("收件人");
            cell = rowT.createCell(2);
            cell.setCellValue("地址");
            cell = rowT.createCell(3);
            cell.setCellValue("订单号码");
            cell = rowT.createCell(4);
            cell.setCellValue("订购日期");
            cell = rowT.createCell(5);
            cell.setCellValue("产品简称");
            cell = rowT.createCell(6);
            cell.setCellValue("邮件编号");
            cell = rowT.createCell(7);
            cell.setCellValue("重量");
            int count = 2;
            for(int j = 0;j < list.size();j++)
            {     //遍历集合对象创建行和单元格
                count++;
                ZMMRPTReceivableslist temp = list.get(j);
                //创建行
                HSSFRow row = sheet.createRow(j+2);
                //创建单元格并赋值
                HSSFCell nameCell1 = row.createCell(0);
                nameCell1.setCellValue(temp.getProname());
                HSSFCell nameCell2 = row.createCell(1);
                nameCell2.setCellValue(temp.getToPerson());
                HSSFCell nameCell3 = row.createCell(2);
                nameCell3.setCellValue(temp.getAddress());
                HSSFCell nameCell4 = row.createCell(3);
                nameCell4.setCellValue(temp.getOrderNum());
                HSSFCell nameCell5 = row.createCell(4);
                nameCell5.setCellValue(temp.getOrderDate());
                HSSFCell nameCell6 = row.createCell(5);
                nameCell6.setCellValue(temp.getProductName());
                HSSFCell nameCell7 = row.createCell(6);
                nameCell7.setCellValue(temp.getEmialNum());
                HSSFCell nameCell9 = row.createCell(7);
                nameCell9.setCellValue(df.format(temp.getWeight()));
            }
            rowT = sheet.createRow(count);	//统计包裹数
            cell = rowT.createCell(0);
            cell.setCellValue("合计邮件个数：");
            cell = rowT.createCell(1);
            cell.setCellValue(list.size());
            //生成文件
            ByteArrayOutputStream  fos = new ByteArrayOutputStream();
            byte[] bufferBytes;
            try {
                wb.write(fos);
                bufferBytes = fos.toByteArray();
            } catch (Exception e) {
                bufferBytes = new byte[0];
                throw new Exception("文件流R56申通非代收异常："+e.getMessage());
            }finally{
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new Exception("文件流R56申通非代收关闭异常："+e.getMessage());
                }
            }
            return bufferBytes;
        }
        return new byte[0];
    }
}
