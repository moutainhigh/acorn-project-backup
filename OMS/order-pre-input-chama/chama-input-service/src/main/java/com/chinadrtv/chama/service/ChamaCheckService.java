package com.chinadrtv.chama.service;

import java.util.List;

import com.chinadrtv.chama.bean.PreTrademap;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-1
 * Time: 下午5:07
 * To change this template use File | Settings | File Templates.
 */
public interface ChamaCheckService {

    //smooks转换xml文件到PreTrademap对象中
    List<PreTrademap> getOrderInfo(String txml);
    //转换listmap的值到ListPretare
    List<PreTradeDto>  getPretrademapto(List<PreTrademap> trademapList);
    //数据转换保存到数据库
    PreTradeLotDto routeTradeInfo(String customerId, List<PreTrademap> trademapList);
    //检测当前导入的茶马订单信息是否正确
    List<String> orderFeedback(List<PreTrademap> tradeList);
    //检查茶马订单的信息
    List<String> checkChama(List<PreTrademap> tradeList);

}
