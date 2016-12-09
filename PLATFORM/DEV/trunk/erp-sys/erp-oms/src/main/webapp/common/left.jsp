<!--
http://union.yihaodian.com/passport/viewBankInfo.do
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!--
<div class="admin_logout">
<c:if test="${not empty pageContext.request.remoteUser}">
<span class="admintil"><sec:authentication property="principal.username"/></span> | <a href="<c:url value='/logout'/>">Logout</a>
</c:if>
<span class="admin_tip">&#9670 </span>
</div>
-->
<div class="lft_nav">
<sec:authorize ifAllGranted="omsOrderFileUpload">
    <h1>手工订单导入</h1>
    <ul>
        <li><a href="<c:url value='/order/fileUpload'/>">手工订单导入</a></li>
    </ul>
</sec:authorize>

<sec:authorize ifAnyGranted="omsOrderPreprocess,omsOrderPreprocessCreditcards,omsOrderPreprocessYhd,omsOrderPreprocessPOLICY">
    <h1>前置订单处理 </h1>
    <ul>
        <sec:authorize ifAllGranted="omsOrderPreprocess">
            <li><a href="<c:url value='/orderPreprocessing/index/TAOBAOC'/>">淘宝C店</a></li>
            <li><a href="<c:url value='/orderPreprocessing/index/TAOBAO'/>">淘宝奥雅店</a></li>
            <li><a href="<c:url value='/orderPreprocessing/index/TAOBAOZ'/>">橡果官方直营店</a></li>
            <!--
            <li><a href="<c:url value='/orderPreprocessing/index/TAOBAOCJ'/>">淘宝C店聚划算</a></li>
            <li><a href="<c:url value='/orderPreprocessing/index/TAOBAOJ'/>">淘宝奥雅聚划算</a></li>
            -->
            <li><a href="<c:url value='/orderPreprocessing/index/QQPP'/>">奥雅QQ商城</a></li>
            <!--
            <li><a href="<c:url value='/orderPreprocessing/index/HJXF'/>">好记星分销</a></li>
            <li><a href="<c:url value='/orderPreprocessing/index/HJXYX'/>">易迅直采</a></li>
            <li><a href="<c:url value='/orderPreprocessing/index/HJXGM'/>">国美直采</a></li>
            -->



            <li><a href="<c:url value='/orderPreprocessing/index/JINGDONG'/>">京东SOP订单</a></li>
            <!--
            <li><a href="<c:url value='/orderPreprocessing/index/JINGDONGFBP'/>">京东FBP订单</a></li>
            -->
            <li><a href="<c:url value='/orderPreprocessing/index/AMAZON'/>">卓越自配</a></li>
            <!--
            <li><a href="<c:url value='/orderPreprocessing/index/AMAZONFBP'/>">卓越商配</a></li>
-->
            <li><a href="<c:url value='/orderPreprocessing/index/CHAMA'/>">茶马订单</a></li>
            <li><a href="<c:url value='/orderPreprocessing/index/DANGDANGAY'/>">当当网奥雅旗舰店</a></li>
            <!--
            <li><a href="<c:url value='/orderPreprocessing/index/CSV'/>">其他统计订单</a></li>
            -->
        </sec:authorize>
        <sec:authorize ifAllGranted="omsOrderPreprocessCreditcards">
            <li><a href="<c:url value='/orderPreprocessing/index/CREDITCARDS'/>">信用卡订单</a></li>
        </sec:authorize>

        <sec:authorize ifAllGranted="omsOrderPreprocessCreditcards">
            <li><a href="<c:url value='/orderPreprocessing/index/TAOBAOCBG'/>">CBG-天猫店</a></li>
        </sec:authorize>

        <sec:authorize ifAllGranted="omsOrderPreprocessCreditcards">
            <li><a href="<c:url value='/orderPreprocessing/index/DANGDANG'/>">当当网</a></li>
        </sec:authorize>

        <sec:authorize ifAllGranted="omsOrderPreprocessCreditcards">
            <li><a href="<c:url value='/orderPreprocessing/index/JINGDONGDSB'/>">电子商务部-京东SOP</a></li>
        </sec:authorize>

        <sec:authorize ifAllGranted="omsOrderPreprocessYhd">
            <li><a href="<c:url value='/orderPreprocessing/index/YIHAODIAN'/>">一号店</a></li>
        </sec:authorize>

        <sec:authorize ifAllGranted="omsOrderPreprocessCreditcards">
            <li><a href="<c:url value='/orderPreprocessing/index/TAOBAODSB'/>">电子商务部-TAOBAO</a></li>
        </sec:authorize>

        <sec:authorize ifAllGranted="omsOrderPreprocessPOLICY">
            <li><a href="<c:url value='/orderPreprocessing/index/TAOBAOPOLICY'/>">淘宝医保中心</a></li>
        </sec:authorize>

    </ul>
</sec:authorize>
<sec:authorize ifAllGranted="omsOrderFreeBack">
    <h1>订单反馈</h1>
    <ul>
        <li><a href="<c:url value='/orderFreeback/index'/>">订单反馈</a></li>
        <li><a href="<c:url value='/orderFreeback/findResult'/>">反馈结果查询</a></li>
    </ul>
</sec:authorize>
<!--
<sec:authorize ifAllGranted="omsOrderFileUpload">
    <h1>结算单导入</h1>
    <ul>
        <li><a href="<c:url value='/taobao/fileUpload'/>">淘宝结算单导入</a></li>
        <li><a href="<c:url value='/jingdong/fileUpload'/>">京东结算单导入</a></li>
        <li><a href="<c:url value='/amazon/fileUpload'/>">卓越结算单导入</a></li>
    </ul>
</sec:authorize>
<sec:authorize ifAllGranted="omsOrderSettleFind">
    <h1>结算单查询</h1>
    <ul>
        <li><a href="<c:url value='/taobao/settle/10'/>">淘宝C店结算单</a></li>
        <li><a href="<c:url value='/taobao/settle/9'/>">天猫自营店结算单</a></li>
        <li><a href="<c:url value='/taobao/settle/2'/>">淘宝奥雅结算单</a></li>
        <li><a href="<c:url value='/taobao/settle/1'/>">淘宝（聚划算）结算单</a></li>
        <li><a href="<c:url value='/jingdong/settle/3'/>">京东SOP结算单</a></li>
        <li><a href="<c:url value='/jingdong/settle/6'/>">京东FBP结算单</a></li>
        <li><a href="<c:url value='/amazon/settle/4'/>">卓越自配结算单</a></li>
        <li><a href="<c:url value='/amazon/settle/8'/>">卓越商配结算单</a></li>
    </ul>
</sec:authorize>
<sec:authorize ifAllGranted="omsInventoryFind">
    <h1>库存同步查询</h1>
    <ul>
        <li><a href="<c:url value='/taobao/inventory/10'/>">淘宝C店库存</a></li>
        <li><a href="<c:url value='/taobao/inventory/9'/>">天猫自营店库存</a></li>
        <li><a href="<c:url value='/taobao/inventory/2'/>">淘宝奥雅库存</a></li>
        <li><a href="<c:url value='/taobao/inventory/1'/>">淘宝（聚划算）库存</a></li>
        <li><a href="<c:url value='/jingdong/inventory/3'/>">京东SOP库存</a></li>
        <li><a href="<c:url value='/jingdong/inventory/6'/>">京东FBP库存</a></li>
        <li><a href="<c:url value='/amazon/inventory/4'/>">卓越自配库存</a></li>
        <li><a href="<c:url value='/amazon/inventory/8'/>">卓越商配库存</a></li>
    </ul>
</sec:authorize>
-->
<sec:authorize ifAllGranted="omsOrderProcess">
    <h1>订单处理</h1>
    <ul>
        <li><a href="<c:url value='/order/backorder/fileUpload'/>">订单压单导入</a></li>
        <li><a href="<c:url value='/order/backorder'/>">订单压单查询</a></li>
    </ul>
</sec:authorize>

<sec:authorize ifAnyGranted="omsRefundSend,omsRefundCheckIn,omsRefundRegister,omsRefundSend,omsOrderLogisticsStatus,omsAvoidFreight">
    <h1>配送跟踪</h1>
    <ul>
        <sec:authorize ifAllGranted="omsAvoidFreight">
            <li><a href="<c:url value='/freight/avoidFreight'/>">免运费登记</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsRefundCheckIn">
            <li><a href="<c:url value='/shipment/refund/checkIn'/>">半签收订单登记</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsRefundRegister">
            <li><a href="<c:url value='/shipment/refundRegister'/>">半拒收退货登记</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsRefundSend">
            <li><a href="<c:url value='/shipment/refundSend'/>">半拒收退货确认</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsOrderLogisticsStatus">
            <li><a href="<c:url value='/logisticsStatus/fileUpload'/>">物流状态反馈</a></li>
        </sec:authorize>
        <%--<sec:authorize ifAllGranted="omsRefundSend">
            <li><a href="<c:url value='/report/orderDetailsSearch'/>">订单详细信息查询</a></li>
        </sec:authorize>--%>
    </ul>
</sec:authorize>
<sec:authorize ifAnyGranted="omsShipmentLogistics,omsOrderAuth,omsShipmentIndx,omsShipmentStock,omsOrderHistIndex,omsOrderPaymentConfirm,omsRequestCarrier,omsOrderHistIndexNew">
    <h1>发运单管理</h1>
    <ul>
        <sec:authorize ifAllGranted="omsShipmentStock">
            <li><a href="<c:url value='/shipment/stock'/>">库内改单</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsShipmentLogistics">
            <li><a href="<c:url value='/shipment/logistics'/>">承运商指派</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsOrderAuth">
            <li><a href="<c:url value='/order/auth'/>">信用卡索权导入</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsOrderHistIndex">
            <li><a href="<c:url value='/orderhist/index'/>">承运商挑单</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsOrderHistIndexNew">
            <li><a href="<c:url value='/orderhistNew/index'/>">承运商挑单(新版)</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsShipmentIndx">
            <li><a href="<c:url value='/shipment/index'/>">运单重发</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsOrderPaymentConfirm">
            <li><a href="<c:url value='/shipment/orderPaymentConfirm'/>">订单付款确认</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsRequestCarrier">
            <li><a href="<c:url value='/shipment/omsRequestCarrier'/>">请求EMS发货</a></li>
        </sec:authorize>
    </ul>
</sec:authorize>

<sec:authorize ifAnyGranted="omsShipmentContract,omsPriceRule,omsWeightRule,omsAddreMaintain,omsCarrierIndex">
    <h1>基础资料</h1>
    <ul>
        <sec:authorize ifAllGranted="omsShipmentContract">
            <li><a href="<c:url value='/company/contract'/>">承运商合同维护</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsPriceRule">
            <li><a href="<c:url value='/company/priceRule'/>">计费规则维护（金额）</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsWeightRule">
            <li><a href="<c:url value='/company/weightRule'/>">计费规则维护（重量）</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsAddreMaintain">
            <li><a href="<c:url value='/addreMaintain/index'/>">地址组维护</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsCarrierIndex">
            <li><a href="<c:url value='/carrier/index'/>">匹配规则主表维护</a></li>
        </sec:authorize>
    </ul>
</sec:authorize>
<%--报表统计--%>
<sec:authorize ifAnyGranted="omsContractDailyCheck,omsSendPackageInventory,omsDishonourOrderList,omsDishonourDailyCheck,omsPaymentCheck,omsSettlePaidan,omsCompanyPayment,omsSettlePostFee,omsRefundFeeReport,omsPaymentReport">
    <h1>核帐管理</h1>
    <ul>
        <sec:authorize ifAllGranted="omsContractDailyCheck">
            <li><a href="<c:url value='/report/contractDailyCheck'/>">发包日报核对表</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsSendPackageInventory">
            <li><a href="<c:url value='/report/sendPackageInventory'/>">发包清单查询</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsDishonourOrderList">
            <li><a href="<c:url value='/report/dishonourOrderList'/>">退包清单查询</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsDishonourDailyCheck">
            <li><a href="<c:url value='/report/dishonourDailyCheck'/>">拒收日报核对表</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsPaymentCheck">
            <li><a href="<c:url value='/report/paymentCheck'/>">应收应付款核对查询</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsSettlePaidan">
            <li><a href="<c:url value='/company/settle'/>">核销付款单拍平</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsCompanyPayment">
            <li><a href="<c:url value='/company/companyPayment'/>">核销付款单录入</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsSettlePostFee">
            <li><a href="<c:url value='/report/freightCheck'/>">运费对账报表</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsRefundFeeReport">
            <li><a href="<c:url value='/report/rejectionAndPostfee'/>">半拒收免运费报表</a></li>
        </sec:authorize>
        <sec:authorize ifAllGranted="omsPaymentReport">
            <li><a href="<c:url value='/report/rebates'/>">返款报表</a></li>
        </sec:authorize>
    </ul>
</sec:authorize>
<%--订单追踪报表--%>
<sec:authorize ifAllGranted="omsReportOrderChama">
    <h1>订单追踪报表</h1>
    <ul>
        <li><a href="<c:url value='/report/orderChama'/>">茶马古道订单追踪查询</a></li>
    </ul>
</sec:authorize>

<sec:authorize ifAllGranted="OrderPrice_Adjustment_Service">
    <h1>订单金额折扣处理</h1>
    <ul>
        <li><a href="<c:url value='/lower/price'/>">客服价格调整</a></li>
    </ul>
</sec:authorize>
<sec:authorize ifAllGranted="WH_KPI_REPORT">
    <h1>仓库KPI报表</h1>
    <ul>
        <li><a href="<c:url value='/report/inBoundPerformance'/>">入库作业绩效</a></li>
        <li><a href="<c:url value='/report/outBoundPerformance'/>">出库作业绩效</a></li>
        <li><a href="<c:url value='/report/inventoryDifferencePapers'/>">库存差异（纸张盘点）</a></li>
        <li><a href="<c:url value='/report/inventoryDifferenceRF'/>">库存差异（RF盘点）</a></li>
        <li><a href="<c:url value='/report/warehouseInventoryDamaged'/>">在库损耗</a></li>
        <li><a href="<c:url value='/report/orderTaskKpi'/>">订单作业</a></li>

    </ul>
</sec:authorize>

<sec:authorize ifAllGranted="omsPublicFunc">
    <h1>系统管理</h1>
    <ul>
        <li><a href="<c:url value='/admin/auditLogs'/>">操作日志</a></li>
        <li><a href="<c:url value='/admin/esbAuditLogs'/>">系统错误</a></li>
    </ul>
</sec:authorize>
</div>