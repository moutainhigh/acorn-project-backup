package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.BlackList;
import com.chinadrtv.erp.model.BlackPhone;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.uc.dao.BlackListDao;
import com.chinadrtv.erp.uc.dao.BlackPhoneDao;
import com.chinadrtv.erp.uc.dto.BlackPhoneDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.BlackListService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.PotentialContactPhoneService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackListServiceImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Service
public class BlackListServiceImpl extends GenericServiceImpl<BlackList, Long> implements BlackListService {
    @Autowired
    private BlackListDao blackListDao;

    @Autowired
    private BlackPhoneDao blackPhoneDao;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PhoneService phoneService;

//    @Autowired
//    private OrderService orderService;

    @Autowired
    private PotentialContactPhoneService potentialContactPhoneService;

    @Override
    public void addBlackList(BlackList blackList) {
        blackList.setActive(1);
        blackList.setCreateDate(new Date());
        blackListDao.save(blackList);
        if (isSecondUserAdd(blackList)) {
            if (StringUtils.isNotBlank(blackList.getContactId())) {
                phoneService.addToBlackList(blackList.getPhoneId());
            } else potentialContactPhoneService.addToBlackList(blackList.getPhoneId());
        }
    }

    @Override
    public void addBlackPhone(String customerId, String customerType, LeadInteraction leadInteraction, Long phoneId, String phoneNum) {
        BlackPhone blackPhone = createBlackPhone(phoneNum);
        blackPhone = blackPhoneDao.save(blackPhone);
        BlackList blackList = createBlackList(customerId, customerType, leadInteraction, phoneId, blackPhone.getId());
        blackList.setActive(1);
        blackList.setCreateDate(new Date());
        blackListDao.save(blackList);
    }

    @Override
    public Map<String, Object> queryWithDetails(String phoneNum, Integer addTimes, Integer status, DataGridModel dataGridModel) {
        Map result = new HashMap();
        List<BlackPhoneDto> rows = new ArrayList<BlackPhoneDto>();
        List<BlackPhone> blackPhones = blackPhoneDao.queryPageList(phoneNum, addTimes, status, dataGridModel.getStartRow(), dataGridModel.getRows());
        Integer blackPhonesCount = blackPhoneDao.queryCountPageList(phoneNum, addTimes, status);
        for (BlackPhone blackPhone : blackPhones) {
            List<BlackList> blackLists = blackListDao.findByBlackPhoneId(blackPhone.getId());
            BlackPhoneDto blackPhoneDto = new BlackPhoneDto();
            blackPhoneDto.setBlackPhone(blackPhone);
            blackPhoneDto.setBlackLists(blackLists);
            rows.add(blackPhoneDto);
        }
        result.put("rows", rows);
        result.put("total", blackPhonesCount);
        return result;
    }

    @Override
    public void addToBlackPhone(Long blackPhoneId) {
        BlackPhone blackPhone = blackPhoneDao.get(blackPhoneId);
        blackPhone.setStatus(2);
        blackPhone.setApproveManager(SecurityHelper.getLoginUser().getUserId());
        blackPhone.setApproveDate(new Date());
        blackPhoneDao.saveOrUpdate(blackPhone);
        blackPhoneDao.saveBlackPhoneToCti(blackPhone);
    }

    @Override
    public void removeFromBlackPhone(Long blackPhoneId) {
        BlackPhone blackPhone = blackPhoneDao.get(blackPhoneId);
        blackPhone.setStatus(3);
        blackPhone.setReuseManager(SecurityHelper.getLoginUser().getUserId());
        blackPhone.setReuseDate(new Date());
        blackPhoneDao.saveOrUpdate(blackPhone);

        List<BlackList> blackLists = blackListDao.findByBlackPhoneId(blackPhoneId);
        for (BlackList blackList : blackLists) {
            blackList.setActive(0);
            blackList.setUpdateDate(new Date());
            blackList.setUpdateUser(SecurityHelper.getLoginUser().getUserId());
            blackListDao.save(blackList);
        }
        blackPhoneDao.deleteBlackPhoneFromCti(blackPhone);
    }

    private BlackPhone createBlackPhone(String phoneNum) {
        BlackPhone blackPhone = blackPhoneDao.findByCustomer(phoneNum);
        if (blackPhone != null) {
            blackPhone.setAddTimes(blackPhone.getAddTimes() + 1);
        } else {
            blackPhone = new BlackPhone();
            blackPhone.setAddTimes(1);
            blackPhone.setPhoneNum(phoneNum);
            blackPhone.setStatus(1);
        }
        return blackPhone;
    }

    private BlackList createBlackList(String customerId, String customerType, LeadInteraction leadInteraction, Long phoneId, Long blackPhoneId) {
        if ("2".equals(customerType)) {
            return new BlackList(Long.valueOf(customerId), phoneId, leadInteraction.getId(), SecurityHelper.getLoginUser().getUserId(), SecurityHelper.getLoginUser().getWorkGrp(), blackPhoneId);
        } else {
            return new BlackList(customerId, phoneId, leadInteraction.getId(), SecurityHelper.getLoginUser().getUserId(), SecurityHelper.getLoginUser().getWorkGrp(), blackPhoneId);
        }
    }

//    @Override
//    public void calculateOrderTransferBlackList(Date minOrderCreateDate, Date maxOrderCreateDate) {
//        List<Order> orderList = orderService.findByCreateDateRange(minOrderCreateDate, maxOrderCreateDate);
//        HashSet hs = new HashSet();
//        BigDecimal measureRatio = new BigDecimal("0.2");
//        for (Order order : orderList) {
//            if (hs.contains(order.getContactid())) continue;
//            else {
//                hs.add(order.getContactid());
//                if (orderService.queryOrderCountByContactId(order.getContactid()) < 3) continue;
//                BigDecimal complteRatio = orderService.calculateOrderTransferBlackList(order.getContactid());
//                if (measureRatio.compareTo(complteRatio) > 0) {
//                    BlackList blackList = new BlackList(order.getContactid(), "定时任务");
//                    addBlackList(blackList);
//                }
//            }
//        }
//    }

    private boolean isSecondUserAdd(BlackList blackList) {
        return blackListDao.isSecondUserAdd(blackList);
    }

    @Override
    protected GenericDao<BlackList, Long> getGenericDao() {
        return blackListDao;
    }
}
