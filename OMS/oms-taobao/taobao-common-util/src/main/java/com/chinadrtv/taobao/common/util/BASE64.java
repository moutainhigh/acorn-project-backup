package com.chinadrtv.taobao.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-6-5
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
public class BASE64 {
    private static String key = "eis324EWx786";

    private final static String DES = "DES/CBC/PKCS5Padding"; //DES/ECB/PKCS5Padding
    private final static String Encoding = "UTF-8";

    public static String EncryptDES(String encryptString) {
        return BASE64.EncryptDES(encryptString, key);
    }

    public static String EncryptDES(String encryptString, String encryptKey) {
        String ret = "";
        try {

            byte[] key = encryptKey.getBytes(Encoding);
            DESKeySpec dks = new DESKeySpec(key);

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance(DES);

            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);

            byte[] src = encryptString.getBytes(Encoding);
            src = cipher.doFinal(src);
            BASE64Encoder enc = new BASE64Encoder();
            ret = enc.encode(src);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ret = encryptString;
        }
        return ret;
    }

    public static String DecryptDES(String decryptString) {
        return BASE64.DecryptDES(decryptString, key);
    }

    public static String DecryptDES(String decryptString, String decryptKey) {
        String ret = "";
        try {

            byte[] key = decryptKey.getBytes(Encoding);
            DESKeySpec dks = new DESKeySpec(key);

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance(DES);

            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.DECRYPT_MODE, securekey, iv);

            BASE64Decoder dnc = new BASE64Decoder();
            byte[] src = dnc.decodeBuffer(decryptString);
            src = cipher.doFinal(src);
            ret = new String(src, 0, src.length, Encoding);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());

            ret = decryptString;
        }
        return ret;
    }

    public static void main(String[] args) throws Exception {
        String result = BASE64.EncryptDES("KJLK,123123,asdsd,2123,asdasd,123123,sdfs23123,asdsd,2123,asdasd,123123,sdfsdf");
        System.out.println(result);
        String result1 = BASE64.DecryptDES(result);
        System.out.println(result1);

    }
}