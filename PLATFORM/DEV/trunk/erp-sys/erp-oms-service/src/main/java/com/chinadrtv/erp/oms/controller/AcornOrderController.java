package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.oms.dao.AcornAreaDao;
import com.chinadrtv.erp.oms.dao.AcornCityDao;
import com.chinadrtv.erp.oms.dao.AcornProvinceDao;
import com.chinadrtv.erp.oms.model.AcornSession;
import com.chinadrtv.erp.oms.service.AcornAddressService;
import com.chinadrtv.erp.oms.service.AcornOrderExtService;
import com.chinadrtv.erp.oms.service.AcornSessionService;
import com.chinadrtv.erp.oms.util.*;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.uc.dao.AreaDao;
import com.chinadrtv.erp.uc.dao.ProvinceDao;
import com.chinadrtv.erp.uc.dao.hibernate.ProvinceDaoImpl;
import com.chinadrtv.erp.uc.service.AreaService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/order")
public class AcornOrderController extends TCBaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AcornOrderController.class);

    @Autowired
    private AcornSessionService acornSessionService;

    @Autowired
    private AcornOrderExtService acornOrderExtService;


    @RequestMapping(value = "/login",method= RequestMethod.POST)
    @ResponseBody
    public AcornLoginResult login(HttpServletRequest request,@RequestBody AcornUserInfo acornUserInfo)
    {
        logger.info("call login begin");
        AcornLoginResult acornLoginResult=new AcornLoginResult();

        if(acornUserInfo==null|| StringUtils.isBlank(acornUserInfo.getUserName())||StringUtils.isBlank(acornUserInfo.getCheckData()))
        {
            acornLoginResult.setSucc(false);
            acornLoginResult.setMessage("未提供用户数据");

            logger.error(acornLoginResult.getMessage());
            return acornLoginResult;
        }

        logger.info("user:"+acornUserInfo.getUserName()+"-data:"+acornUserInfo.getCheckData());
        String password="";
        if (!AcornUserConfig.userConfigInfo.containsKey(acornUserInfo.getUserName())) {
            acornLoginResult.setSucc(false);
            acornLoginResult.setMessage("用户名不存在");

            logger.error(acornLoginResult.getMessage());
            return acornLoginResult;
        }
        else
        {
            password=AcornUserConfig.userConfigInfo.get(acornUserInfo.getUserName());
        }
        //检查用户信息
        String strCheckData =  MD5Utils.getMD5(acornUserInfo.getUserName() + password);
        if(!strCheckData.equals(acornUserInfo.getCheckData()))
        {
            acornLoginResult.setSucc(false);
            acornLoginResult.setMessage("用户名或者密码不正确");

            logger.error(acornLoginResult.getMessage());
            return acornLoginResult;
        }

        AcornSession acornSession=new AcornSession();
        acornSession.setLoginDate(new Date());
        acornSession.setUserName(acornUserInfo.getUserName());
        //获取请求IP
        String strIp= getIpAddr(request);
        logger.info("remote ip:"+strIp);
        acornSession.setLoginIp(strIp);


        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate=simpleDateFormat.format(acornSession.getLoginDate());
        String sessionId=MD5Utils.getMD5(acornUserInfo.getUserName()+":"+strDate);
        acornSession.setSessionId(sessionId);

        acornSessionService.AddSession(acornSession);

        acornLoginResult.setToken(sessionId);
        return acornLoginResult;
    }

    @RequestMapping(value = "/getOrderId",method= RequestMethod.POST)
    @ResponseBody
    public AcornOrderSeqResult getOrderId(HttpServletRequest request, @RequestBody AcornUserToken acornUserToken)
    {
        logger.info("call getOrderId begin");
        AcornOrderSeqResult acornOrderSeqResult=new AcornOrderSeqResult();

        if(acornUserToken==null||StringUtils.isBlank(acornUserToken.getUserName())||StringUtils.isBlank(acornUserToken.getToken()))
        {
            acornOrderSeqResult.setSucc(false);
            acornOrderSeqResult.setMessage("未提供用户数据");

            logger.error(acornOrderSeqResult.getMessage());
            return acornOrderSeqResult;
        }

        //获取请求IP
        String strIp=getIpAddr(request);
        logger.info("remote ip:"+strIp);

        //检查用户登录信息
        AcornSession acornSession=new AcornSession();
        AcornResult acornResult=checkUserLogin(strIp, acornUserToken, acornSession);
        if(acornResult.isSucc()==false)
        {
            acornOrderSeqResult.setSucc(false);
            acornOrderSeqResult.setMessage(acornResult.getMessage());

            logger.error(acornOrderSeqResult.getMessage());
            return acornOrderSeqResult;
        }
        //检查配额
        if(acornSession.getOrderCount().compareTo(acornOrderExtService.getMaxCount())>=0)
        {
            acornOrderSeqResult.setSucc(false);
            acornOrderSeqResult.setMessage("当前获取的订单编号数量已达最大配额，请稍后获取");

            logger.error(acornOrderSeqResult.getMessage());
            return acornOrderSeqResult;
        }

        acornOrderSeqResult.setOrderId(acornOrderExtService.getOrderId());

        acornSession.setOrderCount(acornSession.getOrderCount()+1);
        acornSessionService.refreshSession(acornSession);
        return acornOrderSeqResult;
    }

    private AcornResult checkUserLogin(String strIp, AcornUserToken acornUserToken, AcornSession acornSession)
    {
        AcornResult acornResult=new AcornResult();

        if (!AcornUserConfig.userConfigInfo.containsKey(acornUserToken.getUserName())) {
            acornResult.setSucc(false);
            acornResult.setMessage("用户名不存在");

            return acornResult;
        }

        AcornSession acornSession1=acornSessionService.getSession(acornUserToken.getToken());
        if(acornSession1==null)
        {
            acornResult.setSucc(false);
            acornResult.setMessage("用户未登陆");
            return acornResult;
        }

        if(!strIp.equals(acornSession1.getLoginIp())||!acornUserToken.getUserName().equals(acornSession1.getUserName()))
        {
            acornResult.setSucc(false);
            acornResult.setMessage("登陆信息错误");
            return acornResult;
        }

        if(!DateUtils.isSameDay(acornSession1.getLoginDate(),new Date()))
        {
            acornResult.setSucc(false);
            acornResult.setMessage("登陆信息已过期，请重新登陆");

            acornSessionService.invalidSession(acornSession1.getSessionId());
            return acornResult;
        }

        BeanUtils.copyProperties(acornSession1,acornSession);
        return acornResult;
    }

    @Autowired
    protected AcornAreaDao acornAreaDao;

    @Autowired
    protected AcornAddressService acornAddressService;

    @RequestMapping(value = "/queryAreas",method= RequestMethod.POST)
    @ResponseBody
    public AcornAreaResult queryAreaNames(HttpServletRequest request, @RequestBody AcornAddressInfo acornAddressInfo)
    {
        logger.info("call queryAreaNames begin");
        AcornAreaResult acornAreaResult=new AcornAreaResult();

        if(acornAddressInfo==null||StringUtils.isBlank(acornAddressInfo.getUserName())||StringUtils.isBlank(acornAddressInfo.getToken()))
        {
            acornAreaResult.setSucc(false);
            acornAreaResult.setMessage("未提供用户数据");

            logger.error(acornAreaResult.getMessage());
            return acornAreaResult;
        }

        if(acornAddressInfo.getStartPos()==null||acornAddressInfo.getPageSize()==null)
        {
            acornAreaResult.setSucc(false);
            acornAreaResult.setMessage("未提供分页参数");

            logger.error(acornAreaResult.getMessage());
            return acornAreaResult;
        }
        if(acornAddressInfo.getPageSize().intValue()>10)
        {
            acornAreaResult.setSucc(false);
            acornAreaResult.setMessage("每页最多支持10条记录");

            logger.error(acornAreaResult.getMessage());
            return acornAreaResult;
        }

        //获取请求IP
        String strIp=getIpAddr(request);
        logger.info("remote ip:"+strIp);

        //检查用户登录信息
        AcornSession acornSession=new AcornSession();
        AcornResult acornResult=checkUserLogin(strIp, acornAddressInfo, acornSession);
        if(acornResult.isSucc()==false)
        {
            acornAreaResult.setSucc(false);
            acornAreaResult.setMessage(acornResult.getMessage());

            logger.error(acornAreaResult.getMessage());
            return acornAreaResult;
        }

        List<AreaAll> areaAllList= acornAreaDao.getPageAreaByCountryId(acornAddressInfo.getCountyId(), acornAddressInfo.getStartPos(), acornAddressInfo.getPageSize());
        if(areaAllList!=null)
        {
            for(AreaAll areaAll:areaAllList)
            {
                acornAreaResult.getAreas().add(areaAll.getAreaname());
            }
        }
        return acornAreaResult;
    }

    @RequestMapping(value = "/queryCountys",method= RequestMethod.POST)
    @ResponseBody
    public AcornCountryResult queryCountyNames(HttpServletRequest request, @RequestBody AcornCountryInfo acornCountryInfo)
    {
        logger.info("call queryCountyNames begin");
        AcornCountryResult acornCountryResult=new AcornCountryResult();

        if(acornCountryInfo==null||StringUtils.isBlank(acornCountryInfo.getUserName())||StringUtils.isBlank(acornCountryInfo.getToken()))
        {
            acornCountryResult.setSucc(false);
            acornCountryResult.setMessage("未提供用户数据");

            logger.error(acornCountryResult.getMessage());
            return acornCountryResult;
        }

        //获取请求IP
        String strIp=getIpAddr(request);
        logger.info("remote ip:"+strIp);

        //检查用户登录信息
        AcornSession acornSession=new AcornSession();
        AcornResult acornResult=checkUserLogin(strIp, acornCountryInfo, acornSession);
        if(acornResult.isSucc()==false)
        {
            acornCountryResult.setSucc(false);
            acornCountryResult.setMessage(acornResult.getMessage());

            logger.error(acornCountryResult.getMessage());
            return acornCountryResult;
        }


        //根据省、城市名称来获取id，最后缓存
        String provId=acornAddressService.findProviceByName(acornCountryInfo.getProvinceName());
        if(StringUtils.isNotBlank(provId))
        {
            Integer cityId=acornAddressService.findCityByName(provId,acornCountryInfo.getCityName());
            if(cityId!=null)
            {
                List<AcornCountry> nameList=acornAddressService.findCountysByCity(cityId);
                if(nameList!=null)
                    acornCountryResult.setCountrys(nameList);
            }
        }
        return acornCountryResult;
    }
}
