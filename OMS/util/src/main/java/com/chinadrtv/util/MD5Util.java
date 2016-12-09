package com.chinadrtv.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MD5Util {
    // 用来将字节转换成 16 进制表示的字符
    static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getFileMD5(InputStream fis) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[2048];
        int length = -1;
        long s = System.currentTimeMillis();
        try {
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.err.println("last: " + (System.currentTimeMillis() - s));
        byte[] b = md.digest();
        return byteToHexStringSingle(b);
    }

    /**
     * /** 对文件全文生成MD5摘要
     *
     * @param file 要加密的文件
     * @return MD5摘要码
     * @throws Exception
     */
    public static String getFileMD5(File file) {
        try {
            return getFileMD5(new FileInputStream(file));
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    /** */
    /**
     * 对一段String生成MD5加密信息
     *
     * @param message 要加密的String
     * @return 生成的MD5信息
     */
    public static String getMD5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            System.err.println("MD5摘要长度：" + md.getDigestLength());
            byte[] b = md.digest(message.getBytes("utf-8"));
            return byteToHexStringSingle(b);// byteToHexString(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** */
    /**
     * 对一段String生成MD5加密信息
     *
     * @param message 要加密的String
     * @return 生成的MD5信息
     */
    public static String getMD5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes("UTF-16LE"));
            System.err.println("MD5摘要长度：" + md.getDigestLength());
            byte[] b = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                int val = b[i] & 0xff;
                sb.append(Integer.toHexString(val).length() == 1 ? "0" + Integer.toHexString(val) : Integer.toHexString(val));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    /** */
    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     * @param tmp    要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    private static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }

    /**
     * 独立把byte[]数组转换成十六进制字符串表示形式
     *
     * @param byteArray
     * @return
     * @author Bill
     * @create 2010-2-24 下午03:26:53
     */
    public static String byteToHexStringSingle(byte[] byteArray) {
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    public static void main(String arg[]) throws UnsupportedEncodingException {
        String xml = "<root> <UpdateInfo> <LogisticProviderID>95</LogisticProviderID> <BillNo>ATV91041559</BillNo> <OperaType>OP01</OperaType> <OperaDate>2012-03-01T09:00:00</OperaDate> <Station>青岛总站</Station> <Operator>刘伟</Operator> <RefuseReason> </RefuseReason> <ProblemReason> </ProblemReason> <Remark> </Remark> <Update_time>2012-03-01T09:00:00</Update_time> </UpdateInfo> <UpdateInfo> <LogisticProviderID>95</LogisticProviderID> <BillNo>ATV91041559</BillNo> <OperaType>OP01</OperaType> <OperaDate>2012-03-01T09:00:00</OperaDate> <Station>青岛总站</Station> <Operator>刘伟</Operator> <RefuseReason> </RefuseReason> <ProblemReason> </ProblemReason> <Remark> </Remark> <Update_time>2012-03-01T09:00:00</Update_time> </UpdateInfo> <UpdateInfo> <LogisticProviderID>95</LogisticProviderID> <BillNo>ATV91041559</BillNo> <OperaType>OP01</OperaType> <OperaDate>2012-03-01T09:00:00</OperaDate> <Station>青岛总站</Station> <Operator>刘伟</Operator> <RefuseReason> </RefuseReason> <ProblemReason> </ProblemReason> <Remark> </Remark> <Update_time>2012-03-01T09:00:00</Update_time> </UpdateInfo> </root>";
//		 System.out.println(getMD5(xml));
        // System.out.println(byteToHexStringSingle(xml.getBytes("utf-8")));
//		System.out.println("3C757064617465496E666F3E696E666F3C2F757064617465496E666F3E");
        System.out.println(encode2hex(xml));
//		System.out.println(decodeHex(xml));

//		try{
//			String s1 = "2001-9-11T15:30:00";
//			Date d = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")).parse(s1);
//			String s2 = (new SimpleDateFormat("MM/dd/yyyy hh:mm a")).format(d);
//			System.out.println("s2 "+s2);
//			} catch(Exception e) {
//			e.printStackTrace();
//			}

    }

    /*
     * 16进制数字字符集
     */
    private static String hexString = "0123456789ABCDEF";

    /*
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static String encode2hex(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    public static byte[] decodeBytesHex(String bytes)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        return baos.toByteArray();
    }

    /*
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decodeHex(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        String result = "";
        try {
            result = new String(baos.toByteArray(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
