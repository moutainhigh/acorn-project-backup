package com.chinadrtv.remindmail.service.impl;

import com.chinadrtv.remindmail.common.dal.dao.CompanyContractDao;
import com.chinadrtv.remindmail.common.dal.dao.MailDetailsDao;
import com.chinadrtv.remindmail.common.dal.model.CompanyContract;
import com.chinadrtv.remindmail.common.dal.model.MailDetails;
import com.chinadrtv.remindmail.service.AutoEmailService;
import com.chinadrtv.remindmail.service.MailDetailsService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-27
 * Time: 下午1:15
 * To change this template use File | Settings | File Templates.
 */
@Service("mailDetailsService")
public class MailDetailsServiceImpl implements MailDetailsService {
    private static Logger log  = LoggerFactory.getLogger(MailDetailsServiceImpl.class);
    @Value("${mail.username}")
    private String userName;
    @Value("${mail.failusername}")
    private String failuserName;

    @Autowired
    private MailDetailsDao mailDetailsDao;
    @Autowired
    private CompanyContractDao companyContractDao;
    @Autowired
    private AutoEmailService autoEmailService;

    @Override
    public void sendMailDetails() {
        try{
            Map<String, String> param = this.getSearchDate(); //催送货单据查询时间段
            List<CompanyContract> companyContractList = companyContractDao.findCompanyContract();
            log.info("get companyContractList size:"+companyContractList.size());
            if(companyContractList != null && companyContractList.size() > 0){
                //遍历承运商
                for(CompanyContract contract : companyContractList){
                    log.info(contract.getCompanyName()+"begin send send mail....");
                    param.put("companyId",contract.getCompanyId().trim());
                    log.info(contract.getCompanyName()+"startDate"+param.get("startDate")+",endDate:"+param.get("endDate")+",companyId:"+param.get("companyId"));

                    if(!param.containsKey("startDate") || !param.containsKey("endDate") || !param.containsKey("companyId")){
                        log.info(contract.getCompanyName()+"search param have null continue!");
                        continue;
                    }
                    List<MailDetails> mailDetailsList = mailDetailsDao.findMailDetailsByAppDate(param);
                    log.info(contract.getCompanyName()+"get mailDetailsList size:"+mailDetailsList.size());
                    if(mailDetailsList != null && mailDetailsList.size() > 0){
                        String title = contract.getCompanyName()+"_催送货邮件"+param.get("startDate")+"至"+param.get("endDate");   //邮件标题
                        String fileName = contract.getCompanyName()+"_催单数据.xls";   //附件名称
                        //创建邮件发送Excel信息模板
                        byte[] bytes = this.createExcel(mailDetailsList);
                        String[] toMail = this.returnToMail(contract);
                        String[] cMail = this.returnCMail(contract);
                        log.info(contract.getCompanyName()+"toMail.length"+toMail.length+",cMail.length"+cMail.length);
                        //发送邮件清单
                        boolean sendResult = this.inventoryAutoSendEmail(title,toMail,cMail,fileName,bytes);
                        log.info("send mail success !"+sendResult);
                        if(sendResult){
                            //回写状态
                            mailDetailsDao.updateOrderurgentapplication(param);
                            log.info("update status success !");
                        }
                    }
                }
            }
        }catch (Exception ex){
            log.error("sendMailDetails() Error:",ex);
        }

    }
    //返回收件人邮箱列表
    private String[] returnToMail(CompanyContract companyContract){
        String[] temp = null;
        if(companyContract.getOptsEmail() != null || companyContract.getOptsEmail().length() > 5){
            String strToMail = companyContract.getOptsEmail() + ";";
            log.info("toMail="+strToMail);
            temp = strToMail.split(";");
        }
        return temp;
    }
    //返回抄送人邮件列表
    private String[] returnCMail(CompanyContract companyContract){
        String[] temp = null;
        if(companyContract.getAcornOptsEmail() != null && companyContract.getAcornOptsEmail().length() > 5){
            String strCMail = companyContract.getAcornOptsEmail() + ";";
            log.info("cMail = "+strCMail);
            temp = strCMail.split(";");
        }
        return temp;
    }
    //获取催送货查询时间
    private Map getSearchDate(){
        Map<String, String> param = new HashMap<String, String>();
        com.chinadrtv.util.DateUtil dateUtil = new com.chinadrtv.util.DateUtil();
        Date date = new Date();
        int week = dateUtil.getWeekDay(date);
        int hours = date.getHours();
        String strTime = dateUtil.addDays(date,-3); //开始时间为当前时间三天前
        String today = com.chinadrtv.util.DateUtil.dateToString(date);
        if(week < 6){
            //周一到周五
            if(hours == 10){
                //10:00发送昨天最后发送时间到今天10：00的催单数据
                String startDate = strTime + " 00:00:00";
                String endDate = today + " 10:00:00";
                param.put("startDate",startDate);
                param.put("endDate",endDate);
                return param;
            }else if(hours == 14){
                //14：00发送当天10：00-14：00的催单数据
                String startDate = strTime + " 00:00:00";
                String endDate = today + " 14:00:00";
                param.put("startDate",startDate);
                param.put("endDate",endDate);
                return param;
            }else if(hours == 16){
                //16：00发送当天14：00-16：00的催单数据
                String startDate = strTime + " 00:00:00";
                String endDate = today + " 16:00:00";
                param.put("startDate",startDate);
                param.put("endDate",endDate);
                return param;
            }

        }else{
            //周六周日
            if(hours == 10){
                //10:00发送昨天最后发送时间到今天10：00的催单数据
                String startDate = strTime + " 00:00:00";
                String endDate = today + " 10:00:00";
                param.put("startDate",startDate);
                param.put("endDate",endDate);
                return param;
            }else if(hours == 15){
                //15：00发送当天10：00-15：00的催单数据
                String startDate = strTime + " 00:00:00";
                String endDate = today + " 15:00:00";
                param.put("startDate",startDate);
                param.put("endDate",endDate);
                return param;
            }
        }
        return param;
    }
    //催送货邮件
    private boolean inventoryAutoSendEmail(String title,String[] toMail,String[] cMail,String fileName,byte[] bufferBytes)
    {
        Date date = new Date();
        SimpleDateFormat dateMM = new SimpleDateFormat("HH时MM分");
        SimpleDateFormat dateYY = new SimpleDateFormat("yyyy年MM月dd日 HH:MM:ss");
        String hMs = dateMM.format(date);
        String hMd = dateYY.format(date);
        String contentVal = ""; //发送内容
        contentVal += "  至今日 " + hMs + "，我公司委托配送的包裹中，催送货信息清单。\r\n";
        contentVal += "详细信息，请查看附件。\r\n";
        contentVal += "请及时处理，谢谢！\r\n\r\n\r\n";
        contentVal += "橡果国际\r\n" + hMd;
        boolean result = false;
        try{
            autoEmailService.sendMail(title,contentVal,toMail,cMail,userName,fileName,bufferBytes);
            result = true;
        }catch (Exception ex){
            log.error("inventoryAutoSendEmail exception:"+ex.getMessage());
            String errorMsg = ex.getMessage();
            this.sendEmailUnSuccessful(errorMsg,title);
        }
        return result;
    }
    //邮件异常提醒
    private void sendEmailUnSuccessful(String errorMsg,String title){
        Date date = new Date();
        SimpleDateFormat dateYY = new SimpleDateFormat("yyyy年MM月dd日 HH:MM:ss");
        String hMd = dateYY.format(date);
        String contentVal = ""; //发送内容
        contentVal += "  至今日，我公司催送货清单邮件发送失败。\r\n";
        contentVal += "错误信息："+errorMsg+"\r\n";
        contentVal += "请核实，谢谢！\r\n\r\n";
        contentVal += "橡果国际\r\n" + hMd;
        title = "发送失败";  //主题
        String deliver = failuserName;    //收件人
        String from =userName;  //发件人
        try{
            autoEmailService.sendRemindMail(title,contentVal,deliver,from);
        }catch (Exception ex){
            log.debug(ex.getMessage());
        }

    }

    //创建Excel数据内容
    private byte[] createExcel(List<MailDetails> list) throws Exception {
        if(list.size() > 0){
            HSSFWorkbook wb = new HSSFWorkbook();   //创建工作蒲对象
            HSSFSheet sheet = wb.createSheet("催送货清单");     //创建工作薄对象并命名
            HSSFRow rowT = sheet.createRow(0);  //创建Excel数据第一行标题
            HSSFCell cell = rowT.createCell(0);
            cell.setCellValue("订单编号");
            cell = rowT.createCell(1);
            cell.setCellValue("坐席编号");
            cell = rowT.createCell(2);
            cell.setCellValue("坐席名称");
            cell = rowT.createCell(3);
            cell.setCellValue("订单状态");
            cell = rowT.createCell(4);
            cell.setCellValue("订单RF状态");
            cell = rowT.createCell(5);
            cell.setCellValue("催送货等级");
            cell = rowT.createCell(6);
            cell.setCellValue("申请日期");
            cell = rowT.createCell(7);
            cell.setCellValue("申请原因");
            cell = rowT.createCell(8);
            cell.setCellValue("申请人员");
            cell = rowT.createCell(9);
            cell.setCellValue("送货公司");
            cell = rowT.createCell(10);
            cell.setCellValue("出货仓库");
            cell = rowT.createCell(11);
            cell.setCellValue("邮件编号");
            cell = rowT.createCell(12);
            cell.setCellValue("顾客姓名");
            cell = rowT.createCell(13);
            cell.setCellValue("交寄日期");
            cell = rowT.createCell(14);
            cell.setCellValue("订单类型");
            cell = rowT.createCell(15);
            cell.setCellValue("顾客地址");
            cell = rowT.createCell(16);
            cell.setCellValue("记录状态");
            cell = rowT.createCell(17);
            cell.setCellValue("确认原因");
            cell = rowT.createCell(18);
            cell.setCellValue("确认人员");
            cell = rowT.createCell(19);
            cell.setCellValue("确认日期");
            cell = rowT.createCell(20);
            cell.setCellValue("完成人员");
            cell = rowT.createCell(21);
            cell.setCellValue("完成日期");
            /*cell = rowT.createCell(22);
            cell.setCellValue("送货公司ID");*/
            //创建数据内容
            for(int i=0;i<list.size();i++){
                MailDetails mailDetails = list.get(i);
                rowT = sheet.createRow(i+1);
                cell = rowT.createCell(0);
                cell.setCellValue(mailDetails.getOrderId());
                cell = rowT.createCell(1);
                cell.setCellValue(mailDetails.getUserId());
                cell = rowT.createCell(2);
                cell.setCellValue(mailDetails.getUserName());
                cell = rowT.createCell(3);
                cell.setCellValue(mailDetails.getOrderStatus());
                cell = rowT.createCell(4);
                cell.setCellValue(mailDetails.getRfStatus());
                cell = rowT.createCell(5);
                cell.setCellValue(mailDetails.getOrderClass());
                cell = rowT.createCell(6);
                cell.setCellValue(mailDetails.getAppDate());
                cell = rowT.createCell(7);
                cell.setCellValue(mailDetails.getApplicationReason());
                cell = rowT.createCell(8);
                cell.setCellValue(mailDetails.getAppPsn());
                cell = rowT.createCell(9);
                cell.setCellValue(mailDetails.getCompanyName());
                cell = rowT.createCell(10);
                cell.setCellValue(mailDetails.getOutHouse());
                cell = rowT.createCell(11);
                cell.setCellValue(mailDetails.getMailId());
                cell = rowT.createCell(12);
                cell.setCellValue(mailDetails.getName());
                cell = rowT.createCell(13);
                cell.setCellValue(mailDetails.getSendDt());
                cell = rowT.createCell(14);
                cell.setCellValue(mailDetails.getOrderType());
                cell = rowT.createCell(15);
                cell.setCellValue(mailDetails.getAddress());
                cell = rowT.createCell(16);
                cell.setCellValue(mailDetails.getStatus());
                cell = rowT.createCell(17);
                cell.setCellValue(mailDetails.getChkReason());
                cell = rowT.createCell(18);
                cell.setCellValue(mailDetails.getChkPsn());
                cell = rowT.createCell(19);
                cell.setCellValue(mailDetails.getChkDate());
                cell = rowT.createCell(20);
                cell.setCellValue(mailDetails.getFinishUser());
                cell = rowT.createCell(21);
                cell.setCellValue(mailDetails.getFinishDate());
                /*cell = rowT.createCell(22);
                cell.setCellValue(mailDetails.getEntityId());*/
            }
            //生成文件流
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            byte[] bufferBytes;
            try{
                wb.write(fos);
                bufferBytes = fos.toByteArray();
            }catch (Exception ex){
                log.error("IO error:"+ex.getMessage());
                bufferBytes = new byte[0];
                throw ex;
            }finally {
               try{
                   fos.close();
               }catch (IOException ex){
                   log.error("Io close error:"+ex.getMessage());
                   throw ex;
               }
            }
            return bufferBytes;
        }
       return new byte[0];
    }
}
