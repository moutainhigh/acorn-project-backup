package com.chinadrtv.chama.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinadrtv.service.oms.PreTradeImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.chama.bean.PreTrademap;
import com.chinadrtv.chama.service.ChamaCheckService;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.util.HttpParamUtils;


/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-10-24
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping({ "/order" })
public class ChamaOrderInputController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ChamaOrderInputController.class);

    @Autowired
    private ChamaCheckService chamaCheckService;
    @Autowired
    private PreTradeImportService preTradeLotService;

    @RequestMapping(value = "/input",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void transformChamaOrderMessage(HttpServletRequest request,HttpServletResponse res)
    {
        StringBuilder response = new StringBuilder();
        try{
            logger.info("com.chinadrtv.chama.controller.ChamaOrderInputController receive http param begin");
            //获取post 数据流
            ServletInputStream stream=request.getInputStream();
            byte[] bytes= new byte[request.getContentLength()];
            stream.read(bytes);
            String params=new String(bytes,"utf-8");
            stream.close();
            logger.info("get post params:"+params);
            List<PreTrademap> tradeList = null;
            //获取订单列表
            if(params != null){
                //绘制当前导入茶马订单的xml
                Map<String, String> paraMap = HttpParamUtils.getUrlParameters(params);
                logger.info("com.chinadrtv.chama.controller.ChamaOrderInputController receive http param end param="+paraMap.values().toString());
                logger.info("com.chinadrtv.chama.controller.ChamaOrderInputController parse http param begin");
                tradeList = chamaCheckService.getOrderInfo(paraMap.values().toString());
                logger.info("com.chinadrtv.chama.controller.ChamaOrderInputController parse http param end");
            }
            /**
             * 错误消息的list消息集合
             */
             //检查数据的合法性
            logger.info("com.chinadrtv.chama.controller.ChamaOrderInputController check valid begin");
            List<String> errorname = new LinkedList<String>();
            errorname = chamaCheckService.orderFeedback(tradeList);
            logger.info("com.chinadrtv.chama.controller.ChamaOrderInputController check valid: " + errorname.get(0).toString());
            logger.info("com.chinadrtv.chama.controller.ChamaOrderInputController check valid end");
            //如果成功调用前置订单服务
            logger.info("errorname.get(0).toString()="+errorname.get(0).toString());
            if (!errorname.get(0).toString().equals("") && errorname.get(0).toString().equals("013")) {
                logger.info("save begin..");
                String customerId = "茶马古道";
                PreTradeLotDto p = chamaCheckService.routeTradeInfo(customerId, tradeList);
                //数据保存
                preTradeLotService.importPretrades(p);
                logger.info("save success !");
            }
            response.append(errorname.get(1));
            res.getOutputStream().write(response.toString().getBytes("utf-8"));
        }catch (Exception e)
        {
            logger.error("controller error",e);
            response.append("<ops_trades_response>");
            response.append("<ops_trade_id></ops_trade_id>");
            response.append("<agent_trade_id></agent_trade_id>");
            response.append("<result>false</result>");
            response.append("<message_code>012</message_code>");
            response.append("<message>系统异常, 请重试</message>");
            response.append("</ops_trades_response>");
            logger.error("com.chinadrtv.chama.controller.ChamaOrderInputController Exception:" + response.toString());
        }
        logger.info("http end!");
    }

    @ExceptionHandler
    @ResponseBody
    public String handleException(Exception ex, HttpServletRequest request) {
        return ex.getMessage();
    }
}
