package com.chinadrtv.erp.oms.controller;

import static org.junit.Assert.assertTrue;

import com.chinadrtv.erp.constant.TradeSourceConstants;
import com.chinadrtv.erp.core.model.AuditLog;
import com.chinadrtv.erp.core.service.AuditLogService;

import net.sf.json.util.JSONUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.hibernate.StaleObjectStateException;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeCard;
import com.chinadrtv.erp.model.PreTradeCompany;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.oms.service.OrderhistService;
import com.chinadrtv.erp.oms.service.PreTradeCompanyService;
import com.chinadrtv.erp.oms.service.PreTradeDetailService;
import com.chinadrtv.erp.oms.service.PreTradeService;
import com.chinadrtv.erp.oms.service.SourceConfigure;

import com.chinadrtv.erp.oms.util.BeanUtilEx;
import com.chinadrtv.erp.oms.util.HttpUtil;
import com.chinadrtv.erp.oms.util.SaxParseXmlUtil;
import com.chinadrtv.erp.oms.util.StringUtil;
import com.chinadrtv.erp.oms.util.XmlBeanConvertUtil;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 前置订单处理
 * 
 * @author haoleitao
 *
 */

@Controller
public class OrderPreprocessingController {

	private static final Logger log = LoggerFactory
			.getLogger(OrderPreprocessingController.class);
	@Autowired
	private PreTradeService preTradeService;

	@Autowired
	private PreTradeDetailService preTradeDetailService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private OrderhistService orderhistService;

	@Autowired
	private SourceConfigure sourceConfigure;

	@Autowired
	private AuditLogService auditLogService;
	
	@Autowired
	private PreTradeCompanyService preTradeCompanyService;
	
	
	
	@RequestMapping(value = "orderPreprocessing/test", method = RequestMethod.POST)
	public void test(HttpServletRequest request, HttpServletResponse response){
		String message = "{\"rows\":[{\"id\":165095,\"version\":2,\"sourceId\":9,\"tradeId\":\"297802088662637\",\"opsTradeId\":\"297802088662637\",\"alipayTradeId\":\"2013022100001000120037537477\",\"buyerAlipayId\":\"13237027451\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"TAOBAO\",\"shippingType\":\"express\",\"tmsCode\":\"6\",\"crdt\":\"2013-02-21 15:40:01\",\"outCrdt\":\"2013-02-21 15:32:36\",\"buyerNick\":\"夏天梁凉\",\"receiverName\":\"王萍\",\"receiverProvince\":\"江西\",\"receiverCity\":\"九江市\",\"receiverCounty\":\"武宁县\",\"receiverAddress\":\"玉景花园5栋1单元202室\",\"receiverMobile\":\"13479883827\",\"receiverPostCode\":\"332300\",\"shippingFee\":0.0,\"payment\":378.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119430603\",\"discountFee\":20.0,\"commissionFee\":0.0,\"isVaid\":true,\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:25:04\",\"createDate\":\"2013-02-21 15:40:03\",\"fromAndDate\":\"来源：TAOBAO</br>付款时间:2013-02-21 15:32:36.0\",\"info\":\"橡果国际益尔健懒人运动收腹机卓越版家用仰卧起坐减肥健身器材(数量：1)\",\"sendInfo\":\"快递公司:上海21CN商城</br>发货地址:江西九江市武宁县玉景花园5栋1单元202室\",\"orderMoney\":\"产品金额:378.0</br>运费:0.0</br>支付金额:378.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"王萍</br>手机: 13479883827</br>订购账号: 夏天梁凉\",\"refund\":\"0,0\"},{\"id\":165089,\"version\":2,\"sourceId\":9,\"tradeId\":\"195772745268219\",\"opsTradeId\":\"195772745268219\",\"alipayTradeId\":\"2013022100001000870037168716\",\"buyerAlipayId\":\"15851239363\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"TAOBAO\",\"shippingType\":\"express\",\"tmsCode\":\"6\",\"crdt\":\"2013-02-21 15:30:03\",\"outCrdt\":\"2013-02-21 15:21:08\",\"buyerNick\":\"hongshufufu\",\"receiverName\":\"王佳佳\",\"receiverProvince\":\"江苏\",\"receiverCity\":\"南通市\",\"receiverCounty\":\"海门市\",\"receiverAddress\":\"悦来镇工业园区 新城西路99号  三信渔具\",\"receiverMobile\":\"13376103555\",\"receiverPostCode\":\"226131\",\"shippingFee\":0.0,\"payment\":378.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119396788\",\"discountFee\":20.0,\"commissionFee\":0.0,\"isVaid\":true,\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:25:03\",\"createDate\":\"2013-02-21 15:30:06\",\"fromAndDate\":\"来源：TAOBAO</br>付款时间:2013-02-21 15:21:08.0\",\"info\":\"橡果国际益尔健懒人运动收腹机卓越版家用仰卧起坐减肥健身器材(数量：1)\",\"sendInfo\":\"快递公司:上海21CN商城</br>发货地址:江苏南通市海门市悦来镇工业园区新城西路99号三信渔具\",\"orderMoney\":\"产品金额:378.0</br>运费:0.0</br>支付金额:378.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"王佳佳</br>手机: 13376103555</br>订购账号: hongshufufu\",\"refund\":\"0,0\"},{\"id\":165085,\"version\":2,\"sourceId\":9,\"tradeId\":\"297791725182187\",\"opsTradeId\":\"297791725182187\",\"alipayTradeId\":\"2013022100001000270039597284\",\"buyerAlipayId\":\"15215046996\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"TAOBAO\",\"shippingType\":\"express\",\"tmsCode\":\"6\",\"crdt\":\"2013-02-21 15:20:04\",\"outCrdt\":\"2013-02-21 15:15:04\",\"buyerNick\":\"小田田zcq\",\"receiverName\":\"王建森\",\"receiverProvince\":\"山西\",\"receiverCity\":\"大同市\",\"receiverCounty\":\"城区\",\"receiverAddress\":\"北辰西苑2号楼2单元\",\"receiverMobile\":\"13734186000\",\"receiverPostCode\":\"037000\",\"shippingFee\":0.0,\"payment\":298.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119396787\",\"discountFee\":0.0,\"commissionFee\":0.0,\"isVaid\":true,\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:45:04\",\"createDate\":\"2013-02-21 15:20:08\",\"fromAndDate\":\"来源：TAOBAO</br>付款时间:2013-02-21 15:15:04.0\",\"info\":\"橡果国际官方正品背背佳V姿专业版儿童青少年矫姿带新款上市(数量：1)\",\"sendInfo\":\"快递公司:上海21CN商城</br>发货地址:山西大同市城区北辰西苑2号楼2单元\",\"orderMoney\":\"产品金额:298.0</br>运费:0.0</br>支付金额:298.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"王建森</br>手机: 13734186000</br>订购账号: 小田田zcq\",\"refund\":\"0,0\"},{\"id\":165087,\"version\":2,\"sourceId\":9,\"tradeId\":\"227732751249476\",\"opsTradeId\":\"227732751249476\",\"alipayTradeId\":\"2013022100001000820036419628\",\"buyerAlipayId\":\"18680871375\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"TAOBAO\",\"shippingType\":\"express\",\"tmsCode\":\"6\",\"crdt\":\"2013-02-21 15:20:05\",\"outCrdt\":\"2013-02-21 15:11:58\",\"buyerNick\":\"被拉长的时光\",\"receiverName\":\"余茂光\",\"receiverProvince\":\"重庆\",\"receiverCity\":\"重庆市\",\"receiverCounty\":\"渝北区\",\"receiverAddress\":\"紫荆路佳华世纪C区乐车坊\",\"receiverMobile\":\"15102351656\",\"receiverPostCode\":\"420040\",\"shippingFee\":0.0,\"payment\":99.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119396786\",\"discountFee\":0.0,\"commissionFee\":0.0,\"isVaid\":true,\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:50:04\",\"createDate\":\"2013-02-21 15:20:08\",\"fromAndDate\":\"来源：TAOBAO</br>付款时间:2013-02-21 15:11:58.0\",\"info\":\"背背佳e版成人男士驼背矫正带女士收腹挺胸矫姿带天猫正品发票(数量：1)\",\"sendInfo\":\"快递公司:上海21CN商城</br>发货地址:重庆重庆市渝北区紫荆路佳华世纪C区乐车坊\",\"orderMoney\":\"产品金额:99.0</br>运费:0.0</br>支付金额:99.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"余茂光</br>手机: 15102351656</br>订购账号: 被拉长的时光\",\"refund\":\"0,0\"},{\"id\":165084,\"version\":4,\"sourceId\":9,\"tradeId\":\"297785081156646\",\"opsTradeId\":\"297785081156646\",\"alipayTradeId\":\"2013022100001000320036640906\",\"buyerAlipayId\":\"15848513152\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"TAOBAO\",\"shippingType\":\"express\",\"crdt\":\"2013-02-21 15:20:05\",\"outCrdt\":\"2013-02-21 15:10:45\",\"invoiceComment\":\"0\",\"buyerNick\":\"tb_8513152\",\"receiverName\":\"董文旭\",\"receiverProvince\":\"内蒙古\",\"receiverCity\":\"通辽市\",\"receiverCounty\":\"霍林郭勒市\",\"receiverAddress\":\"振兴路北处长楼101栋\",\"receiverMobile\":\"15848513152\",\"receiverPostCode\":\"029200\",\"shippingFee\":0.0,\"payment\":99.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119396784\",\"discountFee\":0.0,\"commissionFee\":0.0,\"isVaid\":false,\"errMsg\":\"省份:[内蒙古自治区]不匹配;\",\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:55:11\",\"createDate\":\"2013-02-21 15:20:08\",\"fromAndDate\":\"来源：TAOBAO</br>付款时间:2013-02-21 15:10:45.0\",\"info\":\"背背佳e版成人男士驼背矫正带女士收腹挺胸矫姿带天猫正品发票(数量：1)\",\"sendInfo\":\"快递公司:上海邮购</br>发货地址:内蒙古通辽市霍林郭勒市振兴路北处长楼101栋\",\"orderMoney\":\"产品金额:99.0</br>运费:0.0</br>支付金额:99.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"董文旭</br>手机: 15848513152</br>订购账号: tb_8513152\",\"refund\":\"0,0\"},{\"id\":165082,\"version\":2,\"sourceId\":9,\"tradeId\":\"195768541435837\",\"opsTradeId\":\"195768541435837\",\"alipayTradeId\":\"2013022100001000040036574865\",\"buyerAlipayId\":\"13623109378\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"TAOBAO\",\"shippingType\":\"express\",\"tmsCode\":\"6\",\"crdt\":\"2013-02-21 15:10:04\",\"outCrdt\":\"2013-02-21 15:03:52\",\"buyerNick\":\"airuzhishiu159\",\"receiverName\":\"赵新丽\",\"receiverProvince\":\"河北\",\"receiverCity\":\"邯郸市\",\"receiverCounty\":\"邱县\",\"receiverAddress\":\"园丁苑小区3号楼4单元402室\",\"receiverMobile\":\"13932049656\",\"receiverPostCode\":\"057450\",\"shippingFee\":0.0,\"payment\":378.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119396785\",\"discountFee\":20.0,\"commissionFee\":0.0,\"isVaid\":true,\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:55:07\",\"createDate\":\"2013-02-21 15:10:06\",\"fromAndDate\":\"来源：TAOBAO</br>付款时间:2013-02-21 15:03:52.0\",\"info\":\"橡果国际益尔健懒人运动收腹机卓越版家用仰卧起坐减肥健身器材(数量：1)\",\"sendInfo\":\"快递公司:上海21CN商城</br>发货地址:河北邯郸市邱县园丁苑小区3号楼4单元402室\",\"orderMoney\":\"产品金额:378.0</br>运费:0.0</br>支付金额:378.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"赵新丽</br>手机: 13932049656</br>订购账号: airuzhishiu159\",\"refund\":\"0,0\"},{\"id\":165076,\"version\":2,\"sourceId\":9,\"tradeId\":\"297774683303609\",\"opsTradeId\":\"297774683303609\",\"alipayTradeId\":\"2013022100001000900040649711\",\"buyerAlipayId\":\"15926512691\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"WAP\",\"shippingType\":\"express\",\"tmsCode\":\"6\",\"crdt\":\"2013-02-21 14:50:07\",\"outCrdt\":\"2013-02-21 14:45:20\",\"buyerNick\":\"宇你同行1989\",\"receiverName\":\"赵晶晶\",\"receiverProvince\":\"湖北\",\"receiverCity\":\"荆州市\",\"receiverCounty\":\"沙市区\",\"receiverAddress\":\"春风小区5栋\",\"receiverMobile\":\"15926512691\",\"receiverPostCode\":\"434000\",\"shippingFee\":0.0,\"payment\":99.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119391528\",\"discountFee\":0.0,\"commissionFee\":0.0,\"isVaid\":true,\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:50:04\",\"createDate\":\"2013-02-21 14:50:08\",\"fromAndDate\":\"来源：WAP</br>付款时间:2013-02-21 14:45:20.0\",\"info\":\"背背佳e版成人男士驼背矫正带女士收腹挺胸矫姿带天猫正品发票(数量：1)\",\"sendInfo\":\"快递公司:上海21CN商城</br>发货地址:湖北荆州市沙市区春风小区5栋\",\"orderMoney\":\"产品金额:99.0</br>运费:0.0</br>支付金额:99.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"赵晶晶</br>手机: 15926512691</br>订购账号: 宇你同行1989\",\"refund\":\"0,0\"},{\"id\":165073,\"version\":2,\"sourceId\":9,\"tradeId\":\"297772567070302\",\"opsTradeId\":\"297772567070302\",\"alipayTradeId\":\"2013022100001000010036696744\",\"buyerAlipayId\":\"13572400133\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"TAOBAO\",\"shippingType\":\"express\",\"tmsCode\":\"6\",\"crdt\":\"2013-02-21 14:50:07\",\"outCrdt\":\"2013-02-21 14:44:22\",\"buyerNick\":\"完美嘀幸福\",\"receiverName\":\"王婧\",\"receiverProvince\":\"陕西\",\"receiverCity\":\"咸阳市\",\"receiverCounty\":\"渭城区\",\"receiverAddress\":\"人民路东段80号3号楼3层（二院东边）\",\"receiverMobile\":\"13379102346\",\"receiverPhone\":\"029-33258246\",\"receiverPostCode\":\"712000\",\"shippingFee\":0.0,\"payment\":378.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119391527\",\"discountFee\":20.0,\"commissionFee\":0.0,\"isVaid\":true,\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:25:06\",\"createDate\":\"2013-02-21 14:50:08\",\"fromAndDate\":\"来源：TAOBAO</br>付款时间:2013-02-21 14:44:22.0\",\"info\":\"橡果国际益尔健懒人运动收腹机卓越版家用仰卧起坐减肥健身器材(数量：1)\",\"sendInfo\":\"快递公司:上海21CN商城</br>发货地址:陕西咸阳市渭城区人民路东段80号3号楼3层（二院东边）\",\"orderMoney\":\"产品金额:378.0</br>运费:0.0</br>支付金额:378.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"王婧</br>手机: 13379102346</br>订购账号: 完美嘀幸福\",\"refund\":\"0,0\"},{\"id\":165068,\"version\":2,\"sourceId\":9,\"tradeId\":\"195698814257914\",\"opsTradeId\":\"195698814257914\",\"alipayTradeId\":\"2013022100001000020037878258\",\"buyerAlipayId\":\"15850790234\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"TAOBAO\",\"shippingType\":\"express\",\"tmsCode\":\"6\",\"crdt\":\"2013-02-21 14:40:02\",\"outCrdt\":\"2013-02-21 14:34:14\",\"buyerNick\":\"胖子的快乐98\",\"receiverName\":\"齐艳茹\",\"receiverProvince\":\"河北\",\"receiverCity\":\"唐山市\",\"receiverCounty\":\"迁安市\",\"receiverAddress\":\"花园街中段路北982号靓雅制衣\",\"receiverMobile\":\"15850790234\",\"receiverPostCode\":\"064400\",\"shippingFee\":0.0,\"payment\":378.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119391523\",\"discountFee\":20.0,\"commissionFee\":0.0,\"isVaid\":true,\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:25:05\",\"createDate\":\"2013-02-21 14:40:03\",\"fromAndDate\":\"来源：TAOBAO</br>付款时间:2013-02-21 14:34:14.0\",\"info\":\"橡果国际益尔健懒人运动收腹机卓越版家用仰卧起坐减肥健身器材(数量：1)\",\"sendInfo\":\"快递公司:上海21CN商城</br>发货地址:河北唐山市迁安市花园街中段路北982号靓雅制衣\",\"orderMoney\":\"产品金额:378.0</br>运费:0.0</br>支付金额:378.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"齐艳茹</br>手机: 15850790234</br>订购账号: 胖子的快乐98\",\"refund\":\"0,0\"},{\"id\":165067,\"version\":2,\"sourceId\":9,\"tradeId\":\"297764208289072\",\"opsTradeId\":\"297764208289072\",\"alipayTradeId\":\"2013022100001000300037215069\",\"buyerAlipayId\":\"xingwujun@yahoo.cn\",\"customerId\":\"橡果国际官方旗舰店\",\"tradeType\":\"127\",\"tradeFrom\":\"TAOBAO\",\"shippingType\":\"express\",\"tmsCode\":\"6\",\"crdt\":\"2013-02-21 14:40:02\",\"outCrdt\":\"2013-02-21 14:29:25\",\"buyerNick\":\"天下行a\",\"receiverName\":\"邢伍军\",\"receiverProvince\":\"河南\",\"receiverCity\":\"平顶山市\",\"receiverCounty\":\"鲁山县\",\"receiverAddress\":\"花园路南段菜花香胡同口\",\"receiverMobile\":\"13937503934\",\"receiverPostCode\":\"467300\",\"shippingFee\":0.0,\"payment\":378.0,\"impStatus\":\"002\",\"impStatusRemark\":\"订单重复, 该订单已经被导入过\",\"shipmentId\":\"1119396765\",\"discountFee\":20.0,\"commissionFee\":0.0,\"isVaid\":true,\"feedbackStatus\":\"2\",\"feedbackStatusRemark\":\"反馈成功\",\"feedbackUser\":\"esb-order-feedback-taobao\",\"feedbackDate\":\"2013-02-21 18:30:00\",\"createDate\":\"2013-02-21 14:40:03\",\"fromAndDate\":\"来源：TAOBAO</br>付款时间:2013-02-21 14:29:25.0\",\"info\":\"橡果国际益尔健懒人运动收腹机卓越版家用仰卧起坐减肥健身器材(数量：1)\",\"sendInfo\":\"快递公司:上海21CN商城</br>发货地址:河南平顶山市鲁山县花园路南段菜花香胡同口\",\"orderMoney\":\"产品金额:378.0</br>运费:0.0</br>支付金额:378.0\",\"remark\":\"买家:</br>卖家:\",\"orderState\":\"002,2\",\"nameAndPhone\":\"邢伍军</br>手机: 13937503934</br>订购账号: 天下行a\",\"refund\":\"0,0\"}],\"total\":360 }";
		HttpUtil.ajaxReturn(response, message, "application/json");
	}

	@RequestMapping(value = "orderPreprocessing/index/{from}", method = RequestMethod.GET)
	public ModelAndView index(
            @PathVariable("from") String from,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		ModelAndView mav = new ModelAndView("orderPreproccessing/index");
		
//		log.info("OMSsession:" + request.getSession().getAttribute("OMSsessiongId"));
//		if (request.getSession().getAttribute("OMSsessiongId") == null) {
//			wirteLog("登陆", "系统登陆", "", request);
//		}
//		request.getSession().setAttribute("OMSsessiongId",
//				request.getSession().getId());
//		request.getSession().setAttribute("omsusername",SecurityHelper.getLoginUser().getUsername());
		request.getSession().setAttribute("from", from);
		request.getSession().setAttribute("isJson", sourceConfigure.getApprovalInterface());
		request.getSession().setAttribute("preTreadType", preTradeCompanyService.getPreTradeCompanyBySourceid(getSourceID(from)));
		return mav;
	}

	/**
	 * 获取订单列表
	 * 
	 * @param page
	 * @param rows
	 * @param id
	 * @param tradeId
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@RequestMapping(value = "orderPreprocessing/list", method = RequestMethod.POST)
	public void list(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam Integer rows,
			@RequestParam(required = false, defaultValue = "") String tradeId,
			@RequestParam(required = false, defaultValue = "") String from,
			@RequestParam(required = false, defaultValue = "") String tradeFrom,
			@RequestParam(required = false, defaultValue = "") String alipayTradeId,
			@RequestParam(required = false, defaultValue = "") String treadType,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, JSONException, ParseException {
		
		List<PreTradeCompany> listTradeCompany= preTradeCompanyService.getPreTradeCompanyBySourceid(getSourceID(from));
		String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
		String sBeginDate = request.getParameter("beginDate");
		Date beginDate = null;
		if (sBeginDate != null && !sBeginDate.isEmpty()) {
			sBeginDate += " 00:00:00";
			beginDate = dateFormat.parse(sBeginDate);
		}
		String sEndDate = request.getParameter("endDate");

		Date endDate = null;
		if (sEndDate != null && !sEndDate.isEmpty()) {
			sEndDate += " 23:59:59";
			endDate = dateFormat.parse(sEndDate);
		}
		String strMin = request.getParameter("min");
		String strMax = request.getParameter("max");
		Double min = null, max = null;
		if (strMin != null && !strMin.isEmpty()) {
			min = Double.valueOf(strMin);
		}
		if (strMax != null && !strMax.isEmpty()) {
			max = Double.valueOf(strMax);
		}
		String shipmentId = request.getParameter("shipmentId");
		String receiverMobile = request.getParameter("receiverMobile");
		String buyerMessage = request.getParameter("buyerMessage");
		String sellerMessage = request.getParameter("sellerMessage");
		String isCombine = request.getParameter("isCombine");
		System.out.println("isCombine:"+isCombine);
		int state = 0;
		if (request.getParameter("state") != null)
			state = Integer.valueOf(request.getParameter("state"));
		int refundStatus = 0;
		if (request.getParameter("refundStatus") != null)
			refundStatus = Integer
					.valueOf(request.getParameter("refundStatus"));
		
		int refundStatusConfirm =0;
		if (request.getParameter("refundStatusConfirm") != null)
			refundStatusConfirm = Integer
					.valueOf(request.getParameter("refundStatusConfirm"));
		long sourceId = getSourceID(from);
		List<PreTrade> ptList = null;
		int totalRecords =0;
        try{
        	if(isCombine != null){
        		totalRecords=preTradeService.getCountAllIsCombinePreTrade(treadType,sourceId,
        				tradeFrom, alipayTradeId, beginDate, endDate, min, max,
        				receiverMobile, buyerMessage, sellerMessage, state,
        				refundStatus,refundStatusConfirm, shipmentId, tradeId);
                ptList = preTradeService.getAllIsCombinePreTrade(treadType,sourceId,
        				tradeFrom, alipayTradeId, beginDate, endDate, min, max,
        				receiverMobile, buyerMessage, sellerMessage, state,
        				refundStatus,refundStatusConfirm, shipmentId, tradeId, page - 1, rows);			
        		
        	}else{
        	totalRecords= preTradeService.getCountAllPreTrade(treadType,sourceId,
    				tradeFrom, alipayTradeId, beginDate, endDate, min, max,
    				receiverMobile, buyerMessage, sellerMessage, state,
    				refundStatus,refundStatusConfirm, shipmentId, tradeId);
        	ptList= preTradeService.getAllPreTrade(treadType,sourceId,
				tradeFrom, alipayTradeId, beginDate, endDate, min, max,
				receiverMobile, buyerMessage, sellerMessage, state,
				refundStatus,refundStatusConfirm, shipmentId, tradeId, page - 1, rows);
        	}
        }catch(Exception e){
        	log.info("OMSexception:"+e.getMessage());
        }
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.setExclusionStrategies(
						new ExcludeUnionResource(new String[] { "preTradeLot",
								"preTradeDetails", "preTradeCards" })).create();
		StringBuffer sb = new StringBuffer("[");
		String invoice="", fromAndDate = "", info = "", sendInfo = "", remark = "", nameAndPhone = "", orderMoney = "", tmpStr = "", orderState = "", refund = "",treadTypeName="";
		try {
			for (PreTrade pt : ptList) {
				// 订单状

				// 来源及订购日期
				String sfrom = pt.getTradeFrom() != null ? pt.getTradeFrom()
						.toString() : "";
						
				String soutCrdt = pt.getOutCrdt() != null ? pt.getOutCrdt()
						.toString() : "";
				fromAndDate = "来源：" + sfrom + "</br>付款时间:" + soutCrdt;
				if (pt.getPreTradeDetails() != null) {
					for (PreTradeDetail ptd : pt.getPreTradeDetails()) {
						if (ptd.getIsActive() != null
								&& ptd.getIsVaid() != null && ptd.getIsActive()
								&& ptd.getIsVaid())
							info += ptd.getSkuName() + "(数量：" + ptd.getQty()
									+ ")";
					}
				}
				// 发货地址
				String province = pt.getReceiverProvince() != null ? pt
						.getReceiverProvince() : "";
				String city = pt.getReceiverCity() != null ? pt
						.getReceiverCity() : "";
				String area = pt.getReceiverCounty() != null ? pt
						.getReceiverCounty() : "";
				String address = pt.getReceiverAddress() != null ? pt
						.getReceiverAddress() : "";
				sendInfo = new StringBuffer("快递公司: ")
						.append(StringUtil.nullToBlank(companyService
								.getCompanyNameByID(pt.getTmsCode())))
						.append("</br>发货地址:").append(province).append(city)
						.append(area).append(address).toString();
				//发票需求
				  if(pt.getInvoiceComment() == null){
					  invoice = "未指定";
				  }else if(pt.getInvoiceComment().trim().equals("0")){
					  invoice = "不需要";
				  }else if(pt.getInvoiceComment().trim().equals("1")){
					  invoice = "需要";
				  }else if(pt.getInvoiceComment().trim().equals("2")){
					  invoice = "特殊需求";
				  }
				  
                remark = "需求:"+invoice+"</br>抬头:"+StringUtil.nullToBlank(pt.getInvoiceTitle());
				
                
                nameAndPhone = StringUtil.nullToBlank(pt.getReceiverName())
						+ "</br>手机: "
						+ mastPhoneNo(pt.getReceiverMobile());
				orderState = StringUtil.nullToBlank(pt.getImpStatus()) + ","+ StringUtil.nullToBlank(pt.getFeedbackStatus());

				refund = StringUtil.nullTo0(pt.getRefundStatus())+","+ StringUtil.nullTo0(pt.getRefundStatusConfirm());
				for(PreTradeCompany preTradeCompany :listTradeCompany){
					
					if(preTradeCompany.getTradeType().equals(pt.getTradeType())){
						treadTypeName=  StringUtil.nullToBlank(preTradeCompany.getRemark());
					}
				}
	
				if (from.equals("TAOBAO") || from.equals("TAOBAOZ")
						|| from.equals("TAOBAOC"))
					nameAndPhone += "</br>订购账号: "
							+ StringUtil.nullToBlank(pt.getBuyerNick());
				/* 订单金额 */
				double shippingFee = pt.getShippingFee() == null ? 0 : pt
						.getShippingFee();
				double payment = pt.getPayment() == null ? 0 : pt.getPayment();
				orderMoney = "产品金额:" + (payment - shippingFee) + "</br>运费:"
						+ shippingFee + "</br>支付金额:" + payment;
				tmpStr = gson.toJson(pt);
				tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
				tmpStr += ",\"info\":\"" + StringUtil.stringToJson(info) + "\"";
				tmpStr += ",\"sendInfo\":\""
						+ StringUtil.stringToJson(sendInfo) + "\"";
				tmpStr += ",\"orderMoney\":\""
						+ StringUtil.stringToJson(orderMoney) + "\"";
				tmpStr += ",\"remark\":\""+ StringUtil.stringToJson(remark) + "\"";
				tmpStr += ",\"orderState\":\""
						+ StringUtil.stringToJson(orderState) + "\"";
				tmpStr += ",\"nameAndPhone\":\"" + StringUtil.stringToJson(nameAndPhone) + "\"";
				tmpStr += ",\"refund\":\"" + StringUtil.stringToJson(refund) + "\"";
				tmpStr += ",\"treadTypeName\":\"" + StringUtil.stringToJson(treadTypeName) + "\"";			
				tmpStr += "}";
				sb.append(tmpStr);
				sb.append(",");
				invoice="";
				fromAndDate = "";
				info = "";
				sendInfo = "";
				remark = "";
				nameAndPhone = "";
				orderMoney = "";
				tmpStr = "";
				orderState = "";
				refund = "";
				treadTypeName="";
				
				
			}
		} catch (Exception e) {
			log.info("OMSexception:"+e.getMessage());
			e.printStackTrace();
		}
		String str = sb.toString();
		if (str.length() > 1)
			str = str.substring(0, str.length() - 1);
		str += "]";
		response.setContentType("text/json");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		String jsonData = "{\"rows\":" + str + ",\"total\":" + totalRecords
				+ " }";


		PrintWriter pt;
		try {
			pt = response.getWriter();
			pt.write(jsonData);
			pt.flush();
			pt.close();
		} catch (IOException e) {
			log.info("OMSexception:"+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String mastPhoneNo(String phoneNo) {
		if(null == phoneNo || "".equals(phoneNo.trim()) || "null".equals(phoneNo.trim())) {
			return "";
		}
		
		if(phoneNo.length() > 5) {
			phoneNo = phoneNo.substring(0, phoneNo.length() - 4) + "****";
		}
		return phoneNo;
	}

	/**
	 * 根据订单ID 获取订单商品详细
	 * 
	 * @param preTradeID
	 *            订单Id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderPreprocessing/getDetails", method = RequestMethod.GET)
	public void getPreTradeDetailByTradeId(
			@RequestParam(required = false, defaultValue = "") String preTradeID,
			HttpServletRequest request, HttpServletResponse response) {
		List<PreTradeDetail> ptdList = preTradeDetailService
				.getAllPreTradeDetailByPerTradeID(preTradeID);
		List<PreTradeDetail> ptddList = new ArrayList();
		for (PreTradeDetail ptd : ptdList) {
			PreTradeDetail ptd1 = ptd;
			ptd1.setPayment(ptd.getUpPrice() * ptd.getQty());
			ptddList.add(ptd1);
		}
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd")
				.setExclusionStrategies(
						new ExcludeUnionResource(new String[] { "preTrade" }))
				.create();

		response.setContentType("text/json");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		String jsonData = gson.toJson(ptdList);
		PrintWriter pt;
		try {
			pt = response.getWriter();
			pt.write(jsonData);
			pt.flush();
			pt.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 修改订单
	 * 
	 * @param v_id
	 * @param v_receiverProvince
	 * @param v_receiverArea
	 * @param v_receiverCity
	 * @param v_shippingType
	 * @param v_invoiceComment
	 * @param v_invoiceTitle
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderPreprocessing/updatePreTrade", method = RequestMethod.POST)
	public void updatePreTrade(
			@RequestParam(required = false, defaultValue = "") String v_id,
			@RequestParam(required = false, defaultValue = "") String s1,
			@RequestParam(required = false, defaultValue = "") String s3,
			@RequestParam(required = false, defaultValue = "") String s2,
			@RequestParam(required = false, defaultValue = "") String v_shippingType,
			@RequestParam(required = false, defaultValue = "") String v_invoiceComment,
			@RequestParam(required = false, defaultValue = "") String v_invoiceTitle,
			@RequestParam(required = false, defaultValue = "") String v_receiverName,
			@RequestParam(required = false, defaultValue = "") String v_receiverMobile,
			@RequestParam(required = false, defaultValue = "") String v_receiverPhone,
			@RequestParam(required = false, defaultValue = "") String v_address,
			HttpServletRequest request, HttpServletResponse response) {
		String message ="";
		PreTrade preTrade = preTradeService.getById(Long.valueOf(v_id));
		preTrade.setReceiverCounty(s3);
		preTrade.setReceiverProvince(formatProvince(s1));
		preTrade.setReceiverCity(s2);
		preTrade.setReceiverAddress(v_address);
		preTrade.setTmsCode(v_shippingType);
		preTrade.setReceiverName(v_receiverName);
		preTrade.setReceiverMobile(v_receiverMobile);
		preTrade.setReceiverPhone(v_receiverPhone);
		preTrade.setInvoiceComment(v_invoiceComment);
		preTrade.setInvoiceTitle(v_invoiceTitle);
		preTrade.setFeedbackStatus("0");
		try{
		preTradeService.savePreTrade(preTrade);
		message = "修改成功!";
		wirteLog("前置订单处理", "修改订单", preTrade.getTradeId(), request);
		}catch(Exception e){
			message="修改失败!";
			log.error("修改失败:网上单号"+preTrade.getTradeId()+";失败原因:"+e.getMessage());
			wirteLog("前置订单处理", "修改订单失败:"+e.getMessage(), preTrade.getTradeId(), request);
		}
		
	HttpUtil.ajaxReturn(response, message, "application/json");
		
	}

	/**
	 * 操作日志记录
	 * 
	 * @param title
	 * @param txt
	 * @param request
	 * @author haoleitao
	 * @date 2013-2-1 下午3:49:54
	 */
	public void wirteLog(String title, String txt, String treadid,
			HttpServletRequest request) {
		AuditLog auditLog = new AuditLog();
		auditLog.setAppName("OMS");
		auditLog.setFuncName(title);
		auditLog.setTreadid(treadid);
		auditLog.setLogDate(new Date());
		auditLog.setLogValue(txt);
		auditLog.setSessionId(request.getSession().getId());
		auditLog.setUserId(SecurityHelper.getLoginUser().getUsername());
		auditLogService.addAuditLog(auditLog);

	}

	public String formatProvince(String province) {
		if (StringUtil.isNullOrBank(province)) {
			return "";
		} else {
			if(province.endsWith("省") && province.endsWith("市")) {
				province = province.substring(0, province.length() - 1);
			}
		}

		return province;
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderPreprocessing/deletePreTrade", method = RequestMethod.POST)
	public void deletePreTrade(
			@RequestParam(required = false, defaultValue = "") String id,
			@RequestParam(required = false, defaultValue = "") String treadid,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			preTradeService.delPreTrade(Long.valueOf(id));
			wirteLog("前置订单处理", "删除订单", treadid, request);
			ajaxReturn(response, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(response, "删除失败!");
		}
	}

	public void ajaxReturn(HttpServletResponse response, String message) {

		try {
			response.setContentType("application/text");
			response.addHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(message);
			out.flush();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 压单
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderPreprocessing/detainOrder", method = RequestMethod.POST)
	public void detainOrder(
			@RequestParam(required = false, defaultValue = "") String id,
			HttpServletRequest request, HttpServletResponse response) {
		PreTrade preTrade = preTradeService.getById(Long.valueOf(id));
		preTrade.setImpStatus("99");
		preTrade.setImpStatusRemark("已压单");
		preTradeService.savePreTrade(preTrade);
		wirteLog("前置订单处理", "压单操作", preTrade.getTradeId(), request);
		PrintWriter out;
		try {
			response.setContentType("application/text");
			response.addHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.write("订单号" + preTrade.getTradeId() + ",压单成功!");
			out.flush();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 反馈
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderPreprocessing/feedback", method = RequestMethod.POST)
	public void feedback(
			@RequestParam(required = false, defaultValue = "") String id,
			HttpServletRequest request, HttpServletResponse response) {
		PreTrade preTrade = preTradeService.getById(Long.valueOf(id));
		/* 反馈逻辑 */
		preTrade.setFeedbackStatus("2");
		 String message = "";
		if(preTradeService.updatePreTrade(preTrade) != null){
			 message= "订单号:" + preTrade.getTradeId() + ",已标记完成!";	
		}else{
			 message= "订单号:" + preTrade.getTradeId() + ",反馈失败!";
		}
		wirteLog("前置订单处理", "订单反馈", preTrade.getTradeId(), request);
      
		
		HttpUtil.ajaxReturn(response, message, "application/text");

	}

	@RequestMapping(value = "orderPreprocessing/getbw", method = RequestMethod.POST)
	public void getbaoguowl(
			@RequestParam(required = false, defaultValue = "") String id,

			HttpServletRequest request, HttpServletResponse response) {
		Orderhist oh = null;
		String message = "";
		orderhistService.getOrderhistById(id);
		try {
			oh = orderhistService.getOrderhistById(id);
		} catch (Exception e) {
			log.error(e.getMessage());

		}
		if (oh != null) {
			if (!StringUtil.nullToBlank(oh.getEntityid()).equals("")
					&& !StringUtil.nullToBlank(oh.getMailid()).equals("")) {
				message = "面单号:" + StringUtil.nullToBlank(oh.getMailid()) + ",";
				message += "送货公司:"
						+ companyService.getCompanyByID(oh.getEntityid());
			} else {
				if (StringUtil.nullToBlank(oh.getMailid()).equals("")
						&& StringUtil.nullToBlank(oh.getEntityid()).equals("")) {
					message = "3";
				} else {
					if (StringUtil.nullToBlank(oh.getMailid()).equals(""))
						message = "1";
					if (StringUtil.nullToBlank(oh.getEntityid()).equals(""))
						message = "2";
				}
			}
		} else {
			message = "4";
		}

		HttpUtil.ajaxReturn(response, message, "application/text");

	}

	@RequestMapping(value = "orderPreprocessing/getcompany", method = RequestMethod.POST)
	public void getCompany(HttpServletRequest request,
			HttpServletResponse response) {

		List<Company> com = companyService.getAllCompanies();

		Gson gson = new GsonBuilder().create();
		String message = gson.toJson(com);
		log.debug(message);
		HttpUtil.ajaxReturn(response, message, "application/json");

	}

	/**
	 * 合单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderPreprocessing/combineOrders", method = RequestMethod.POST)
	public void combineOrders(
			@RequestParam(required = false, defaultValue = "") String ids,
			@RequestParam(required = false, defaultValue = "") String ptids,
			@RequestParam(required = false, defaultValue = "") String tids,
			@RequestParam(required = false, defaultValue = "") Double payment,
			@RequestParam(required = false, defaultValue = "") String postFee,
			@RequestParam(required = false, defaultValue = "") String invoiceTitle,
			@RequestParam(required = false, defaultValue = "") String invoiceComment,
			HttpServletRequest request, HttpServletResponse response) {
		ids = ids.substring(0, ids.length() - 1);
		tids = tids.substring(0, tids.length() - 1);
		ptids = ptids.substring(0, ptids.length() - 1);
		String allid[] = ids.split(",");

		/* 合单逻辑 */
		/* 合单产品列表 */
		String r3 = "";
		List<PreTrade> listPreTrade = new ArrayList();
		PreTrade preTrade = new PreTrade();
		for (String id : allid) {
			preTrade = preTradeService.getById(Long.valueOf(id));
			List<PreTradeDetail> list = preTradeDetailService
					.getAllPreTradeDetailByPerTradeID(preTrade.getTradeId());

			for (PreTradeDetail pre : list) {
				if (pre.getOutSkuId() == null) {
					r3 += "<sku><sku_code>" + pre.getSkuId();
				} else {
					r3 += "<sku><sku_code>" + pre.getOutSkuId();
				}
				r3 += "</sku_code><number>" + pre.getQty() + "</number><price>"
						+ pre.getUpPrice() + "</price></sku>";
			}
			listPreTrade.add(preTrade);
		}

		// 格式化省份地址
		// if(preTrade.getReceiverProvince() != null &&
		// !preTrade.getReceiverProvince().equals(""))
		// preTrade.setReceiverProvince(preTrade.getReceiverProvince().replace("省",
		// ""));

		String result = "", url = "";
		try {

			String r2 = "<ops_trade>" + "<ops_trade_id>"
					+ ptids
					+ "</ops_trade_id>"
					+ "<tid>"
					+ tids
					+ "</tid>"
					// + "</ops_trade_id>" + "<tid></tid>"
					+ "<retailer_id>ORD_"
					+ preTrade.getTradeType()
					+ "</retailer_id>"
					+ "<customer_id>"
					+ preTrade.getCustomerId()
					+ "</customer_id>"
					+ "<trade_from>"
					+ preTrade.getTradeFrom()
					+ "</trade_from>"
					+ "<receiver_name>"
					+ preTrade.getReceiverName()
					+ "</receiver_name>"
					+ "<receiver_mobile>"
					+ preTrade.getReceiverMobile()
					+ "</receiver_mobile>"
					+ "<receiver_phone>"
					+ preTrade.getReceiverPhone()
					+ "</receiver_phone>"
					+ "<receiver_province>"
					+ preTrade.getReceiverProvince()
					+ "</receiver_province>"
					+ "<receiver_city>"
					+ preTrade.getReceiverCity()
					+ "</receiver_city>"
					+ "<receiver_district>"
					+ preTrade.getReceiverCounty()
					+ "</receiver_district>"
					+ "<receiver_street>"
					+ preTrade.getReceiverArea()
					+ "</receiver_street>"
					+ "<receiver_address><![CDATA["
					+ preTrade.getReceiverAddress()
					+ "]]></receiver_address>"
					+ "<receiver_post_code>"
					+ preTrade.getReceiverPostCode()
					+ "</receiver_post_code>"
					+ "<tms_code>"
					+ preTrade.getTmsCode()
					+ "</tms_code>"
					+ "<payment>"
					+ payment
					+ "</payment>"
					+ "<commission_fee>"
					+ StringUtil.objnullToBlank(preTrade.getCreditFee())
					+ "</commission_fee>"
					+ "<retailer_commission_fee>"
					+ StringUtil.objnullToBlank(preTrade
							.getRetailerCommissionFee())
					+ "</retailer_commission_fee>"
					+ "<platform_commission_fee>"
					+ StringUtil.objnullToBlank(preTrade
							.getPlatformCommissionFee())
					+ "</platform_commission_fee>" + "<credit_fee>"
					+ StringUtil.objnullToBlank(preTrade.getCreditFee())
					+ "</credit_fee>" + "<adv_fee>"
					+ StringUtil.objnullToBlank(preTrade.getAdvFee())
					+ "</adv_fee>" + "<jhs_fee>"
					+ StringUtil.objnullToBlank(preTrade.getJhsFee())
					+ "</jhs_fee>" + "<revenue>"
					+ StringUtil.objnullToBlank(preTrade.getRevenue())
					+ "</revenue>" + "<created>"
					+ StringUtil.objnullToBlank(preTrade.getOutCrdt())
					+ "</created>" + "<post_fee>" + postFee + "</post_fee>"
					+ "<invoice_title><![CDATA["
					+ StringUtil.objnullToBlank(invoiceTitle)
					+ "]]></invoice_title>" + "<invoice_comment>"
					+ StringUtil.objnullToBlank(invoiceComment)
					+ "</invoice_comment>" + "<skus>";
			r2 += r3;
			r2 += "</skus>" + "</ops_trade>";
			log.info("r2:" + r2);
			url = getPostUrl(preTrade, url);
			if (!url.isEmpty())
				result = HttpUtil.postUrl(url, r2, "UTF-8");

			log.info("result:" + result);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mcode = "", message = "", igentId = "";
		if (!StringUtil.isNullOrBank(result)) {
			mcode = SaxParseXmlUtil._readXml(result, "message_code").get(0)
					.get("message_code");
			message = SaxParseXmlUtil._readXml(result, "message").get(0)
					.get("message");
			boolean igent_result = Boolean.valueOf(SaxParseXmlUtil
					._readXml(result, "result").get(0).get("result"));

			// 保存反馈信息
			// 反馈成功

			for (PreTrade pte : listPreTrade) {

				if (!StringUtil.isNullOrBank(mcode) && igent_result) {
					igentId = SaxParseXmlUtil
							._readXml(result, "agent_trade_id").get(0)
							.get("agent_trade_id");
					pte.setShipmentId(igentId);
					// 修改合单运费
					pte.setSellerMessage(StringUtil.nullToBlank(pte.getSellerMessage())
							+ " ,此订单已做合单，合单后调整运费为:" + postFee);
				}
				pte.setImpStatus(mcode);
				pte.setImpStatusRemark(message);
				preTradeService.savePreTrade(pte);
			}
		} else {
			message = "订单来源不可识别";
		}

		// TODO Auto-generated catch block

		// XmlObject xml = new XmlObject()
		wirteLog("前置订单处理", "合单操作", ptids, request);

		PrintWriter out;
		try {
			response.setContentType("application/text");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.write("订单号" + preTrade.getTradeId() + ",审核结果:" + message);
			out.flush();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 合单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderPreprocessing/combineOrders/json", method = RequestMethod.POST)
	public void combineOrders_json(
			@RequestParam(required = false, defaultValue = "") String ids,
			@RequestParam(required = false, defaultValue = "") String ptids,
			@RequestParam(required = false, defaultValue = "") String tids,
			@RequestParam(required = false, defaultValue = "") Double payment,
			@RequestParam(required = false, defaultValue = "") String postFee,
			@RequestParam(required = false, defaultValue = "") String invoiceTitle,
			@RequestParam(required = false, defaultValue = "") String invoiceComment,
			HttpServletRequest request, HttpServletResponse response) {
        log.info("begin combine order audit:"+ids);
		ids = ids.substring(0, ids.length() - 1);
		tids = tids.substring(0, tids.length() - 1);
		ptids = ptids.substring(0, ptids.length() - 1);
		String allid[] = ids.split(",");

		/* 合单逻辑 */
		/* 合单产品列表 */
		String r3 = "";
		String cards="";
		List<PreTrade> listPreTrade = new ArrayList();
		
		Gson gson = new GsonBuilder()
		.setDateFormat("yyyy-MM-dd HH:mm:ss")
		.setExclusionStrategies(
				new ExcludeUnionResource(new String[] { "preTradeLot",
						"preTradeDetails","preTrade","preTradeCards" })).create();
		
		List<PreTradeDetail> list = new ArrayList();
		for (String id : allid) {
			 PreTrade preTrade = preTradeService.getById(Long.valueOf(id));
		     list.addAll(preTradeDetailService.getAllPreTradeDetailByPerTradeID(preTrade.getTradeId()));
	
			listPreTrade.add(preTrade);
		}
		
		
		r3= gson.toJson(list);

		String mcode = "", message = "", igentId = "";
		String result = "", url = "";
		PreTrade preTrade = new PreTrade();
		//PreTrade preTrade = listPreTrade.get(0);
		try {
		try {
			BeanUtilEx.copyProperties(preTrade, listPreTrade.get(0));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
                log.error("combine order audit error1:"+e.getMessage(), e);
				//e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
                log.error("combine order audit error2:"+e.getMessage(), e);
				//e.printStackTrace();
			}
			preTrade.setTradeId(ptids);
			preTrade.setAlipayTradeId(tids);
			preTrade.setPayment(payment);
            preTrade.setShippingFee(Double.valueOf(postFee));
			preTrade.setInvoiceTitle(invoiceTitle);
			preTrade.setInvoiceComment(invoiceComment);
			
			preTrade.setReceiverAddress(StringUtil.stringToJson(preTrade.getReceiverAddress()));
			preTrade.setBuyerMessage(StringUtil.stringToJson(preTrade.getBuyerMessage()));
			preTrade.setSellerMessage(StringUtil.stringToJson(preTrade.getSellerMessage()));
			
			String r2 =gson.toJson(preTrade) ;
            // 订单不参与合单
			if(preTrade.getSourceId()==17 || ((preTrade.getSplitFlag()==null ? 0:preTrade.getSplitFlag()) == -1)){
                 if(preTrade.getSourceId()==17){
                     message ="信用卡订单不允许合单";
                 }else{
                     message = "子订单不允许合单";
                 }

			}else{


			r2 = r2.replace("}", ",\"crusr\":\""+SecurityHelper.getLoginUser().getUserId()+"\",\"preTradeDetails\": "+r3+"}");
			log.info("post combine json " + r2);
			
			url = getPostJsonUrl(url);
			if (!url.isEmpty()){
				result = HttpUtil.postjsonUrl(url, r2, "UTF-8");
			}else{
				message = "订单来源不可识别";
			}
				

			log.info("post combine result json:" + result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
            log.error("combine order audit error3:"+e.getMessage(), e);
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
            log.error("combine order audit error4:"+e.getMessage(), e);
			//e.printStackTrace();
		}

        try{
            if (!StringUtil.isNullOrBank(result)) {
                 IgentResult p = gson.fromJson(result, IgentResult.class);
                mcode = p.getImpState();
                message = p.getDesc();
                boolean igent_result = p.isSuccess();

                // 保存反馈信息
                // 反馈成功

                for (PreTrade pte : listPreTrade) {

                    if (!StringUtil.isNullOrBank(mcode) && igent_result) {
                        pte.setShipmentId(p.getOrderId());
                        // 修改合单运费
                        pte.setSellerMessage(StringUtil.nullToBlank(pte.getSellerMessage())
                                + " ,此订单已做合单，合单后调整运费为:" + postFee);
                    }
                    pte.setImpStatus(mcode);
                    pte.setImpStatusRemark( message.length()>99?message.substring(0, 30):message);
                    preTradeService.savePreTrade(pte);
                }
            }
        }catch (Exception exp)
        {
            message=exp.getMessage();
            log.error("combine order audit error5:"+exp.getMessage(), exp);
        }

		// TODO Auto-generated catch block

		// XmlObject xml = new XmlObject()
		wirteLog("前置订单处理", "合单操作", ptids, request);

        log.info("end combine order audit:"+ids);

		PrintWriter out;
		try {
			response.setContentType("application/text");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.write("订单号" + preTrade.getTradeId() + ",审核结果:" + message);
			out.flush();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
            log.error("combine order audit error6:", e);
			//e.printStackTrace();
		}

	}

	/**
	 * 单审
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderPreprocessing/singleApproval", method = RequestMethod.POST)
	public void singleApproval(
			@RequestParam(required = false, defaultValue = "") String id,
			@RequestParam(required = false, defaultValue = "false") Boolean isCombine,
			HttpServletRequest request, HttpServletResponse response) {
		PreTrade preTrade = preTradeService.getById(Long.valueOf(id));
		List<String> combineList = preTradeService.getCombinePreTradeId(preTrade,preTrade.getSourceId());
		List<PreTradeDetail> list = preTradeDetailService
				.getAllPreTradeDetailByPerTradeID(preTrade.getTradeId());
		// 格式化省份地址
		if (preTrade.getReceiverProvince() != null
				&& !preTrade.getReceiverProvince().equals(""))
			preTrade.setReceiverProvince(preTrade.getReceiverProvince()
					.replace("省", ""));
		//设置
		// 单审逻辑
		// String content = XmlBeanConvertUtil.beanToXml(preTrade);
		String mcode;
		String message = "";
		String url = "";
		String losStr = "";
		String sjson ="";
		String result = "";
		url = getPostUrl(preTrade, url);
		boolean igent_result = false;
		// 判断订单是否已经审核
		
		Gson gson = new GsonBuilder()
		.setDateFormat("yyyy-MM-dd")
		.setExclusionStrategies(
				new ExcludeUnionResource(new String[] { "preTradeLot",
						"preTradeDetails","preTradeCards"})).create();

		try {
			if (!url.isEmpty()) {
				//判断是否合单数据 
			    if(combineList != null && combineList.size() >0 && !isCombine){
			    	message = "可以合单的数据:"+combineList;
			    }else{
				if (!StringUtil.nullToFalse(preTrade.getIsApproval())) {
					// 判断是否为退款订单
					if ((preTrade.getRefundStatus() != null && StringUtil.nullTo0(preTrade.getRefundStatusConfirm()) != 1 )) {

						if (preTrade.getRefundStatusConfirm() == 2){
							message = "取消发货的订单";
						}else{
							message = "申请退款的订单";
						}
						preTrade.setImpStatusRemark(message);
					} else {
				
						preTrade.setIsApproval(false);
						preTradeService.savePreTrade(preTrade);
	                    Long sh = System.currentTimeMillis();
						result = postTrade(preTrade, list, url, result);
						Long eh = System.currentTimeMillis();
						
						log.info("result("+(eh-sh)+"ms):" + result);
						// 保存反馈信息
						// 反馈成功
						mcode = SaxParseXmlUtil
								._readXml(result, "message_code").get(0)
								.get("message_code");
						message = SaxParseXmlUtil._readXml(result, "message")
								.get(0).get("message");
						String igentId = SaxParseXmlUtil
								._readXml(result, "agent_trade_id").get(0)
								.get("agent_trade_id");
						igent_result = Boolean.valueOf(SaxParseXmlUtil
								._readXml(result, "result").get(0)
								.get("result"));
						if (igent_result && igentId != null && !igentId.equals("")) {
							preTrade.setShipmentId(igentId);
						//} else {
							preTrade.setIsApproval(true);
						}
						preTrade.setImpStatus(mcode);
						if(igent_result) message="导入订单成功";
						preTrade.setImpStatusRemark(message);
						preTradeService.savePreTrade(preTrade);
					}
				} else {
					message = "重复订单";
					//preTrade.setImpStatusRemark(message);
				}
			    }
			} else {
				message = "订单来源不可识别";
				//preTrade.setImpStatusRemark(message);
			}
			
			

	String json = gson.toJson(preTrade);
	//log.info("json:"+json);
	//{"id":195087,"version":2,"sourceId":2,"tradeId":"195447254758957","opsTradeId":"195447254758957","alipayTradeId":"2013022000001000400036710737","buyerAlipayId":"18239919142","customerId":"奥雅化妆品旗舰店","tradeType":"98","tradeFrom":"TAOBAO","shippingType":"free","tmsCode":"6","crdt":"2013-02-20","outCrdt":"2013-02-20","buyerNick":"王瑶广521","receiverName":"王瑶广","receiverProvince":"河南","receiverCity":"郑州市","receiverCounty":"登封市","receiverAddress":"中岳大街原水利医院对面祥和药店","receiverMobile":"13526543734","receiverPostCode":"452470","shippingFee":0.0,"payment":199.0,"impStatus":"014","impStatusRemark":"订单导入成功,但商品单价低于成本, 请检查.","shipmentId":"49996579927","discountFee":0.0,"commissionFee":0.0,"isVaid":true,"feedbackStatus":"0","isApproval":true,"createDate":"2013-02-20"}
	 sjson = "{\"tradeId\":\""+preTrade.getTradeId()+"\",\"impStatusRemark\":\""+message+"\",\"impStatus\":\""+StringUtil.nullToBlank(preTrade.getImpStatus())+"\",\"\":\"\"}";

	 losStr= igent_result ? "审核成功," + preTrade.getImpStatusRemark()
			: "审核失败," + preTrade.getImpStatusRemark()==null?message:preTrade.getImpStatusRemark();

		}catch(HibernateOptimisticLockingFailureException e){
			message = "重复订单";
		}catch (Exception e) {
		    if(result == null) message = "TC服务调用失败!";
			message = "系统异常";
			log.info("系统异常"+e.getMessage());
			preTrade.setIsApproval(false);
			//preTrade.setImpStatusRemark(message);
			preTradeService.savePreTrade(preTrade);

		}finally{
			 sjson = "{\"tradeId\":\""+preTrade.getTradeId()+"\",\"impStatusRemark\":\""+message+"\",\"impStatus\":\""+StringUtil.nullToBlank(preTrade.getImpStatus())+"\",\"\":\"\"}";
			HttpUtil.ajaxReturn(response, sjson, "application/json");
			wirteLog("前置订单处理", "审核操作:" + losStr, preTrade.getTradeId(), request);
		}

	}
	
	
	@RequestMapping(value = "orderPreprocessing/getCombine", method = RequestMethod.POST)
	public void getCombine(
			@RequestParam(required = false, defaultValue = "") String id,
			HttpServletRequest request, HttpServletResponse response){
		PreTrade preTrade = preTradeService.getById(Long.valueOf(id));
		List<String> combineList = preTradeService.getCombinePreTradeId(preTrade,preTrade.getSourceId());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String sjson = gson.toJson(combineList);
		  if(combineList != null && combineList.size() >0 ){
			  sjson = "{\"result\":\"true\",\"combineList\":\""+combineList+"\"}";	  
		  }else{
			  sjson = "{\"result\":\"false\",\"combineList\":\""+combineList+"\"}";
		  }
		
		HttpUtil.ajaxReturn(response, sjson, "application/json");
	}

	
	/**
	 * 单审,支持Json新接口
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "orderPreprocessing/singleApproval/json", method = RequestMethod.POST)
	public void singleApproval_json(
			@RequestParam(required = false, defaultValue = "") String id,
			@RequestParam(required = false, defaultValue = "false") Boolean isCombine,
			HttpServletRequest request, HttpServletResponse response) {
        log.info("begin single order audit:"+id);
		PreTrade preTrade = preTradeService.getById(Long.valueOf(id));
		List<String> combineList = preTradeService.getCombinePreTradeId(preTrade,preTrade.getSourceId());
		List<PreTradeDetail> list = preTradeDetailService.getAllPreTradeDetailByPerTradeID(preTrade.getTradeId());
		Set<PreTradeCard> set =preTrade.getPreTradeCards(); 
		// 格式化省份地址
		if (preTrade.getReceiverProvince() != null
				&& !preTrade.getReceiverProvince().equals(""))
			preTrade.setReceiverProvince(preTrade.getReceiverProvince()
					.replace("省", ""));
		// 单审逻辑
		// String content = XmlBeanConvertUtil.beanToXml(preTrade);
		String mcode;
		String message = "";
		String url = "";
		String losStr = "";
		String sjson ="";
		url = getPostJsonUrl(url);
		boolean igent_result = false;
		// 判断订单是否已经审核
		Gson gson = new GsonBuilder()
		.setDateFormat("yyyy-MM-dd")
		.setExclusionStrategies(
				new ExcludeUnionResource(new String[] { "preTradeLot",
						"preTradeDetails","preTradeCards" })).create();
		String result = "";
		try {
			if (!url.isEmpty()) {
				
				//判断是否合单数据 
			    if(combineList != null && combineList.size() >0 && !isCombine){
			    	message = "可以合单的数据:"+combineList;
			    }else{
				if (!StringUtil.nullToFalse(preTrade.getIsApproval())) {
					// 判断是否为退款订单
					if ((preTrade.getRefundStatus() != null && StringUtil.nullTo0(preTrade.getRefundStatusConfirm()) != 1 )) {

						if (preTrade.getRefundStatusConfirm() == 2){
							message = "取消发货的订单";
						}else{
							message = "申请退款的订单";
						}
						preTrade.setImpStatusRemark(message);
					} else {
			
						preTrade.setIsApproval(false);
						preTradeService.savePreTrade(preTrade);
	                    Long sh = System.currentTimeMillis();
						result = postTradeJson(preTrade, list,set, url,SecurityHelper.getLoginUser().getUserId());
						Long eh = System.currentTimeMillis();
						
						log.info("result("+(eh-sh)+"ms):" + result);
						// 保存反馈信息
						// 反馈成功
						
						 IgentResult p = gson.fromJson(result, IgentResult.class);
                        this.isJsonSucc(preTrade.getTradeId(), p);
                        mcode = p.getImpState();
						message = p.getDesc();
						String igentId = p.getOrderId();
						igent_result = p.isSuccess();
						if (igent_result) {
							igentId = p.getOrderId();
							preTrade.setShipmentId(igentId);
							preTrade.setIsApproval(true);
						}else if( igentId != null && !igentId.equals("")){
							preTrade.setShipmentId(igentId);
						}
						preTrade.setImpStatus(mcode);
						if(igent_result) message="导入订单成功";
						preTrade.setImpStatusRemark(message);
						preTradeService.savePreTrade(preTrade);
					}
				} else {
					message = "重复订单";
					//preTrade.setImpStatusRemark(message);
				}
			    }
			} else {
				message = "订单来源不可识别";
				//preTrade.setImpStatusRemark(message);
			}
			
			

	String json = gson.toJson(preTrade);

	 sjson = "{\"tradeId\":\""+preTrade.getTradeId()+"\",\"impStatusRemark\":\""+message+"\",\"impStatus\":\""+StringUtil.nullToBlank(preTrade.getImpStatus())+"\",\"\":\"\"}";

	 losStr= igent_result ? "审核成功," + preTrade.getImpStatusRemark()
			: "审核失败," + preTrade.getImpStatusRemark()==null?message:preTrade.getImpStatusRemark();

            log.info("end single order audit:"+id);

		}catch(HibernateOptimisticLockingFailureException e){
            log.error("single order audit error1:"+e.getMessage(),e);
			message = "重复订单";
		}catch (Exception e) {
            log.error("single order audit error2:"+e.getMessage(),e);
			message = "系统异常:";
		    if(result == null) message = "TC服务调用失败!";
			log.info("系统异常"+e.getMessage());
			preTrade.setIsApproval(false);
			preTradeService.savePreTrade(preTrade);
			//preTrade.setImpStatusRemark(message);
		}finally{
			sjson = "{\"tradeId\":\""+preTrade.getTradeId()+"\",\"impStatusRemark\":\""+message+"\",\"impStatus\":\""+StringUtil.nullToBlank(preTrade.getImpStatus())+"\",\"\":\"\"}";
			HttpUtil.ajaxReturn(response, sjson, "application/json");
			wirteLog("前置订单处理", "审核操作:" + losStr, preTrade.getTradeId(), request);
		}

	}
	

	private void reponseSingleApproval(HttpServletResponse response,
			PreTrade preTrade, String message) {
		PrintWriter out;
		try {
			response.setContentType("application/text");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			if (preTrade.getTradeId() != null) {
				out.write("订单号" + preTrade.getTradeId() + ",审核结果:" + message);
			} else {
				out.write("订单无法单审:" + message);
			}
			out.flush();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private String postTrade(PreTrade preTrade, List<PreTradeDetail> list,
			String url, String result) {
		try {

			String r2 = "<ops_trade>" + "<ops_trade_id>"
					+ preTrade.getTradeId() + "</ops_trade_id>" + "<tid>"
					+ preTrade.getAlipayTradeId() + "</tid>"
					+ "<retailer_id>ORD_" + preTrade.getTradeType()
					+ "</retailer_id>" + "<customer_id>"
					+ preTrade.getCustomerId() + "</customer_id>"
					+ "<trade_from>" + preTrade.getTradeFrom()
					+ "</trade_from>" + "<receiver_name>"
					+ preTrade.getReceiverName() + "</receiver_name>"
					+ "<receiver_mobile>" + preTrade.getReceiverMobile()
					+ "</receiver_mobile>" + "<receiver_phone>"
					+ preTrade.getReceiverPhone() + "</receiver_phone>"
					+ "<receiver_province>" + preTrade.getReceiverProvince()
					+ "</receiver_province>" + "<receiver_city>"
					+ preTrade.getReceiverCity() + "</receiver_city>"
					+ "<receiver_district>" + preTrade.getReceiverCounty()
					+ "</receiver_district>" + "<receiver_street>"
					+ preTrade.getReceiverArea() + "</receiver_street>"
					+ "<receiver_address><![CDATA[" + preTrade.getReceiverAddress()
					+ " ]]></receiver_address>" + "<receiver_post_code>"
					+ preTrade.getReceiverPostCode() + "</receiver_post_code>"
					+ "<tms_code>" + preTrade.getTmsCode() + "</tms_code>"
					+ "<payment>" + preTrade.getPayment() + "</payment>"
					+ "<commission_fee>" + preTrade.getCreditFee()
					+ "</commission_fee>" + "<retailer_commission_fee>"
					+ preTrade.getRetailerCommissionFee()
					+ "</retailer_commission_fee>"
					+ "<platform_commission_fee>"
					+ preTrade.getPlatformCommissionFee()
					+ "</platform_commission_fee>" + "<credit_fee>"
					+ preTrade.getCreditFee() + "</credit_fee>" + "<adv_fee>"
					+ preTrade.getAdvFee() + "</adv_fee>" + "<jhs_fee>"
					+ preTrade.getJhsFee() + "</jhs_fee>" + "<revenue>"
					+ preTrade.getRevenue() + "</revenue>" + "<created>"
					+ preTrade.getOutCrdt() + "</created>" + "<post_fee>"
					+ preTrade.getShippingFee() + "</post_fee>"
					+ "<invoice_comment>"
					+ StringUtil.nullToBlank(preTrade.getInvoiceComment())
					+ "</invoice_comment>" + "<invoice_title><![CDATA["
					+ StringUtil.nullToBlank(preTrade.getInvoiceTitle())
					+ "]]></invoice_title>" + "<skus>";

			for (PreTradeDetail pre : list) {
				if (pre.getOutSkuId() == null) {
					r2 += "<sku><sku_code>" + pre.getSkuId();
				} else {
					r2 += "<sku><sku_code>" + pre.getOutSkuId();
				}
				r2 += "</sku_code><number>" + pre.getQty() + "</number><price>"
						+ pre.getUpPrice() + "</price></sku>";
			}
			r2 += "</skus>" + "</ops_trade>";
			log.info("xml:" + r2);
			result = HttpUtil.postUrl(url, r2, "UTF-8");
			log.info("result:" + result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private String postTradeJson(PreTrade preTrade,List<PreTradeDetail> list,Set<PreTradeCard> set ,String url,String crusr) {
		
		Gson gson = new GsonBuilder()
		.setDateFormat("yyyy-MM-dd HH:mm:ss")
		.setExclusionStrategies(
				new ExcludeUnionResource(new String[] { "preTradeLot",
						"preTradeDetails","preTrade","preTradeCards" })).create();
		preTrade.setReceiverAddress(StringUtil.stringToJson(preTrade.getReceiverAddress()));
		preTrade.setBuyerMessage(StringUtil.stringToJson(preTrade.getBuyerMessage()));
		preTrade.setSellerMessage(StringUtil.stringToJson(preTrade.getSellerMessage()));
		String r = gson.toJson(preTrade);
	
	
		String detail = "";
		if(list != null){
			detail = gson.toJson(list);
		}else{
			detail = "[]";
		}
		
		String cards = "";
		if(set != null){
			cards = gson.toJson(set);
		}else{
			cards = "[]";
		}
		
		r = r.replace("}", ",\"crusr\":\""+crusr+"\",\"preTradeDetails\": "+detail+",\"preTradeCards\": "+cards+"}");
		log.info("send json:"+r);
		try {
			r = HttpUtil.postjsonUrl(url, r, "UTF-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			r=null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			r=null;;
		}
		
		log.info("receive json:"+r);
		return r;
	}

	private String getPostUrl(PreTrade preTrade, String url) {
		// 淘宝
		if (preTrade.getSourceId() == TradeSourceConstants.TAOBAO_SOURCE_ID)
			url = sourceConfigure.getTaobaoUrl();
		if (preTrade.getSourceId() == TradeSourceConstants.TAOBAOC_SOURCE_ID)
			url = sourceConfigure.getTaobaocUrl();
		if (preTrade.getSourceId() == TradeSourceConstants.TAOBAOZ_SOURCE_ID)
			url = sourceConfigure.getTaobaozUrl();
		if (preTrade.getSourceId() == TradeSourceConstants.TAOBAOJ_SOURCE_ID)
			url = sourceConfigure.getTaobaojUrl();
		if (preTrade.getSourceId() == TradeSourceConstants.TAOBAOCJ_SOURCE_ID)
			url = sourceConfigure.getTaobaocjUrl();
		
		// 京东
		if (preTrade.getSourceId() == TradeSourceConstants.BUY360_SOURCE_ID)
			url = sourceConfigure.getBuy360Url();

		if (preTrade.getSourceId() == TradeSourceConstants.BUY360FBP_SOURCE_ID)
			url = sourceConfigure.getBuy360fbpUrl();
		// 卓越
		if (preTrade.getSourceId() == TradeSourceConstants.AMAZON_SOURCE_ID)
			url = sourceConfigure.getAmazonUrl();
		if (preTrade.getSourceId() == TradeSourceConstants.AMAZONFBP_SOURCE_ID)
			url = "http://192.168.75.220:8888/sync/account/order";
		// VSC
		if (preTrade.getSourceId() == TradeSourceConstants.CSV_SOURCE_ID)
			url = sourceConfigure.getCsvUrl();
		// 茶马
		if (preTrade.getSourceId() == TradeSourceConstants.CHAMA_SOURCE_ID)
			url = sourceConfigure.getChamaurl();
		
		//好记星
		if (preTrade.getSourceId() == TradeSourceConstants.HJXF_SOURCE_ID)
			url = sourceConfigure.getHjxfUrl();
		if (preTrade.getSourceId() == TradeSourceConstants.HJXYX_SOURCE_ID)
			url = sourceConfigure.getHjxyxUrl();
		if (preTrade.getSourceId() == TradeSourceConstants.HJXGM_SOURCE_ID)
			url = sourceConfigure.getHjqgmUrl();
		//qqpp
		if (preTrade.getSourceId() == TradeSourceConstants.QQPP_SOURCE_ID)
			url = sourceConfigure.getQqppUrl();
		
		//信用卡订单
		
		return url;
	}
	
	private String getPostJsonUrl(String url) {
	    url = sourceConfigure.getJsonSourceUrl();
		return url;
	}

	private Long getSourceID(String from) {
		if (from.equalsIgnoreCase("CSV"))
			return TradeSourceConstants.CSV_SOURCE_ID;
		if (from.equalsIgnoreCase("TAOBAO"))
			return TradeSourceConstants.TAOBAO_SOURCE_ID;
		if (from.equalsIgnoreCase("TAOBAOC"))
			return TradeSourceConstants.TAOBAOC_SOURCE_ID;
		if (from.equalsIgnoreCase("TAOBAOZ"))
			return TradeSourceConstants.TAOBAOZ_SOURCE_ID;
		if (from.equalsIgnoreCase("JINGDONG"))
			return TradeSourceConstants.BUY360_SOURCE_ID;
		if (from.equalsIgnoreCase("JINGDONGFBP"))
			return TradeSourceConstants.BUY360FBP_SOURCE_ID;
		if (from.equalsIgnoreCase("AMAZON"))
			return TradeSourceConstants.AMAZON_SOURCE_ID;
		if (from.equalsIgnoreCase("AMAZONFBP"))
			return TradeSourceConstants.AMAZONFBP_SOURCE_ID;
		if (from.equalsIgnoreCase("CHAMA"))
			return TradeSourceConstants.CHAMA_SOURCE_ID;
		if (from.equalsIgnoreCase("TAOBAOCJ"))
			return TradeSourceConstants.TAOBAOCJ_SOURCE_ID;
		if (from.equalsIgnoreCase("TAOBAOJ"))
			return TradeSourceConstants.TAOBAOJ_SOURCE_ID;
		if (from.equalsIgnoreCase("QQPP"))
			return TradeSourceConstants.QQPP_SOURCE_ID;
		if (from.equalsIgnoreCase("HJXF"))
			return TradeSourceConstants.HJXF_SOURCE_ID;
		if (from.equalsIgnoreCase("HJXYX"))
			return TradeSourceConstants.HJXYX_SOURCE_ID;
		if (from.equalsIgnoreCase("HJXGM"))
			return TradeSourceConstants.HJXGM_SOURCE_ID;
		if (from.equalsIgnoreCase("CREDITCARDS"))
			return TradeSourceConstants.CREDITCARDS_SOURCE_ID;
		if (from.equalsIgnoreCase("DANGDANGAY"))
			return TradeSourceConstants.DANGDANGAY_SOURCE_ID;
        if(from.equalsIgnoreCase("TAOBAOCBG"))
            return TradeSourceConstants.TAOBAOCBG_SOURCE_ID;
        if(from.equalsIgnoreCase("DANGDANG"))
            return  TradeSourceConstants.DANGDANG_SOURCE_ID;
        if(from.equalsIgnoreCase("JINGDONGDSB"))
            return TradeSourceConstants.JINGDONGDSB_SOURCE_ID;
        if(from.equalsIgnoreCase("TAOBAODSB"))
            return TradeSourceConstants.TAOBAODSB_SOURCE_ID;
        if(from.equalsIgnoreCase("YIHAODIAN"))
            return 19L;          //一号店
        if(from.equalsIgnoreCase("TAOBAOPOLICY"))
            return 20L;          //淘宝医保中心

		return null;
	}

	// 拒绝退款
	@RequestMapping(value = "orderPreprocessing/refund", method = RequestMethod.POST)
	public void refund(
			@RequestParam(required = false, defaultValue = "") String ids,
			@RequestParam(required = false, defaultValue = "") String treadIds,
			HttpServletRequest request, HttpServletResponse response) {
		String message = "操作成功";
		String idss[] = ids.split(",");
		String treadId[] = treadIds.replaceAll("<br/>", "").split(",");
		Long insl[] = new Long[idss.length];
		int result = 0;
		for(int i=0  ; i <= idss.length-1; i++){
			insl[i] = Long.valueOf(idss[i]);
		}
		try {
			result = preTradeService.batchupdateConfrimState(insl, 1);
			if(result<1){
				message ="操作失败";
			}else{
				for(String tid:treadId){
				wirteLog("前置订单处理", "允许发货", tid, request);
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			message = "操作失败";
		}
		HttpUtil.ajaxReturn(response, message, "application/text");

	}
	
	//同意退款
	@RequestMapping(value = "orderPreprocessing/consent", method = RequestMethod.POST)
	public void consent(
			@RequestParam(required = false, defaultValue = "") String ids,
			@RequestParam(required = false, defaultValue = "") String treadIds,
			HttpServletRequest request, HttpServletResponse response) {
		String message = "操作成功";
		String idss[] = ids.split(",");
		String treadId[] = treadIds.replaceAll("<br/>", "").split(",");
		Long insl[] = new Long[idss.length];
		int result = 0;
		for(int i=0  ; i <= idss.length-1; i++){
			insl[i] = Long.valueOf(idss[i]);
		}
		try {
			result = preTradeService.batchupdateConfrimState(insl, 2);
			if(result<1){
				message ="操作失败";
			}else{
				for(String tid:treadId){
				wirteLog("前置订单处理", "取消发货", tid, request);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			message = "操作失败";
		}
		HttpUtil.ajaxReturn(response, message, "application/text");

	}
	
	



	public void shenhe(String[] id) {
		// 批审, 有备注不允许批审,只允许单审
		// 合单,合单并审核
		// 删除 impStatus != 13 可以删除,可以编辑
		// 压单 impStatus != 13 可以压, set impStatus= 99
		// 已发货待反馈
		// 反馈 调用订单反馈方法.
	}

    private boolean isJsonSucc(String tradeId, IgentResult p)
    {
        if(p.isSuccess())
        {
            return true;
        }
        else
        {
            if(StringUtils.isNotBlank(p.getOrderId())&&("002".equals(p.getImpState())))
            {
                log.warn("trade id:"+tradeId+" - order id:"+p.getOrderId());
                List<PreTrade> preTradeList=preTradeService.findPretrades(tradeId);
                if(preTradeList!=null&&preTradeList.size()>0)
                {
                }
                else
                {
                    p.setSuccess(true);
                    p.setImpState("013");
                }
                return true;
            }
        }
        return false;
    }

}
