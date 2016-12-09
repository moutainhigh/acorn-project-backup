/**
 * 
 */
package com.chinadrtv.erp.sales.core.constant;

/**
 * @author dengqianyong
 *
 */
public enum LogisticsStatusEnums {
	/**
     * 正常
     */
    LOGISTICS_NORMAL("0", "正常"),
    /**
     * 已取消
     */
    LOGISTICS_CANCEL("1", "已取消"),
    /**
     * 已出货
     */
    LOGISTICS_OUT("2", "已出货"),
    /**
     * 拣货确认（已废弃）
     */
    LOGISTICS_TRANS("3", "拣货确认"),
    /**
     * 等待仓库处理
     */
    LOGISTICS_WMS_WAIT("-100", "等待仓库处理"),
    /**
     * 正在加入处理列表
     */
    LOGISTICS_WMS_HANDLING("-200", "正在加入处理列表"),
    /**
     * 分配拣货
     */
    LOGISTICS_WMS_DISTRIBUTION("-300", "分配拣货"),
    /**
     * 拣货完成，等待包装
     */
    LOGISTICS_WMS_PICKING("-400", "拣货完成，等待包装"),
    /**
     * 包装中
     */
    LOGISTICS_WMS_PACKING("-401", "包装中"),
    /**
     * 等待出库扫描
     */
    LOGISTICS_WMS_SCANNING("-700", "等待出库扫描"),
    /**
     * 出库扫描完成，等待快递上交接
     */
    LOGISTICS_WMS_POSTING("-701", "出库扫描完成，等待快递上交接"),
    /**
     * 已出库，快递商接收 （仓库交接给送货公司）
     */
    LOGISTICS_WMS_OUT("-900", "已出库，快递商接收"),
    /**
     * 送货公司签收 （已废弃不用）
     */
    LOGISTICS_COMAPNY_SIGN("4", "送货公司签收"),
    /**
     * 客户签收
     */
    LOGISTICS_CONTACT_SIGN("5", "客户签收"),
    /**
     * 拒收未入库
     */
    LOGISTICS_REJECTION("6", "拒收未入库"),
    /**
     * 问题单
     */
    LOGISTICS_PROBLEM("7", "问题单"),
    /**
     * 结算完成
     */
    LOGISTICS_ACCOUNT("8", "结算完成"),
    /**
     * 拒收入库
     */
    LOGISTICS_REJECTION_STOCKIN("9", "拒收入库"),
    /**
     * 配送异常
     */
    LOGISTICS_SEND_EXCEPTION("10", "配送异常"),
    /**
     * 总站收件
     */
    LOGISTICS_TERMINUS_RECIPIENT("11", "总站收件"),
    /**
     * 派送点收件
     */
    LOGISTICS_DELIVERTYPOINT_RECIPIENT("12", "派送点收件"),
    /**
     * 派件
     */
    LOGISTICS_SEND_PIECES("13", "派件"),
    /**
     * 部分签收
     */
    LOGISTICS_PARTIAL_SIGN("14", "部分签收"),
    /**
     * 丢失
     */
    LOGISTICS_LOST("15", "丢失"),
    /**
     * 退回
     */
    LOGISTICS_RETURN("16", "退回"),
    /**
     * 转站
     */
    LOGISTICS_TRANSFER_STATION("17", "转站"),
    /**
     * 二次配送
     */
    LOGISTICS_SECONDARY_DISTRIBUTION("18", "二次配送"),
    /**
     * 三次配送
     */
    LOGISTICS_THREE_DISTRIBUTION("19", "三次配送"),

    LOGISTICS_URGENT_APPLICATION("20","催送货-待处理"),

    LOGISTICS_URGENT_CHECK("20","催送货-已回复"),

    LOGISTICS_URGENT_END("20","催送货-已处理")
	;
	private String code;
	private String name;
	
	private LogisticsStatusEnums(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static LogisticsStatusEnums getBy(String code) {
		for (LogisticsStatusEnums lse : LogisticsStatusEnums.values()) {
			if (lse.getCode().equals(code)) return lse;
		}
		return null;
	}
}
