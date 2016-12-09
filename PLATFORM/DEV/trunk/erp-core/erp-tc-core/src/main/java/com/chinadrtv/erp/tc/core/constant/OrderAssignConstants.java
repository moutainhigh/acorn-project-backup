package com.chinadrtv.erp.tc.core.constant;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderAssignConstants {
    /**
     * 未处理标志(初始状态)
     */
    public final static String NOT_HANDLED="0";
    /**
     * 简单分拣处理标志
     */
    public final static String SIMPLE_ASSIGN="1";
    /**
     * 手工指定标志
     */
    public final static String HAND_ASSIGN="2";
    /**
     * 稍后重试标记
     */
    public final static String RETRY_SIMPLE_ASSIGN="3";

    public final static String RETRY_HAND_ASSIGN="4";
}
