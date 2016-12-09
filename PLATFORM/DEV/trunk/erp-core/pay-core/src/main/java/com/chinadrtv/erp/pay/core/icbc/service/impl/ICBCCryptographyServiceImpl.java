package com.chinadrtv.erp.pay.core.icbc.service.impl;

import com.chinadrtv.erp.pay.core.icbc.service.ICBCCryptographyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 建行des加密解密服务
 * User: 徐志凯
 * Date: 13-8-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class ICBCCryptographyServiceImpl implements ICBCCryptographyService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ICBCCryptographyServiceImpl.class);

    @Value("${com.chinadrtv.erp.pay.core.icbc.DES}")
    private String algorithm;//DES --"DES/ECB/NoPadding"
    @Value("${com.chinadrtv.erp.pay.core.icbc.DESKey}")
    private String encryptKey;

    /*private Key key;

    @PostConstruct
    private void init()
    {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            generator.init(new SecureRandom(encryptKey.getBytes()));
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            logger.error("Error initializing ICBC des key. Cause: ", e);
            throw new RuntimeException("Error initializing ICBC des key. Cause: " + e);
        }
    }*/

    public byte[] encryptDES(byte[] data) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(encryptKey.getBytes());

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);

            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());
            byteFina = cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("encrypt des error:", e);
            throw new RuntimeException(
                    "encrypt des error. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    public byte[] decryptDES(byte[] data) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(encryptKey.getBytes());

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);

            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE,securekey,new SecureRandom());
            byteFina = cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("decrypt des error:", e);
            throw new RuntimeException(
                    "decrypt des error. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }
}
