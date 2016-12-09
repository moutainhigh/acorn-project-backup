package com.chinadrtv.erp.sales.util;

import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-2-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Aspect
public class ControllerLogTracer {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ControllerLogTracer.class);

    private ObjectMapper objectMapper;

    public ControllerLogTracer()
    {
        objectMapper=new ObjectMapper();
        try{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            objectMapper.setDateFormat(simpleDateFormat);
        }catch (Exception exp)
        {
            logger.error("init controller log error:",exp);
        }
    }


    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerBean() {}

    @Pointcut("execution(* com.chinadrtv.erp.sales.controller.*.*(..))")
    public void methodPointcut() {}

    @Before("controllerBean() && methodPointcut() ")
    public void beforeMethodInControllerClass(JoinPoint jp) {
        if(logger!=null&&logger.isDebugEnabled())
        {
            try{
                if(jp!=null&&(jp.getSignature() instanceof MethodSignature))
                {
                    MethodSignature methodSignature=(MethodSignature)jp.getSignature();
                    log(methodSignature,jp.getArgs());
                }
            }catch (Exception e)
            {
                logger.error("****log unkown error:", e);
            }
        }
    }

    public List<String> getPrefixInludeTypeList() {
        return prefixInludeTypeList;
    }

    public void setPrefixInludeTypeList(List<String> prefixInludeTypeList) {
        this.prefixInludeTypeList = prefixInludeTypeList;
    }

    private List<String> prefixInludeTypeList;

    private boolean isLogType(Class clazz)
    {
        if(clazz.isPrimitive())
            return true;
        if(clazz.getName().startsWith("java.lang."))
            return true;

        if(clazz.getName().startsWith("java.math."))
            return true;
        if(clazz.getName().startsWith("com.chinadrtv.erp"))
            return true;

        //配置中读取的类型
        if(prefixInludeTypeList!=null)
        {
            for(String item :prefixInludeTypeList)
            {
                if(clazz.getName().startsWith(item))
                    return true;
            }
        }
        return false;
    }

    /**
     * 记录有业务参数的方法以及业务数据
     * 目前只记录chinadrtv包里面的类型数据以及基本类型数据
     * @param methodSignature
     * @param datas
     */
    private void log(MethodSignature methodSignature,Object[] datas)
    {
        if(methodSignature.getParameterTypes()!=null&&methodSignature.getParameterTypes().length>0)
        {
            boolean bLog=false;
            for(int i=0;i<methodSignature.getParameterTypes().length;i++)
            {
                Class paramterClass=methodSignature.getParameterTypes()[i];
                if(this.isLogType(paramterClass))
                {
                    if(bLog==false)
                    {
                        String strUsr="";
                        bLog=true;
                        try{
                            AgentUser agentUser=SecurityHelper.getLoginUser();
                            if(agentUser!=null)
                            {
                                strUsr=" -current user id:"+agentUser.getUserId();
                            }
                        }catch (Exception exp)
                        {
                            logger.error("fetch login user error:",exp);
                        }
                        logger.debug(methodSignature.getDeclaringType()+"."+methodSignature.getName()+strUsr);
                    }
                    //
                    if(datas[i]==null)
                    {
                        logger.debug(methodSignature.getParameterNames()[i]+ ":<null>");
                    }
                    else
                    {
                        if(paramterClass.isPrimitive())
                        {
                            logger.debug(methodSignature.getParameterNames()[i]+ ":"+String.valueOf(datas[i]));
                        }
                        else
                        {
                            try{
                                logger.debug(methodSignature.getParameterNames()[i]+ ":"+objectMapper.writeValueAsString(datas[i]));
                            }catch (Exception exp)
                            {
                                logger.error("---***--- controller log error:",exp);
                            }
                        }
                    }

                }
            }
        }
    }
}