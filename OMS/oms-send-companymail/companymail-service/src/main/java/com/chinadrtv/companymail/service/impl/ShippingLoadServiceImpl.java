package com.chinadrtv.companymail.service.impl;

import com.chinadrtv.companymail.common.dal.dao.CompanyContractDao;
import com.chinadrtv.companymail.common.dal.daowms.WmsDataInfoDao;
import com.chinadrtv.companymail.common.dal.model.CompanyContract;
import com.chinadrtv.companymail.common.dal.model.ShippingLoad;
import com.chinadrtv.companymail.common.dal.model.ZMMRPTEMSMailList;
import com.chinadrtv.companymail.common.dal.model.ZMMRPTReceivableslist;
import com.chinadrtv.companymail.service.AutoEmailService;
import com.chinadrtv.companymail.service.GenerateExcelService;
import com.chinadrtv.companymail.service.ShippingLoadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-21
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 * 发包清单邮件自动传送
 */
@Service("ShippingLoadService")
public class ShippingLoadServiceImpl implements ShippingLoadService {
    private static Logger log  = LoggerFactory.getLogger(ShippingLoadServiceImpl.class);
    @Value("${mail.username}")
    private String userName;
    @Value("${mail.failusername}")
    private String failuserName;

    @Autowired
    private CompanyContractDao companyContractDao;
    @Autowired
    private WmsDataInfoDao wmsDataInfoDao;
    @Autowired
    private AutoEmailService autoEmailService;
    @Autowired
    private GenerateExcelService generateExcelService;

    /**
     * 发包清单邮件发送
     */
    public void sendEmailCompany() {
        log.info("ShippingLoadServiceImpl.sendEmailCompany is start");
        //获取最新单号
        List<ShippingLoad> shippingLoadList = wmsDataInfoDao.findShippingLoad();
        log.info("shippingLoadList size = "+shippingLoadList.size());
        if(shippingLoadList.size() > 0)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String nowDate =  sdf.format(new Date());
            String title = "";     //邮件主题
            String fileName = "";   //附件名称
            String[] toMail = null; //收件人
            String[] cMail = null;  //抄送人
            for(ShippingLoad shippingLoad : shippingLoadList)
            {
                String company = shippingLoad.getCarrier();
              try{
                    CompanyContract companyContract = companyContractDao.findCompanyContractByName(company);
                    if(companyContract != null){
                        int countOrder = 0;
                        double countMoney = 0D;
                        int type = companyContract.getTemplateId() != null ? companyContract.getTemplateId() : 0;
                        title = nowDate+company+shippingLoad.getInternalLoadNum()+"邮件交寄清单";
                        switch (type){
                            case 1:
                                List<ZMMRPTReceivableslist> listR54 = wmsDataInfoDao.findZMMRPTReceivableslist(shippingLoad.getInternalLoadNum());
                                countOrder = listR54.size();
                                log.info(company+shippingLoad.getInternalLoadNum()+"countOrder="+countOrder);
                                for(int c=0;c<listR54.size();c++){
                                    countMoney += listR54.get(c).getIncomeMoney();
                                }
                                log.info(company+shippingLoad.getInternalLoadNum()+"countMoney="+countMoney);
                                String vipR54 = this.R54VIP(listR54,shippingLoad.getOrderMoney());
                                fileName = nowDate+company+vipR54+"发包清单"+shippingLoad.getInternalLoadNum()+".xls";
                                break;
                            case 2:
                                List<ZMMRPTEMSMailList> listR6 = wmsDataInfoDao.findZMMRPTEMSMailList(shippingLoad.getInternalLoadNum());
                                countOrder = listR6.size();
                                log.info(company+shippingLoad.getInternalLoadNum()+"countOrder="+countOrder);
                                for(int c=0;c<listR6.size();c++){
                                    countMoney += listR6.get(c).getOrderMoney();
                                }
                                log.info(company+shippingLoad.getInternalLoadNum()+"countMoney="+countMoney);
                                String vipR56 = this.R56VIP(listR6,shippingLoad.getOrderMoney());
                                fileName = nowDate+company+vipR56+"发包清单"+shippingLoad.getInternalLoadNum()+".xls";
                                break;
                            case 3:
                                List<ZMMRPTReceivableslist> listR56F = wmsDataInfoDao.findZMMRPTReceivableslist(shippingLoad.getInternalLoadNum());
                                countOrder = listR56F.size();
                                log.info(company+shippingLoad.getInternalLoadNum()+"countOrder="+countOrder);
                                fileName = nowDate+company+"发包清单"+shippingLoad.getInternalLoadNum()+".xls";
                                break;
                            default:
                                continue;
                        }
                        toMail =this.returnToMail(companyContract);
                        cMail = this.returnCMail(companyContract);
                        log.info(company+"toMail.length:"+toMail.length + ",cMail.length:"+cMail.length);
                        byte[] bufferBytes = generateExcelService.createExcelTemplate(shippingLoad.getInternalLoadNum(),type,company);
                        boolean flag = this.inventoryAutoSendEmail(bufferBytes,fileName,title,toMail,cMail,countOrder,countMoney);
                        if(flag){  //邮件发送成功，回写数据库标记
                            wmsDataInfoDao.updateShippingLoad(shippingLoad.getInternalLoadNum());
                            log.info("email send success!"+title);
                        }
                   }else {
                        log.info(company+"companyContract is null!");
                   }
                } catch (Exception ex)
                {
                    log.error("happen exception :"+ex.getMessage());
                }
            }
        }
        log.info("ShippingLoadServiceImpl.sendEmailCompany is end");
    }
    //返回收件人邮箱列表
    private String[] returnToMail(CompanyContract companyContract){
        String[] temp = null;
        String str = "";
        if(companyContract.getOptsEmail() == null || companyContract.getOptsEmail().length() < 5){
            str = companyContract.getAcornOptsEmail() + ";";
        }else if(companyContract.getAcornOptsEmail() == null || companyContract.getAcornOptsEmail().length() < 5){
            str = companyContract.getOptsEmail() + ";";
        }else{
            str = companyContract.getOptsEmail() + ";" + companyContract.getAcornOptsEmail();
        }
        log.info("to mail list:"+str);
        temp = str.split(";");
        return temp;
    }
    //返回抄送人邮件列表
    private String[] returnCMail(CompanyContract companyContract){
        String[] temp = null;
        String str2 = "";
        if(companyContract.getInformEmail() == null || companyContract.getInformEmail().length() < 5){
            str2 = companyContract.getAcornInformEmail() + ";";
        }else if(companyContract.getAcornInformEmail() == null || companyContract.getAcornInformEmail().length() < 5){
            str2 = companyContract.getInformEmail() + ";";
        }else {
            str2 = companyContract.getInformEmail() + ";" + companyContract.getAcornInformEmail();
        }
        log.info("copy to mail list:"+str2);
        temp = str2.split(";");
        return temp;
    }
    /**
     * 承运商邮件
     * @param bufferBytes
     * @param fileName
     * @param title
     * @param toMail
     * @param cMail
     * @return
     */
    public boolean inventoryAutoSendEmail(byte[] bufferBytes,String fileName,String title,String[] toMail,String[] cMail,Integer countOrder,Double countMoney)
    {
        Date date = new Date();
        SimpleDateFormat dateMM = new SimpleDateFormat("HH时MM分");
        SimpleDateFormat dateYY = new SimpleDateFormat("yyyy年MM月dd日 HH:MM:ss");
        String hMs = dateMM.format(date);
        String hMd = dateYY.format(date);

        String contentVal = ""; //发送内容
        contentVal += "  至今日 " + hMs + ",我公司发包交寄清单,合计订单数"+countOrder+",发包金额"+countMoney+"元。\r\n";
        contentVal += "详细信息，请查看附件。\r\n";
        contentVal += "请核实，谢谢！\r\n\r\n\r\n";
        contentVal += "橡果国际\r\n" + hMd;
        boolean flag = false;
        try{
            autoEmailService.sendMail(title,contentVal,toMail,cMail,userName,fileName,bufferBytes);
            flag = true;
        }catch (Exception ex){
            log.error("send mail exception:",ex);
            String errorMsg = ex.getMessage();
            this.sendEmailUnSuccessful(title,errorMsg); //发包清单发送失败，邮件提醒
        }
        return flag;
    }

    /**
     * 邮件发送失败提醒
     * @param title
     * @param errorMsg
     */
    private void sendEmailUnSuccessful(String title,String errorMsg){
        Date date = new Date();
        SimpleDateFormat dateYY = new SimpleDateFormat("yyyy年MM月dd日 HH:MM:ss");
        String hMd = dateYY.format(date);
        String contentVal = ""; //发送内容
        contentVal += "  至今日，" + errorMsg + "。\r\n";
        contentVal += "请及时处理，谢谢！\r\n\r\n";
        contentVal += "橡果国际\r\n" + hMd;
        String deliver = failuserName;    //收件人
        String from =userName;  //发件人
        try{
            autoEmailService.sendRemindMail(title,contentVal,deliver,from);
        }catch (Exception ex){
             log.error("sendEmailUnSuccessful exception:s"+ex.getMessage());
        }

    }
    //判断r54是否为VIP邮件
    private String R54VIP(List<ZMMRPTReceivableslist> list,Double money){
        String str = "VIP";
        if(money <= 0.0){
            str = "";
        }else{
            for (int i=0;i<list.size();i++){
                if(list.get(i).getIncomeMoney() < money){
                    str = "";
                    break;
                }
            }
        }
        return str;
    }
    //判断r56是否为VIP邮件
    private String R56VIP(List<ZMMRPTEMSMailList> list,Double money){
        String str = "VIP";
        if(money <= 0.0){
            str = "";
        }else{
            for (int i=0;i<list.size();i++){
                if(list.get(i).getOrderMoney() < money){
                    str = "";
                    break;
                }
            }
        }
        return str;
    }

}
