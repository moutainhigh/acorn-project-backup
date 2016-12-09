package com.chinadrtv.erp.tc.core.model;

/**
 * 运费返回信息
 * User: liyu
 * Date: 13-1-29
 * Time: 上午10:15
 * 运费返回结果
 * {"code":"000",
 *  "desc":"xxx",
 *  "value":"999"
 * }
 */
public class OrderTransExpenseReturnInfo extends  ReturnInfo {

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    /**
     * 运费值
     */
    private Double expense;
}
