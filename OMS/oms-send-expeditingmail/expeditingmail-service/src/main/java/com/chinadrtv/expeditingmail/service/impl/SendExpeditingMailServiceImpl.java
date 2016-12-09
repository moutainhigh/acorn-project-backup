package com.chinadrtv.expeditingmail.service.impl;

import com.chinadrtv.expeditingmail.common.dal.dao.CompanyContractDao;
import com.chinadrtv.expeditingmail.common.dal.dao.DeliveryMailDetailDao;
import com.chinadrtv.expeditingmail.common.dal.model.CompanyContract;
import com.chinadrtv.expeditingmail.common.dal.model.DeliveryMailDetail;
import com.chinadrtv.expeditingmail.service.AutoEmailService;
import com.chinadrtv.expeditingmail.service.CreateExcelTemplateService;
import com.chinadrtv.expeditingmail.service.SendExpeditingMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-21
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
@Service("SendExpeditingMailService")
public class SendExpeditingMailServiceImpl implements SendExpeditingMailService {
    private static Logger log  = LoggerFactory.getLogger(SendExpeditingMailServiceImpl.class);
    @Value("${mail.username}")
    private String userName;
    @Value("${mail.failusername}")
    private String failuserName;

    @Autowired
    private DeliveryMailDetailDao deliveryMailDetailDao;
    @Autowired
    private CompanyContractDao companyContractDao;
    @Autowired
    private CreateExcelTemplateService createExcelTemplateService;
    @Autowired
    private AutoEmailService autoEmailService;

    private String errorMsg = "";
    @Override
    public void timingSendMail() {
        try{
            List<CompanyContract> contractList = companyContractDao.findCompanyContract();
            log.info("get contractList size = "+contractList.size());
            if(contractList != null && contractList.size() > 0){
                //循环遍历承运商
                for (CompanyContract contract : contractList){
                    errorMsg = "";  //清空上次异常记录
                    if(contract.getOptsEmail() == null && contract.getAcornOptsEmail() == null){
                        continue;
                    }
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("p_companyid",contract.getId());
                    param.put("p_flag",1);
                    param.put("p_return",null);
                    param.put("p_cur",null);
                    deliveryMailDetailDao.execOmsProDeliveryOvertime(param);
                    //判断执行存储过程成功失败标记
                    int result = Integer.parseInt(param.get("p_return").toString());
                    if(result != 1){
                        continue; //存储过程执行失败
                    }
                    //存储过程返回结果集
                    List<DeliveryMailDetail> detailList = (List<DeliveryMailDetail>)param.get("p_cur");
                    log.info("get detailList size :"+detailList.size());
                    if(detailList != null && detailList.size() > 0){
                        log.info("create Excel Template bytes start");
                        byte[] bytes = createExcelTemplateService.createExcel(detailList);
                        log.info("create Excel Template bytes end");

                        String[] toMail = this.getToMail(contract);
                        String[] cMail = this.getCMail(contract);
                        //String[] toMail1 = new String[]{"maozhenyu@chinadrtv.com","chenpanpan@chinadrtv.com"};
                        //String[] cMail1 = new String[]{"liukuan@chinadrtv.com"};
                        String fileName = contract.getName() + "_超时效清单.xls";
                        String title = contract.getName() + "_超时效邮件";

                        boolean sendResult = this.inventoryAutoSendEmail(title,toMail,cMail,fileName,bytes);
                        if(sendResult){
                            //邮件发送成功，回写数据库状态
                            deliveryMailDetailDao.updateDeliveryMailDetail(String.valueOf(contract.getId()));
                            log.info("send mail success !");
                        }else {
                            //邮件发送失败
                            this.sendEmailUnSuccessful(errorMsg);
                            log.info("send mail fail !");
                        }
                    }
                }
            }

        }catch (Exception ex){
            log.error("SendExpeditingMailServiceImpl.timingSendMail() Exception:"+ex.getMessage());
        }
    }

    //获取发送人邮箱列表
    private String[] getToMail(CompanyContract contract){
        String[] toMail = null;
        if(contract.getOptsEmail() != null && contract.getOptsEmail().length() > 5){
            String str = contract.getOptsEmail()+";";
            log.info("optsEmail = "+str);
            toMail = str.split(";");
        }
        return toMail;
    }
    //获取抄送人邮件列表
    private String[] getCMail(CompanyContract contract){
        String[] cMail = null;
        if(contract.getAcornOptsEmail() != null && contract.getAcornOptsEmail().length() > 5){
            String str = contract.getAcornOptsEmail() + ";";
            log.info("acornOptsEmail = "+str);
            cMail = str.split(";");
        }
        return cMail;
    }

    //邮件发送方法
    private boolean inventoryAutoSendEmail(String title,String[] toMail,String[] cMail,String fileName,byte[] bufferBytes)
    {
        Date date = new Date();
        SimpleDateFormat dateMM = new SimpleDateFormat("HH时MM分");
        SimpleDateFormat dateYY = new SimpleDateFormat("yyyy年MM月dd日 HH:MM:ss");
        String hMs = dateMM.format(date);
        String hMd = dateYY.format(date);
        String contentVal = ""; //发送内容
        contentVal += "  至今日 " + hMs + "，我公司委托配送的包裹中，有单据无信息反馈，超出配送信息反馈时效。\r\n";
        contentVal += "详细信息，请查看附件。\r\n";
        contentVal += "请及时处理，谢谢！\r\n\r\n\r\n";
        contentVal += "橡果国际\r\n" + hMd;
        boolean result = false;
        try{
            autoEmailService.sendMail(title,contentVal,toMail,cMail,userName,fileName,bufferBytes);
            result = true;
        }catch (Exception ex){
            log.error(ex.getMessage());
            errorMsg = title+"异常信息:"+ex.getMessage();
        }
        return result;
    }

    //邮件异常提醒
    private void sendEmailUnSuccessful(String text){
        Date date = new Date();
        SimpleDateFormat dateYY = new SimpleDateFormat("yyyy年MM月dd日 HH:MM:ss");
        String hMd = dateYY.format(date);
        String contentVal = ""; //发送内容
        contentVal += "  至今日，我公司超时效邮件发送失败。\r\n";
        contentVal += "错误信息："+text+"\r\n";
        contentVal += "请核实，谢谢！\r\n\r\n";
        contentVal += "橡果国际\r\n" + hMd;
        String title = "超时效邮件发送失败";  //主题
        String deliver = failuserName;    //收件人
        String from =userName;  //发件人
        try{
            autoEmailService.sendRemindMail(title,contentVal,deliver,from);
        }catch (Exception ex){
            log.debug(ex.getMessage());
        }

    }
}
