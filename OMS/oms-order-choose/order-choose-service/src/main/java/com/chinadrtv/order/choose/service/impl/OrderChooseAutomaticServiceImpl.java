package com.chinadrtv.order.choose.service.impl;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.OrderAssignLog;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.constant.OrderAssignConstants;
import com.chinadrtv.erp.tc.core.constant.OrderCode;
import com.chinadrtv.erp.tc.core.constant.cache.OrderAssignErrorCode;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.tc.core.model.OrderAutoChooseInfo;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.service.OrderhistCompanyAssignService;
import com.chinadrtv.order.choose.dal.dao.OrderChooseAutomaticDao;
import com.chinadrtv.order.choose.dal.dao.OrderChooseDao;
import com.chinadrtv.order.choose.dal.dao.ShipmentHeader2Dao;
import com.chinadrtv.order.choose.dal.model.OrderChooseQueryParm;
import com.chinadrtv.order.choose.service.OrderAssignLogService;
import com.chinadrtv.order.choose.service.OrderChooseAutomaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 挑单_后台自动挑单服务
 *
 * @author zhaizhanyi
 */
@Service
public class OrderChooseAutomaticServiceImpl implements OrderChooseAutomaticService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderChooseAutomaticServiceImpl.class);

    @Autowired
    private OrderChooseAutomaticDao orderChooseAutomaticDao;

    @Autowired
    private ShipmentHeader2Dao shipmentHeader2Dao;


    /**
     * 自动挑单
     *
     * @param param 自动挑单执行的条件
     */
    public int automaticChooseOrder(Map<String, Parameter> param) {
        int result = orderChooseAutomaticDao.automaticChooseOrder(param);
        result = orderChooseAutomaticDao.automaticChooseShipment(param);
        return result;
    }



    public int automaticChooseShipment(Map<String, Parameter> param) {
        return orderChooseAutomaticDao.automaticChooseShipment(param);
    }



    public int automaticChooseOrderForOldData(Map<String, Parameter> param) {
        return orderChooseAutomaticDao.automaticChooseOrderForOldData(param);
    }


    public void automaticOrderForOldData(Map<String, Parameter> param, Integer limit) {

        Map<String, Parameter> param1 = new HashMap<String, Parameter>();
        param1.put("oldStatus", param.get("oldStatus"));
        param1.put("isAssign", param.get("isAssign"));
        param1.put("days", param.get("days"));
        param1.put("manualActing", param.get("manualActing"));
        List<Orderhist> orderList = orderChooseAutomaticDao.queryOrderhist(param1, limit);

        if (!orderList.isEmpty()) {
            Map<String, Parameter> param2 = new HashMap<String, Parameter>();
            param2.put("newStatus", param.get("newStatus"));
            param2.put("sendDate", param.get("sendDate"));
            Parameter orderPara = null;
            for (Orderhist order : orderList) {
                orderPara = new ParameterString("orderId", order.getOrderid());
                param2.put("orderId", orderPara);
                orderChooseAutomaticDao.updateOderhistById(param2);
            }
        }

    }


    public void automaticShipmentHeader(Map<String, Parameter> param, Integer limit) {

        Map<String, Parameter> param1 = new HashMap<String, Parameter>();
        param1.put("oldStatus", param.get("oldStatus"));
        param1.put("days", param.get("days"));
        param1.put("manualActing", param.get("manualActing"));
        List<ShipmentHeader> shipList = shipmentHeader2Dao.queryShipment(param1, limit);

        if (!shipList.isEmpty()) {
            Map<String, Parameter> param2 = new HashMap<String, Parameter>();
            param2.put("newStatus", param.get("newStatus"));
            param2.put("sendDate", param.get("sendDate"));
            Parameter idPara = null;

            Map<String, Parameter> param3 = new HashMap<String, Parameter>();
            param3.put("newStatus", param.get("newStatus"));
            param3.put("sendDate", param.get("sendDate"));
            Parameter orderPara = null;

            for (ShipmentHeader shipment : shipList) {

                orderPara = new ParameterString("orderId", shipment.getOrderId());
                param3.put("orderId", orderPara);
                orderChooseAutomaticDao.updateOderhistById(param3);


                idPara = new ParameterLong("id", shipment.getId());
                param2.put("id", idPara);
                shipmentHeader2Dao.updateShipmentHeaderById(param2);
            }
        }

    }



























    @Autowired
    private OrderChooseDao orderDao;

    @Autowired
    private OrderhistDao orderhistDao;

    private final static String usr="autoChoose";

    @Autowired
    private OrderhistCompanyAssignService orderhistCompanyAssignService;

    @Autowired
    private OrderAssignLogService orderAssignLogService;

    @Autowired
    private RestTemplate template;

    @Value("${TC_DELIVER_IDUP_URL}")
    private String tcUrl;

    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    public List<OrderhistAssignInfo> automaticAssignOrder(OrderChooseQueryParm queryParm) {
        logger.debug("begin select order");
        List<Order> orderList = orderDao.findOrderAutoChoose(queryParm);
        logger.debug("end select order");
        if (orderList != null && orderList.size()>0) {
            logger.debug("begin assign order");
            return orderhistCompanyAssignService.findOrderMatchRule(orderList);
        }

        return new ArrayList<OrderhistAssignInfo>();
    }

    public void assignOrderCompany(OrderhistAssignInfo orderhistAssignInfo) {
        //添加日志
        OrderAssignLog orderAssignLog=new OrderAssignLog();
        orderAssignLog.setAssignType(2);
        orderAssignLog.setCodeId(orderhistAssignInfo.getErrorId());
        orderAssignLog.setCodeDsc(orderhistAssignInfo.getErrorMsg());
        orderAssignLog.setCrUser("");
        orderAssignLog.setMdDate(new Date());
        orderAssignLog.setOrderId(orderhistAssignInfo.getOrder().getOrderid());
        orderAssignLog.setRegulationDsc(orderhistAssignInfo.getRuleName());
        Integer ruleId=Integer.parseInt(orderhistAssignInfo.getRuleId()!=null?orderhistAssignInfo.getRuleId():"0");
        if(ruleId>0)
        {
            orderAssignLog.setRegulationId(orderhistAssignInfo.getRuleId());
        }

        if(orderhistAssignInfo.isSucc())
        {
            //目前直接调用tc服务
            OrderAutoChooseInfo orderAutoChooseInfo=new OrderAutoChooseInfo();
            orderAutoChooseInfo.setId(orderhistAssignInfo.getOrder().getId());
            orderAutoChooseInfo.setOrderId(orderhistAssignInfo.getOrder().getOrderid());
            orderAutoChooseInfo.setVersion(orderhistAssignInfo.getOrder().getVersion());
            orderAutoChooseInfo.setEntityId(orderhistAssignInfo.getEntityId() != null ? Long.parseLong(orderhistAssignInfo.getEntityId()) : null);
            orderAutoChooseInfo.setWarehouseId(orderhistAssignInfo.getWarehouseId());
            logger.debug("call url:"+tcUrl);
            logger.debug("begin call tc service:"+orderhistAssignInfo.getOrder().getOrderid());
            OrderReturnCode returnInfo=template.postForObject(tcUrl,orderAutoChooseInfo, OrderReturnCode.class);
            logger.debug("end call tc service:"+orderhistAssignInfo.getOrder().getOrderid());
            if(!OrderCode.SUCC.equals(returnInfo.getCode()))
            {
                //订单发生修改了，那么重试
                if(OrderCode.ORDER_HAVE_MODIFY.equals(returnInfo.getCode()))
                {
                    logger.error("order is modify:"+orderhistAssignInfo.getOrder().getOrderid());
                     orderhistAssignInfo.setSucc(true);
                     return;
                }
                else
                {
                    //记录错误
                    logger.error("call tc service error:"+returnInfo.getCode()+"-"+orderhistAssignInfo.getOrder().getOrderid());
                    orderAssignLog.setCodeId(OrderAssignErrorCode.OREDR_CHOOSE_SET_ERROR);
                    orderAssignLog.setCodeDsc(returnInfo.getCode()+":"+returnInfo.getDesc());
                    orderAssignLog.setRegulationId(null);
                    orderAssignLog.setRegulationDsc(null);
                    //new code start
                    orderhistAssignInfo.setSucc(false);
                    //new code end
                }
            }
        }

        //不匹配成功的统一打标记，防止重复循环读取
        if(!orderhistAssignInfo.isSucc())
        {
            Order order = orderhistDao.get(orderhistAssignInfo.getOrder().getId());
            if(OrderAssignErrorCode.MATCH_SPEC_CONDITION.equals(orderhistAssignInfo.getErrorId()))
            {
                //并且处理发运单
                String shipmentId=order.getOrderid()+String.format("V%03d", order.getRevision());
                ShipmentHeader shipmentHeader= shipmentHeaderDao.getByShipmentId(shipmentId);
                if(shipmentHeader!=null)
                {
                    shipmentHeader.setEntityId(orderhistAssignInfo.getEntityId());
                    shipmentHeader.setMddt(new Date());
                    shipmentHeader.setMdusr("SYSTEM");
                }

                //满足特定条件，那么更新订单为手工处理，随后可以再次处理
                order.setIsassign(OrderAssignConstants.HAND_ASSIGN);
                order.setEntityid(orderhistAssignInfo.getEntityId());
                order.setMailtype(orderhistAssignInfo.getMailType());
                logger.debug("match spec condition - entity id:"+orderhistAssignInfo.getEntityId());
            }
            else
            {
                if(OrderAssignConstants.HAND_ASSIGN.equals(order.getIsassign()))
                {
                    order.setIsassign(OrderAssignConstants.RETRY_HAND_ASSIGN);
                }
                else
                {
                    order.setIsassign(OrderAssignConstants.RETRY_SIMPLE_ASSIGN);
                }
            }
            orderhistDao.saveOrUpdate(order);
        }
        //保存日志
        orderAssignLogService.saveOrUpateOrderAssignLog(orderAssignLog);
    }

    /**
     * 发生系统级错误的时候，直接记录日志
     */
    public void saveErrorOrderAndLog(Long orderId, String orderNum,String assignFlag, String errorMsg)
    {
        String newAssignFlag="";
        if(OrderAssignConstants.HAND_ASSIGN.equals(assignFlag))
        {
            newAssignFlag= OrderAssignConstants.RETRY_HAND_ASSIGN;
        }
        else
        {
            newAssignFlag= OrderAssignConstants.RETRY_SIMPLE_ASSIGN;
        }
        logger.debug("begin direct update order assign");
        orderDao.execUpdate("update Order set isassign=:isassign where id=:id",
                new ParameterString("isassign",newAssignFlag),new ParameterLong("id",orderId));
        logger.debug("end direct update order assign");
        //记录错误日志
        OrderAssignLog orderAssignLog=new OrderAssignLog();
        orderAssignLog.setAssignType(2);
        orderAssignLog.setCodeId(OrderAssignErrorCode.SYSTEM_ERROR);
        orderAssignLog.setCodeDsc(errorMsg);
        orderAssignLog.setCrUser("");
        orderAssignLog.setMdDate(new Date());
        orderAssignLog.setOrderId(orderNum);
        orderAssignLogService.saveOrUpateOrderAssignLog(orderAssignLog);
    }

}
