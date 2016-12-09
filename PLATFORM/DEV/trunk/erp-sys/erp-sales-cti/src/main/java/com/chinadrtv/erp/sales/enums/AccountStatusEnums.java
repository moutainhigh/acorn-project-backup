/**
 * 
 */
package com.chinadrtv.erp.sales.enums;

/**
 * @author dengqianyong
 *
 */
public enum AccountStatusEnums {

	/**
     * 取消
     */
    ACCOUNT_CANCEL("0", "取消"),
    /**
     * 生成（订购）
     */
    ACCOUNT_NEW("1", "订购"),
    /**
     * 放单（分拣）
     */
    ACCOUNT_TRANS("2", "分拣"),
    /**
     * 完成
     */
    ACCOUNT_FINI("5", "完成"),
    /**
     * 拒收
     */
    ACCOUNT_REJECTION("6", "拒收"),
    /**
     * 压单
     */
    ACCOUNT_NOSEND("7", "压单"),
    /**
     * 物流取消（保持原有兼容）
     */
    ACCOUNT_WMSCANCEL("8", "物流取消"),
    /**
     * 装车(已废弃不用)
     */
    ACCOUNT_ACCOUNT("9", "装车"),
    ;
    
    private AccountStatusEnums(String code, String name) {
    	this.code = code;
    	this.name = name;
    }
    
    private String code;
    private String name;

	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
    
    public static AccountStatusEnums getBy(String code) {
    	for (AccountStatusEnums ase : AccountStatusEnums.values()) {
    		if (ase.getCode().equals(code)) return ase;
    	}
    	return null;
    }
}
