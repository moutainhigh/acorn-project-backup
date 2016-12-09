package com.chinadrtv.erp.pay.core.icbc.service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ICBCCryptographyService {
    byte[] encryptDES(byte[] data);
    byte[] decryptDES(byte[] data);
}
