package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.BaseConfigDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.service.*;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.agent.CallHist;
import com.chinadrtv.erp.model.marketing.*;
import com.chinadrtv.erp.sales.dto.PhoneHookDto;
import com.chinadrtv.erp.sales.service.CommonService;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.service.CallHistService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service 
public class CommonServiceImpl implements CommonService{

    private static final Logger logger = Logger.getLogger(CommonServiceImpl.class.getName());
	

	/**
	 * 客户挂机
	 * 1.更新线索的状态
	 * 2.
	 * 
	 */
	@Override
	public Map phoneHook(PhoneHookDto dto) throws ServiceException {
     return new HashMap();
	}
	
	
	
	public Map fetchMessage(PhoneHookDto dto) throws ServiceException {


		

				return new HashMap();

	}


    public void fetchMessageCallBack(PhoneHookDto dto){


    }

      public Boolean interrupt(PhoneHookDto dto) throws ServiceException {



      return true;
    }

    @Override
    public List<BaseConfig> getNormalPhone() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public void saveCtiInfo(PhoneHookDto dto) throws ServiceException{

    }

    public void saveCallbackCtiInfo(PhoneHookDto dto) throws ServiceException{
    }
}
