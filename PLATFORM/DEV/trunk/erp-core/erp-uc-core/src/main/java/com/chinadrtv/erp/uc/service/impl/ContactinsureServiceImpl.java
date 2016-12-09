package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.Contactinsure;
import com.chinadrtv.erp.model.agent.ContactinsureHistory;
import com.chinadrtv.erp.uc.dao.ContactinsureDao;
import com.chinadrtv.erp.uc.dao.ContactinsureHistoryDao;
import com.chinadrtv.erp.uc.service.ContactinsureService;
import com.chinadrtv.erp.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class ContactinsureServiceImpl extends GenericServiceImpl<Contactinsure,String> implements ContactinsureService {
    @Autowired
    private ContactinsureDao contactinsureDao;

    @Autowired
    private ContactinsureHistoryDao contactinsureHistoryDao;
    @Override
    protected GenericDao<Contactinsure, String> getGenericDao() {
        return contactinsureDao;
    }

    @Override
    public Contactinsure findContactinsure(String contactId) {
        return contactinsureDao.find("from Contactinsure where contactid=:contactid",new ParameterString("contactid",contactId));
    }

    private static final String checkStatus="-1";

    @Override
    public int saveContactinsure(Contactinsure contactinsure) {
        int result = 0; //0错误,,ok,1,已经赠险，2,增险成功
        if(StringUtils.isBlank(contactinsure.getContactid()))
            return result;
        //如果都设置成空，那么不删除
        {
            Contactinsure contactinsureSave=this.findContactinsure(contactinsure.getContactid());
            if(contactinsureSave==null)
            {
                contactinsure.setCrdt(contactinsure.getMddt());
                contactinsure.setMddt(null);
                contactinsure.setCrusr(contactinsure.getMdusr());
                contactinsure.setMdusr("");
                contactinsure.setRefusedate(new Date());
                if(contactinsure.getRefuse().equals("1")){
                    contactinsure.setRefuseDateTime(new Date());
                }else{
                    contactinsure.setRefuseDateTime(null);
                }
                if(contactinsure.getIsok().equals(contactinsure.getStatus()) && contactinsure.getIsok().equals("-1")){
                    if(contactinsure.getHasinsure()==0){
                        contactinsure.setInsureSeccDate(new Date());
                        //添加赠险历史记录
                        ContactinsureHistory contactinsureHistory = new ContactinsureHistory();
                        contactinsureHistory.setContactid(contactinsure.getContactid());
                        contactinsureHistory.setInsureSeccDate(new Date());
                        contactinsureHistory.setCallId(contactinsure.getCallId());
                        contactinsureHistory.setUserId(contactinsure.getCrusr());
                        result =2;
                        contactinsureHistoryDao.save(contactinsureHistory);
                    }
                }

                contactinsureDao.save(contactinsure);
            }
            else
            {
                contactinsureSave.setMddt(contactinsure.getMddt());
                contactinsureSave.setMdusr(contactinsure.getMdusr());
                //如果IsOk与status都为-1; 且赠险时间在90天内
                if(contactinsure.getIsok().equals(contactinsure.getStatus()) && contactinsure.getIsok().equals("-1")){
                    if(DateUtil.getDateDeviations(contactinsureSave.getInsureSeccDate(), new Date()) <=90  && DateUtil.getDateDeviations(contactinsureSave.getInsureSeccDate(),new Date()) >= 0){
                     //已赠保险且在有效期
                        result =1;
                        contactinsureSave.setIsok(contactinsure.getIsok());
                        contactinsureSave.setStatus(contactinsure.getStatus());
                    }else{
                        //赠险过期
                        if(DateUtil.getDateDeviations(contactinsureSave.getInsureSeccDate(), new Date())>90 || DateUtil.getDateDeviations(contactinsureSave.getInsureSeccDate(), new Date())<0 ){

                            if(contactinsure.getHasinsure()==0){
                                contactinsureSave.setInsureSeccDate(new Date());
                                //添加赠险历史记录
                                ContactinsureHistory contactinsureHistory = new ContactinsureHistory();
                                contactinsureHistory.setContactid(contactinsure.getContactid());
                                contactinsureHistory.setInsureSeccDate(new Date());
                                contactinsureHistory.setCallId(contactinsure.getCallId());
                                contactinsureHistory.setUserId(contactinsure.getMdusr());
                                result =2;
                                contactinsureHistoryDao.save(contactinsureHistory);
                            }else{
                                contactinsure.setInsureSeccDate(null);
                            }

                        }
                        contactinsureSave.setIsok(contactinsure.getIsok());
                        contactinsureSave.setStatus(contactinsure.getStatus());
                    }
                }else{

                    contactinsureSave.setIsok(contactinsure.getIsok());
                    contactinsureSave.setStatus(contactinsure.getStatus());
                }

                if(!contactinsureSave.getStatus().equals(contactinsure.getStatus()))
                {
                    contactinsureSave.setRefusedate(new Date());
                }

                if(contactinsure.getRefuse().equals("1")){
                    contactinsureSave.setRefuse(contactinsure.getRefuse());

                    if(DateUtil.getDateDeviations(contactinsureSave.getRefuseDateTime(), new Date())>90 || DateUtil.getDateDeviations(contactinsureSave.getRefuseDateTime(), new Date())<0 ){
                        if(contactinsure.getRefuseDateTime()==null){
                            contactinsureSave.setRefuseDateTime(new Date());
                        }else{
                            contactinsureSave.setRefuseDateTime(contactinsure.getRefuseDateTime());
                        }
                    }

                }

                contactinsureSave.setDsc(contactinsure.getDsc());
                //contactinsureSave.setInsurenote(contactinsure.getInsurenote());
                contactinsureDao.saveOrUpdate(contactinsureSave);
            }
        }
        return result;
    }
}
