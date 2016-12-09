package com.chinadrtv.erp.uc.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.marketing.core.dao.LeadInterActionDao;
import com.chinadrtv.erp.model.marketing.CallbackLog;
import com.chinadrtv.erp.model.marketing.CallbackLogSpecification;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.uc.dao.CallbackLogDao;
import com.chinadrtv.erp.uc.service.CallbackLogService;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;

/**
 * 分配历史记录
 * User: gaudi.gao
 * Date: 13-8-9
 * Time: 下午3:54
 * To change this template use File | Settings | File Templates.
 */
@Service("callbackLogService")
public class CallbackLogServiceImpl implements CallbackLogService {

    @Autowired
    private CallbackLogDao callbackLogDao;

    @Autowired
    private LeadInterActionDao leadInterActionDao;

    @Autowired
    private UserService userService;

    public Long findCallbackLogCount(CallbackLogSpecification specification) {
        return callbackLogDao.findCallbackLogCount(specification);
    }

    public List<CallbackLog> findCallbackLogs(CallbackLogSpecification specification, Integer index, Integer size) {
        List<CallbackLog> logs = callbackLogDao.findCallbackLogs(specification, index, size);
        Map<String, String> users = new HashMap<String, String>();
        for(CallbackLog log : logs){
            if(!users.containsKey(log.getUsrId())){
                users.put(log.getUsrId(), getAgentUserName(log.getUsrId()));
            }
            log.setUsrName(users.get(log.getUsrId()));
            log.setAni(markAniNumber(log.getAni()));
            initLeadInteraction(log, log.getLeadInteractionId());
            initLdapGroup(log);
        }
        return logs;
    }

    public HSSFWorkbook exportCallbackLogs(CallbackLogSpecification specification) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HSSFRow title = sheet.createRow(0);

        title.createCell(0).setCellValue("分配类型");
        title.createCell(1).setCellValue("被叫号");
        title.createCell(2).setCellValue("主叫号");
        title.createCell(3).setCellValue("座席工号");
        title.createCell(4).setCellValue("座席姓名");
        title.createCell(5).setCellValue("工作组");
        title.createCell(6).setCellValue("呼入时间");
        title.createCell(7).setCellValue("1次分配人员工号");
        title.createCell(8).setCellValue("1次分配人员名称");
        title.createCell(9).setCellValue("1次分配时间");
        title.createCell(10).setCellValue("1次被分配坐席工号");
        title.createCell(11).setCellValue("1次被分配坐席名称");
        title.createCell(12).setCellValue("1次被分配坐席工作组");
//        title.createCell(13).setCellValue("2次分配人员工号");
//        title.createCell(14).setCellValue("2次分配时间");
//        title.createCell(15).setCellValue("2次被分配坐席工号");
//        title.createCell(16).setCellValue("2次被分配坐席名称");
//        title.createCell(17).setCellValue("2次被分配坐席工作组");
        title.createCell(13).setCellValue("ACD组");
        title.createCell(14).setCellValue("媒体产品");
        title.createCell(15).setCellValue("分配批次号");

        Long total = findCallbackLogCount(specification);
        if(total != null && total > 0){
            List<CallbackLog> list = findCallbackLogs(specification, 0, total.intValue());
            for(int i=0; i<list.size(); i++){
                CallbackLog log = list.get(i);
                HSSFRow row = sheet.createRow(i+1);
                if("1".equals(log.getType())) {
                    row.createCell(0).setCellValue("VM");
                }
                else if("2".equals(log.getType())) {
                    row.createCell(0).setCellValue("放弃");
                }
                else if("5".equals(log.getType())) {
                    row.createCell(0).setCellValue("SNATCH IN");
                }
                else if("3".equals((log.getType()))) {
                    row.createCell(0).setCellValue("通话");
                }
                else if("11".equals(log.getType())) {
                    row.createCell(0).setCellValue("VM(WILCOM)");
                }
                else if("12".equals(log.getType())) {
                    row.createCell(0).setCellValue("放弃(WILCOM)");
                }
                else if("15".equals(log.getType())) {
                    row.createCell(0).setCellValue("SNATCH IN(WILCOM)");
                }
                else if("13".equals((log.getType()))) {
                    row.createCell(0).setCellValue("通话(WILCOM)");
                }
                else {
                    row.createCell(0).setCellValue(log.getType());
                }

                row.createCell(1).setCellValue(log.getDnis());
                row.createCell(2).setCellValue(markAniNumber(log.getAni()));
                row.createCell(3).setCellValue(log.getUsrId());
                row.createCell(4).setCellValue(log.getUsrName());
                row.createCell(5).setCellValue(log.getUsrGrpName());
                row.createCell(6).setCellValue(log.getCallDate() != null ? sdf.format(log.getCallDate()) : "" ); //呼入时间
                row.createCell(7).setCellValue(log.getOpusr());
                row.createCell(8).setCellValue(log.getOpusrName());
                row.createCell(9).setCellValue(log.getFirstdt() != null ? sdf.format(log.getFirstdt()) : "");
                row.createCell(10).setCellValue(log.getFirstusrId());
                row.createCell(11).setCellValue(log.getFirstusrName());
                row.createCell(12).setCellValue(log.getFirstusrGrpName());
//                row.createCell(11).setCellValue(log.getDbdt() != null ? sdf.format(log.getDbdt()) : "");
//                row.createCell(12).setCellValue(log.getDbusrId());
                row.createCell(13).setCellValue(log.getAcdGroup());  //ACD
                row.createCell(14).setCellValue(log.getMediaprodId());
                row.createCell(15).setCellValue(log.getBatchId());
            }
        }

        return wb;
    }

    private void initLeadInteraction(CallbackLog log, Long leadInteractionId){
        try
        {
            LeadInteraction li = leadInterActionDao.get(leadInteractionId);
            if(li != null){
                log.setCallDuration(li.getTimeLength());
                log.setCallDate(li.getCreateDate()); //li.getCtiStartDate()
            }
        }
        catch (Exception ex){
            /* do nothing */
        }
    }

    private void initLdapGroup(CallbackLog log){
        GroupInfo groupInfo = getAgentGroupInfo(log.getUsrGrp());
        if(groupInfo != null){
            log.setUsrGrpName(groupInfo.getName());
        }  else {
            log.setUsrGrpName(log.getUsrGrp());
        }

        String userName;
        if(log.getOpusr() != null){
            userName = getAgentUserName(log.getOpusr());
            if(StringUtils.isNotBlank(userName)){
                log.setOpusrName(userName);
            } else {
                log.setOpusrName(log.getOpusr());
            }
        }

        if(log.getDbusrId() != null){
            userName = getAgentUserName(log.getDbusrId());
            if(StringUtils.isNotBlank(userName)){
                log.setDbusrName(userName);
            } else {
                log.setDbusrName(log.getDbusrId());
            }
        }

        if(log.getFirstusrId() != null){
            userName = getAgentUserName(log.getFirstusrId());
            if(StringUtils.isNotBlank(userName)){
                log.setFirstusrName(userName);
            } else {
                log.setFirstusrName(log.getFirstusrId());
            }
        }

        if(log.getRdbusrId() != null){
            userName = getAgentUserName(log.getRdbusrId());
            if(StringUtils.isNotBlank(userName)){
                log.setRdbusrName(userName);
            } else {
                log.setRdbusrName(log.getRdbusrId());
            }
        }

        String groupId;
        if(log.getFirstusrId() != null){
            groupId = getAgentUserGroup(log.getFirstusrId());
            if(StringUtils.isNotBlank(groupId)){
                groupInfo = getAgentGroupInfo(groupId);
                if(groupInfo != null){
                    log.setFirstusrGrpName(groupInfo.getName());
                }  else {
                    log.setFirstusrGrpName(groupId);
                }
            }
        }

        if(log.getRdbusrId() != null){
            groupId = getAgentUserGroup(log.getRdbusrId());
            if(StringUtils.isNotBlank(groupId)){
                groupInfo = getAgentGroupInfo(groupId);
                if(groupInfo != null){
                    log.setRdbusrGrpName(groupInfo.getName());
                }  else {
                    log.setRdbusrGrpName(groupId);
                }
            }
        }
    }

    private String getAgentUserName(String usrId){
        try
        {
            if(StringUtils.isNotBlank(usrId)){
                return userService.getUserAttribute(usrId, SecurityConstants.LDAP_USER_ATTRIBUTE_DISPLAYNAME);
            }
            else return usrId;
        }
        catch (Exception ex){
            return usrId;
        }
    }

    private String getAgentUserGroup(String usrId){
        try
        {
            return userService.getUserGroup(usrId);
        }
        catch (Exception ex){
            return "";
        }
    }

    private GroupInfo getAgentGroupInfo(String groupId){
        try
        {
            return userService.getGroupInfo(groupId);
        }
        catch (Exception ex){
            return null;
        }
    }

    public String markAniNumber( String aniNumber){
        if(aniNumber != null && aniNumber.length() > 4) {
            String str = aniNumber.substring(0, aniNumber.length() - 4);
            aniNumber = StringUtils.rightPad(str, aniNumber.length(), "*");
        }
        return aniNumber;
    }
}
