package com.chinadrtv.erp.constant;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-29
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class BusinessTypeConstants {
    /**
     * 送货单(初次采购)(101)
     */
    public static final String ORDER_IN_TRANSIT_PURCHASE_FIRST = "101";
    /**
     * 送货单(采购订单)(102)
     */
    public static final String ORDER_IN_TRANSIT_PURCHASE = "102";
    /**
     * 销售申请单（除了5、6类型）(103)
     */
    public static final String ORDER_IN_TRANSIT_SALES_CUSTOMER = "103";
    /**
     * 退包通知单(104)
     */
    public static final String ORDER_IN_TRANSIT_RETURN_DELIVERY = "104";
    /**
     * 销售申请单
     * (5、6类型)
     * (105)
     */
    public static final String ORDER_IN_TRANSIT_SALES_WHOLESALE = "105";

    /**
     * 调拨申请单
     * 售后退货商品入库
     * 售后换出包裹商品
     * (106)
     */
    public static final String ORDER_IN_TRANSIT_TRANSFER_CROSS_COMPANY = "106";
    /**
     *  未使用
     */
    public static final String ORDER_IN_TRANSIT_IN_OTHER = "107";
    /**
     * 返修申请单(实体入) (108)
     */
    public static final String ORDER_IN_TRANSIT_FIX_VENDOR = "108";
    /**
     *  未使用
     */
    public static final String ORDER_IN_TRANSIT_AFTER_SALES = "109";
    /**
     * 借用申请单(实体入) (110)
     */
    public static final String ORDER_IN_TRANSIT_LOAN = "110";
    /**
     * 转库申请单(111)
     */
    public static final String ORDER_IN_TRANSIT_TRANSFER = "111";
    /**
     * 退包通知单(112)
     */
    public static final String ORDER_IN_TRANSIT_RETURN_HALF = "112";
    /**
     * 直销订单
     * 销售申请单
     * (1、2类型）
     * (202)
     */
    public static final String ORDER_ALLOCATED_RETAIL_COMPANY = "201";
    /**
     * 直销订单
     * 销售申请单
     * (1、2类型）
     * (202)
     */
    public static final String ORDER_ALLOCATED_RETAIL_CROSS_COMPANY = "202";
    /**
     * 销售申请单
     * （3代送销售）
     * (203)
     */
    public static final String ORDER_ALLOCATED_SHIPPING = "203";
    /**
     * 销售申请单
     * （4跨公司代送销售）
     */
    public static final String ORDER_ALLOCATED_SHIPPING_CROSS_COMPANY = "204";
    /**
     * 销售申请单
     * （5批发销售）
     * (205)
     */
    public static final String ORDER_ALLOCATED_WHOLESALES = "205";
    /**
     * 销售申请单
     * （6跨公司批发销售）
     * (206)
     */
    public static final String ORDER_ALLOCATED_WHOLESALES_CROSS_COMPANY = "206";
    /**
     * 销售申请单
     * （7委托代销）
     * (207)
     */
    public static final String ORDER_ALLOCATED_SALES_DELEGATION = "207";
    /**
     * 退货申请单(208)
     */
    public static final String ORDER_ALLOCATED_RETURN_PROCUREMENT   = "208";
    /**
     * 调拨申请单
     * 售后检测补发出库
     * 售后换入包裹商品
     * (209)
     */
    public static final String ORDER_ALLOCATED_TRANSFER_CROSS_COMPANY   = "209";
    /**
     * 返修申请单(实体出)
     * (211)
     */
    public static final String ORDER_ALLOCATED_FIX_VENDOR   = "211";
    /**
     * 借用申请单(实体出)(213)
     */
    public static final String ORDER_ALLOCATED_LOAN   = "213";
    /**
     * 转库申请单(214)
     */
    public static final String ORDER_ALLOCATED_TRANSFER   = "214";
    /**
     * 领用申请单
     * (1领用)
     * (215)
     */
    public static final String ORDER_ALLOCATED_REQUISITION_USE  = "215";
    /**
     * 销售申请单
     * （16跨公司批发销售）
     * (216)
     */
    public static final String ORDER_ALLOCATED_ONLINE_SALES_CROSS_COMPANY  = "216";
    /**
     * 领用申请单
     * (2报废)
     * (217)
     */
    public static final String ORDER_ALLOCATED_REQUISITION_SCRAP  = "217";
    /**
     * 促销活动领用
     * (217)
     */
    public static final String ORDER_ALLOCATED_PROMOTION_SCRAP  = "218";
    /**
     * 更换货主(302)：物权变更
     */
    public static final String ORDER_TRANSIT_OWNER_RIGHTS  = "302";

    public static final String[] ORDER_IN_TRANSIT = {
            BusinessTypeConstants.ORDER_IN_TRANSIT_PURCHASE,
            BusinessTypeConstants.ORDER_IN_TRANSIT_TRANSFER_CROSS_COMPANY,
            BusinessTypeConstants.ORDER_IN_TRANSIT_TRANSFER,
            BusinessTypeConstants.ORDER_IN_TRANSIT_SALES_CUSTOMER,
            BusinessTypeConstants.ORDER_IN_TRANSIT_RETURN_DELIVERY,
            BusinessTypeConstants.ORDER_IN_TRANSIT_SALES_WHOLESALE,
            BusinessTypeConstants.ORDER_IN_TRANSIT_FIX_VENDOR,
            BusinessTypeConstants.ORDER_IN_TRANSIT_LOAN,
            BusinessTypeConstants.ORDER_IN_TRANSIT_RETURN_HALF
    };

    public static final String[] ORDER_IN_TRANSIT_PROCUREMENTS = {
            BusinessTypeConstants.ORDER_IN_TRANSIT_PURCHASE
    };

    public static final String[] ORDER_IN_TRANSIT_MOVEMENTS = {
            BusinessTypeConstants.ORDER_IN_TRANSIT_TRANSFER_CROSS_COMPANY,
            BusinessTypeConstants.ORDER_IN_TRANSIT_TRANSFER
    };

    public static final String[] ORDER_IN_TRANSIT_OTHERS = {
            BusinessTypeConstants.ORDER_IN_TRANSIT_SALES_CUSTOMER,
            BusinessTypeConstants.ORDER_IN_TRANSIT_RETURN_DELIVERY,
            BusinessTypeConstants.ORDER_IN_TRANSIT_SALES_WHOLESALE,
            BusinessTypeConstants.ORDER_IN_TRANSIT_FIX_VENDOR,
            BusinessTypeConstants.ORDER_IN_TRANSIT_LOAN,
            BusinessTypeConstants.ORDER_IN_TRANSIT_RETURN_HALF,
            BusinessTypeConstants.ORDER_IN_TRANSIT_IN_OTHER,
            BusinessTypeConstants.ORDER_IN_TRANSIT_AFTER_SALES
    };

    public static final String[] ORDER_ALLOCATED = {
            BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_CROSS_COMPANY,
            BusinessTypeConstants.ORDER_ALLOCATED_SHIPPING,
            BusinessTypeConstants.ORDER_ALLOCATED_SHIPPING_CROSS_COMPANY,
            BusinessTypeConstants.ORDER_ALLOCATED_WHOLESALES,
            BusinessTypeConstants.ORDER_ALLOCATED_WHOLESALES_CROSS_COMPANY,
            BusinessTypeConstants.ORDER_ALLOCATED_SALES_DELEGATION,
            BusinessTypeConstants.ORDER_ALLOCATED_RETURN_PROCUREMENT,
            BusinessTypeConstants.ORDER_ALLOCATED_TRANSFER_CROSS_COMPANY,
            BusinessTypeConstants.ORDER_ALLOCATED_FIX_VENDOR,
            BusinessTypeConstants.ORDER_ALLOCATED_LOAN,
            BusinessTypeConstants.ORDER_ALLOCATED_TRANSFER,
            BusinessTypeConstants.ORDER_ALLOCATED_REQUISITION_USE,
            BusinessTypeConstants.ORDER_ALLOCATED_REQUISITION_SCRAP,
            BusinessTypeConstants.ORDER_ALLOCATED_PROMOTION_SCRAP,
            BusinessTypeConstants.ORDER_ALLOCATED_ONLINE_SALES_CROSS_COMPANY
    };

    /**
     * 直销
     */
    public static final String[] ORDER_ALLOCATED_RETAILS = {
            BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_COMPANY,
            BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_CROSS_COMPANY
    };
    /**
     * 分销
     */
    public static final String[] ORDER_ALLOCATED_DISTRIBUTIONS = {
            BusinessTypeConstants.ORDER_ALLOCATED_WHOLESALES,
            BusinessTypeConstants.ORDER_ALLOCATED_WHOLESALES_CROSS_COMPANY
    };
    /**
     * 其他
     */
    public static final String[] ORDER_ALLOCATED_OTHERS = {
            BusinessTypeConstants.ORDER_ALLOCATED_SHIPPING,
            BusinessTypeConstants.ORDER_ALLOCATED_SHIPPING_CROSS_COMPANY,
            BusinessTypeConstants.ORDER_ALLOCATED_SALES_DELEGATION,
            BusinessTypeConstants.ORDER_ALLOCATED_RETURN_PROCUREMENT,
            BusinessTypeConstants.ORDER_ALLOCATED_TRANSFER_CROSS_COMPANY,
            BusinessTypeConstants.ORDER_ALLOCATED_FIX_VENDOR,
            BusinessTypeConstants.ORDER_ALLOCATED_LOAN,
            BusinessTypeConstants.ORDER_ALLOCATED_TRANSFER,
            BusinessTypeConstants.ORDER_ALLOCATED_REQUISITION_USE,
            BusinessTypeConstants.ORDER_ALLOCATED_REQUISITION_SCRAP,
            BusinessTypeConstants.ORDER_ALLOCATED_PROMOTION_SCRAP,
            BusinessTypeConstants.ORDER_ALLOCATED_ONLINE_SALES_CROSS_COMPANY
    };

    /**
     * 1.期初采购(101)
     */
    public static final String INVENTORY_STOCK_IN_OPENING = "151";
    /**
     * 2.日常采购(102)
     */
    public static final String INVENTORY_STOCK_IN_PURCHASE = "152";
    /**
     * 3.客退入库(103)
     */
    public static final String INVENTORY_STOCK_IN_RETURN_CUSTOMER = "153";
    /**
     * 4.送货公司退货(104)
     */
    public static final String INVENTORY_STOCK_IN_RETURN_DELIVERY = "154";
    /**
     * 5.批发退货入库(105)
     */
    public static final String INVENTORY_STOCK_IN_RETURN_WHOLESALE = "155";
    /**
     * 6.调拨入库(106)
     */
    public static final String INVENTORY_STOCK_IN_TRANSFER_CROSS_COMPANY = "156";
    /**
     * 7.其他入库(107)
     */
    public static final String INVENTORY_STOCK_IN_OTHER = "157";
    /**
     * 8.厂商返修入库(108)
     */
    public static final String INVENTORY_STOCK_IN_FIX_VENDOR = "158";
    /**
     * 9.售后入库(109)
     */
    public static final String INVENTORY_STOCK_IN_AFTER_SALES = "159";
    /**
     * 10.借用归还入库(110)
     */
    public static final String INVENTORY_STOCK_IN_LOAN = "160";
    /**
     * 11.转入入库(111)
     */
    public static final String INVENTORY_STOCK_IN_TRANSFER = "161";
    /**
     * 12.货运公司半退包(112)
     */
    public static final String INVENTORY_STOCK_IN_RETURN_HALF = "162";
    /**
     * 1.IAGENT零售(201)
     */
    public static final String INVENTORY_STOCK_OUT_RETAIL = "251";
    /**
     * 2.跨公司IAGENT零售(202)
     */
    public static final String INVENTORY_STOCK_OUT_RETAIL_CROSS_COMPANY = "252";
    /**
     * 3.代送销售(203)
     */
    public static final String INVENTORY_STOCK_OUT_SHIPPING = "253";
    /**
     * 4.跨公司代送销售(204)
     */
    public static final String INVENTORY_STOCK_OUT_SHIPPING_CROSS_COMPANY = "254";
    /**
     * 5.批发销售(205)
     */
    public static final String INVENTORY_STOCK_OUT_WHOLESALES = "255";
    /**
     * 6.跨公司批发销售(206)
     */
    public static final String INVENTORY_STOCK_OUT_WHOLESALES_CROSS_COMPANY = "256";
    /**
     * 7.委托代销(207)
     */
    public static final String INVENTORY_STOCK_OUT_SALES_DELEGATION = "257";
    /**
     * 8.采购退库(208)
     */
    public static final String INVENTORY_STOCK_OUT_RETURN_PROCUREMENT   = "258";
    /**
     * 9.调拨出库(209)
     */
    public static final String INVENTORY_STOCK_OUT_TRANSFER_CROSS_COMPANY   = "259";
    /**
     * 10.其他出库(210)
     */
    public static final String INVENTORY_STOCK_OUT_OTHER   = "260";
    /**
     * 11.厂商返修出库(211)
     */
    public static final String INVENTORY_STOCK_OUT_FIX_VENDOR   = "261";
    /**
     * 12.售后出库(212)
     */
    public static final String INVENTORY_STOCK_OUT_AFTER_SALES   = "262";
    /**
     * 13.借用出库(213)
     */
    public static final String INVENTORY_STOCK_OUT_LOAN   = "263";
    /**
     * 14.转库出库(214)
     */
    public static final String INVENTORY_STOCK_OUT_TRANSFER   = "264";
    /**
     * 15.领用出库(215)
     */
    public static final String INVENTORY_STOCK_OUT_REQUISITION_USE  = "265";
    /**
     * 16.跨公司网络销售(216)
     */
    public static final String INVENTORY_STOCK_OUT_ONLINE_SALES_CROSS_COMPANY  = "266";
    /**
     * 17.领用出库(215)
     */
    public static final String INVENTORY_STOCK_OUT_REQUISITION_SCRAP  = "267";
    /**
     * 18.促销活动领用
     * (268)
     */
    public static final String INVENTORY_STOCK_OUT_PROMOTION_SCRAP  = "268";
    /**
     * 1.商品库存调整(301)
     */
    public static final String INVENTORY_STOCK_RECOUNT  = "351";
    /**
     * 2.更换货主(302)：物权变更
     */
    public static final String INVENTORY_STOCK_OWNER_RIGHTS  = "352";
    /**
     * 3.商品状态调整(303)：库内移动
     */
    public static final String INVENTORY_STOCK_MOVEMENT  = "353";
    /**
     * 入库
     */
    public static final String[] INVENTORY_STOCK_IN = {
            BusinessTypeConstants.INVENTORY_STOCK_IN_OPENING,
            BusinessTypeConstants.INVENTORY_STOCK_IN_PURCHASE,
            BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_CUSTOMER,
            BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_DELIVERY,
            BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_WHOLESALE,
            BusinessTypeConstants.INVENTORY_STOCK_IN_TRANSFER_CROSS_COMPANY,
            BusinessTypeConstants.INVENTORY_STOCK_IN_OTHER,
            BusinessTypeConstants.INVENTORY_STOCK_IN_FIX_VENDOR,
            BusinessTypeConstants.INVENTORY_STOCK_IN_AFTER_SALES,
            BusinessTypeConstants.INVENTORY_STOCK_IN_LOAN,
            BusinessTypeConstants.INVENTORY_STOCK_IN_TRANSFER,
            BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_HALF
    };
    /**
     * 采购入库
     */
    public static final String[] INVENTORY_STOCK_IN_PROCUREMENTS = {
            BusinessTypeConstants.INVENTORY_STOCK_IN_PURCHASE
    };
    /**
     * 移动入库
     */
    public static final String[] INVENTORY_STOCK_IN_MOVEMENTS = {
            BusinessTypeConstants.INVENTORY_STOCK_IN_TRANSFER_CROSS_COMPANY,
            BusinessTypeConstants.INVENTORY_STOCK_IN_TRANSFER
    };
    /**
     * 其他入库
     */
    public static final String[] INVENTORY_STOCK_IN_OTHERS = {
            BusinessTypeConstants.INVENTORY_STOCK_IN_OPENING,
            BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_CUSTOMER,
            BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_DELIVERY,
            BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_WHOLESALE,
            BusinessTypeConstants.INVENTORY_STOCK_IN_OTHER,
            BusinessTypeConstants.INVENTORY_STOCK_IN_FIX_VENDOR,
            BusinessTypeConstants.INVENTORY_STOCK_IN_AFTER_SALES,
            BusinessTypeConstants.INVENTORY_STOCK_IN_LOAN,
            BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_HALF
    };

    public static final String[] INVENTORY_STOCK_OUT = {
            BusinessTypeConstants.INVENTORY_STOCK_OUT_RETAIL,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_RETAIL_CROSS_COMPANY,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_SHIPPING,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_SHIPPING_CROSS_COMPANY,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_WHOLESALES,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_WHOLESALES_CROSS_COMPANY,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_SALES_DELEGATION,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_RETURN_PROCUREMENT,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_TRANSFER_CROSS_COMPANY,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_OTHER,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_FIX_VENDOR,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_AFTER_SALES,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_LOAN,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_TRANSFER,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_REQUISITION_USE,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_REQUISITION_SCRAP,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_PROMOTION_SCRAP,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_ONLINE_SALES_CROSS_COMPANY
    };

    /**
     * 直销
     */
    public static final String[] INVENTORY_STOCK_OUT_RETAILS = {
            BusinessTypeConstants.INVENTORY_STOCK_OUT_RETAIL,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_RETAIL_CROSS_COMPANY
    };
    /**
     * 分销
     */
    public static final String[] INVENTORY_STOCK_OUT_DISTRIBUTIONS = {
            BusinessTypeConstants.INVENTORY_STOCK_OUT_WHOLESALES,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_WHOLESALES_CROSS_COMPANY,
    };
    /**
     * 分销
     */
    public static final String[] INVENTORY_STOCK_OUT_OTHERS = {
            BusinessTypeConstants.INVENTORY_STOCK_OUT_SHIPPING,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_SHIPPING_CROSS_COMPANY,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_SALES_DELEGATION,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_RETURN_PROCUREMENT,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_TRANSFER_CROSS_COMPANY,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_OTHER,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_FIX_VENDOR,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_AFTER_SALES,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_LOAN,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_TRANSFER,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_REQUISITION_USE,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_REQUISITION_SCRAP,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_PROMOTION_SCRAP,
            BusinessTypeConstants.INVENTORY_STOCK_OUT_ONLINE_SALES_CROSS_COMPANY
    };

    /**
     * 事物业务类型转化为单据类型
     * @param businessType
     * @return
     */
    public static final String BusinessType2ReceiptTypeWMS(String businessType){
        if (INVENTORY_STOCK_IN_OPENING.equalsIgnoreCase(businessType)) {
            return "1";
        } else if (INVENTORY_STOCK_IN_PURCHASE.equalsIgnoreCase(businessType)) {
            return "2";
        } else if (INVENTORY_STOCK_IN_RETURN_CUSTOMER.equalsIgnoreCase(businessType)) {
            return "3";
        } else if (INVENTORY_STOCK_IN_RETURN_DELIVERY.equalsIgnoreCase(businessType)) {
            return "4";
        } else if (INVENTORY_STOCK_IN_RETURN_WHOLESALE.equalsIgnoreCase(businessType)) {
            return "5";
        } else if (INVENTORY_STOCK_IN_TRANSFER_CROSS_COMPANY.equalsIgnoreCase(businessType)) {
            return "6";
        } else if (INVENTORY_STOCK_IN_OTHER.equalsIgnoreCase(businessType)) {
            return "7";
        } else if (INVENTORY_STOCK_IN_FIX_VENDOR.equalsIgnoreCase(businessType)) {
            return "8";
        } else if (INVENTORY_STOCK_IN_AFTER_SALES.equalsIgnoreCase(businessType)) {
            return "9";
        } else if (INVENTORY_STOCK_IN_LOAN.equalsIgnoreCase(businessType)) {
            return "10";
        } else if (INVENTORY_STOCK_IN_TRANSFER.equalsIgnoreCase(businessType)) {
            return "11";
        } else if (INVENTORY_STOCK_IN_RETURN_HALF.equalsIgnoreCase(businessType)) {
            return "12";
        }
        return businessType;
    }

    /**
     * 单据类型转化为事物业务类型
     * @param receiptType
     * @return
     */
    public static final String ReceiptType2BusinessTypeWMS(String receiptType){
        if ("1".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_OPENING);
        } else if ("2".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_PURCHASE);
        } else if ("3".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_RETURN_CUSTOMER);
        } else if ("4".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_RETURN_DELIVERY);
        } else if ("5".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_RETURN_WHOLESALE);
        } else if ("6".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_TRANSFER_CROSS_COMPANY);
        } else if ("7".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_OTHER);
        } else if ("8".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_FIX_VENDOR);
        } else if ("9".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_AFTER_SALES);
        } else if ("10".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_LOAN);
        } else if ("11".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_TRANSFER);
        } else if ("12".equalsIgnoreCase(receiptType)) {
            return (INVENTORY_STOCK_IN_RETURN_HALF);
        }
        return receiptType;
    }

    /**
     * 单据类型转化为事物业务类型
     * @param receiptType
     * @return
     */
    public static final String ReceiptType2BusinessTypeSCM(String receiptType){
        if ("1".equalsIgnoreCase(receiptType)) {
            return ORDER_IN_TRANSIT_PURCHASE_FIRST;
        } else if ("2".equalsIgnoreCase(receiptType)) {
            return (ORDER_IN_TRANSIT_PURCHASE);
        } else if ("3".equalsIgnoreCase(receiptType)) {
            return (ORDER_IN_TRANSIT_SALES_CUSTOMER);
        } else if ("4".equalsIgnoreCase(receiptType)) {
            return (ORDER_IN_TRANSIT_RETURN_DELIVERY);
        } else if ("5".equalsIgnoreCase(receiptType)) {
            return (ORDER_IN_TRANSIT_SALES_WHOLESALE);
        } else if ("6".equalsIgnoreCase(receiptType)) {
            return ORDER_IN_TRANSIT_TRANSFER_CROSS_COMPANY;
        } else if ("7".equalsIgnoreCase(receiptType)) {
            return ORDER_IN_TRANSIT_IN_OTHER;
        } else if ("8".equalsIgnoreCase(receiptType)) {
            return (ORDER_IN_TRANSIT_FIX_VENDOR);
        } else if ("9".equalsIgnoreCase(receiptType)) {
            return (ORDER_IN_TRANSIT_AFTER_SALES);
        } else if ("10".equalsIgnoreCase(receiptType)) {
            return (ORDER_IN_TRANSIT_LOAN);
        } else if ("11".equalsIgnoreCase(receiptType)) {
            return (ORDER_IN_TRANSIT_TRANSFER);
        } else if ("12".equalsIgnoreCase(receiptType)) {
            return (ORDER_IN_TRANSIT_RETURN_HALF);
        }
        return receiptType;
    }

    public static final String BusinessType2ReceiptTypeSCM(String businessType){
        if (ORDER_IN_TRANSIT_PURCHASE_FIRST.equalsIgnoreCase(businessType)) {
            return "1";
        } else if (ORDER_IN_TRANSIT_PURCHASE.equalsIgnoreCase(businessType)) {
            return "2";
        } else if (ORDER_IN_TRANSIT_SALES_CUSTOMER.equalsIgnoreCase(businessType)) {
            return "3";
        } else if (ORDER_IN_TRANSIT_RETURN_DELIVERY.equalsIgnoreCase(businessType)) {
            return "4";
        } else if (ORDER_IN_TRANSIT_SALES_WHOLESALE.equalsIgnoreCase(businessType)) {
            return "5";
        } else if (ORDER_IN_TRANSIT_TRANSFER_CROSS_COMPANY.equalsIgnoreCase(businessType)) {
            return "6";
        } else if (ORDER_IN_TRANSIT_IN_OTHER.equalsIgnoreCase(businessType)) {
            return "7";
        } else if (ORDER_IN_TRANSIT_FIX_VENDOR.equalsIgnoreCase(businessType)) {
            return "8";
        } else if (ORDER_IN_TRANSIT_AFTER_SALES.equalsIgnoreCase(businessType)) {
            return "9";
        } else if (ORDER_IN_TRANSIT_LOAN.equalsIgnoreCase(businessType)) {
            return "10";
        } else if (ORDER_IN_TRANSIT_TRANSFER.equalsIgnoreCase(businessType)) {
            return "11";
        } else if (ORDER_IN_TRANSIT_RETURN_HALF.equalsIgnoreCase(businessType)) {
            return "12";
        }
        return businessType;
    }

    public static final String OrderType2BusinessTypeAPP(String orderType)
    {
        String businessType = orderType;
        if("1".equalsIgnoreCase(orderType))
            businessType = "201";
        else if("2".equalsIgnoreCase(orderType))
            businessType = "202";
        else if("3".equalsIgnoreCase(orderType))
            businessType = "203";
        else if("4".equalsIgnoreCase(orderType))
            businessType = "204";
        else if("5".equalsIgnoreCase(orderType))
            businessType = "205";
        else if("6".equalsIgnoreCase(orderType))
            businessType = "206";
        else if("7".equalsIgnoreCase(orderType))
            businessType = "207";
        else if("8".equalsIgnoreCase(orderType))
            businessType = "208";
        else if("9".equalsIgnoreCase(orderType))
            businessType = "209";
        else if("10".equalsIgnoreCase(orderType))
            businessType = "210";
        else if("11".equalsIgnoreCase(orderType))
            businessType = "211";
        else if("12".equalsIgnoreCase(orderType))
            businessType = "212";
        else if("13".equalsIgnoreCase(orderType))
            businessType = "213";
        else if("14".equalsIgnoreCase(orderType))
            businessType = "214";
        else if("15".equalsIgnoreCase(orderType))
            businessType = "215";
        else if("16".equalsIgnoreCase(orderType))
            businessType = "216";
        else if("17".equalsIgnoreCase(orderType))
            businessType = "217";
        else if("18".equalsIgnoreCase(orderType))
            businessType = "218";
        return businessType;
    }

    public static final String OrderType2BusinessTypeWMS(String orderType){
        String businessType = orderType;
        if("1".equalsIgnoreCase(orderType))
            businessType = "251";
        else if("2".equalsIgnoreCase(orderType))
            businessType = "252";
        else if("3".equalsIgnoreCase(orderType))
            businessType = "253";
        else if("4".equalsIgnoreCase(orderType))
            businessType = "254";
        else if("5".equalsIgnoreCase(orderType))
            businessType = "255";
        else if("6".equalsIgnoreCase(orderType))
            businessType = "256";
        else if("7".equalsIgnoreCase(orderType))
            businessType = "257";
        else if("8".equalsIgnoreCase(orderType))
            businessType = "258";
        else if("9".equalsIgnoreCase(orderType))
            businessType = "259";
        else if("10".equalsIgnoreCase(orderType))
            businessType = "260";
        else if("11".equalsIgnoreCase(orderType))
            businessType = "261";
        else if("12".equalsIgnoreCase(orderType))
            businessType = "262";
        else if("13".equalsIgnoreCase(orderType))
            businessType = "263";
        else if("14".equalsIgnoreCase(orderType))
            businessType = "264";
        else if("15".equalsIgnoreCase(orderType))
            businessType = "265";
        else if("16".equalsIgnoreCase(orderType))
            businessType = "266";
        else if("17".equalsIgnoreCase(orderType))
            businessType = "267";
        else if("18".equalsIgnoreCase(orderType))
            businessType = "268";

        return businessType;
    }
}
