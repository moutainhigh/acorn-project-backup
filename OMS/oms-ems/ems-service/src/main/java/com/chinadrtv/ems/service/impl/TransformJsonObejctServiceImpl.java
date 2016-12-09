package com.chinadrtv.ems.service.impl;

import com.chinadrtv.ems.common.dal.dao.ShipmentHeaderDao;
import com.chinadrtv.ems.service.TransformJsonObejctService;
import com.chinadrtv.ems.service.bean.Listexpressmail;
import com.chinadrtv.ems.service.bean.Listexpressmails;
import com.chinadrtv.erp.orderfeedback.model.MailStatusHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-10-28
 * Time: 下午12:52
 * To change this template use File | Settings | File Templates.
 */
@Service("transformJsonObejctService")
public class TransformJsonObejctServiceImpl implements TransformJsonObejctService  {
    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    /**
     * 转换Json数据
     * @param strJson
     * @return
     */
    public List<MailStatusHis> transXmlToList(String strJson) throws Exception{
        try{
            Listexpressmails temp = JsonBinder.buildNormalBinder().fromJson(strJson,Listexpressmails.class);
            MailStatusHis mailStatusHis = null;
            List<MailStatusHis> mailStatusHisList = null;
            if(temp != null){
                mailStatusHis = new MailStatusHis();
                mailStatusHisList = new ArrayList<MailStatusHis>();
                for(Listexpressmail lp : temp.getListexpressmail()){
                    mailStatusHis.setMailid(lp.getMailnum());
                    mailStatusHis.setRemarks(lp.getDescription());
                    mailStatusHis.setStation(lp.getOrgfullname());


                    String companyId = shipmentHeaderDao.queryForList(lp.getMailnum()).size() > 0
                            ? shipmentHeaderDao.queryForList(lp.getMailnum()).get(0).getEntityId() : "";
                    mailStatusHis.setCompanyid(companyId);

                    String operaType =  this.getOperaType(lp);
                    mailStatusHis.setOperatype(operaType);

                    String strDate = lp.getProcdate()+lp.getProctime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
                    Date d = df.parse(strDate);
                    mailStatusHis.setOperadate(d);

                    mailStatusHisList.add(mailStatusHis);
                }
            }
            return mailStatusHisList;
        }catch (Exception e){
            throw new Exception("com.chinadrtv.ems.service.impl.TransformJsonObejctServiceImpl转换Json数据异常信息："+e.getMessage());
        }
    }

    /**
     * 验证授权信息和版本号
     * @param authenticate
     * @param version
     * @return
     */
    public String checkAuthenticateAndVersion(String authenticate, String version) {
        StringBuilder response = new StringBuilder();
        //验证授权信息
        if(authenticate != null){
            if(!"Und63920J6E7".equals(authenticate)){
                response.append("{\"response\":{\"success\":0,\"failmailnums\":\"\",\"remark\":\"授权信息不匹配!\"}}");
                return response.toString();
            }
        }else {
            response.append("{\"response\":{\"success\":0,\"failmailnums\":\"\",\"remark\":\"authenticate is null!\"}}");
            return response.toString();
        }
        //验证版本号
        if(version != null){
            if(!"HeN393fm329k".equals(version)){
                response.append("{\"response\":{\"success\":0,\"failmailnums\":\"\",\"remark\":\"版本号不匹配!\"}}");
                return response.toString();
            }
        }else {
            response.append("{\"response\":{\"success\":0,\"failmailnums\":\"\",\"remark\":\"version is null!\"}}");
            return response.toString();
        }
        return response.toString();
    }

    /**
     * EMS物流状态
     * @param lp
     * @return
     */
    public String getOperaType(Listexpressmail lp){
        String result = "";
        String action = lp.getAction() != null ? lp.getAction() : "";
        String properdelivery = lp.getProperdelivery() != null ? lp.getProperdelivery() : "";
        String notproperdelivery = lp.getNotproperdelivery() != null ? lp.getNotproperdelivery() : "";
        if(action.equals("00")){     //收寄
            result = "OP01";
        }else if(action.equals("10")){  //妥投
            if(properdelivery.equals("13") || properdelivery.equals("14")){
                result = "OP09";
            }else {
                result = "OP04";
            }
        }else if(action.equals("20")){  //未妥投
            if(notproperdelivery.equals("104")){
                result = "OP06";
            }else if(notproperdelivery.equals("117")){
                result = "OP07";
            }else{
                result = "OP08";
            }
        } else if(action.equals("41")){   //开拆
            result = "OP02";
        } else if(action.equals("50")){    //安排投递
            result = "OP03";
        }
        return result;
    }
}
