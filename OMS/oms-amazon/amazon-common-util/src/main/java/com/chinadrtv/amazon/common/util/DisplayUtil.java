package com.chinadrtv.amazon.common.util;

public class DisplayUtil {

    /*
     * 银行卡显示函数，显示规则：从后往前数12**56
     */
    public static String display4cardno(String cardno) {
        if (cardno.length() < 6) {
            // 如果输入长度小于6， 则报错
            return null;
        } else {
            StringBuilder toDisplay = new StringBuilder(cardno);
            int length  = cardno.length();
            for (int i = 0; i < length - 6; i++) {
                toDisplay.setCharAt(i, '*');
            }
            toDisplay.setCharAt(length - 6, cardno.charAt(length - 6));
            toDisplay.setCharAt(length - 5, cardno.charAt(length - 5));
            toDisplay.setCharAt(length - 4, '*');
            toDisplay.setCharAt(length - 3, '*');
            toDisplay.setCharAt(length - 2, cardno.charAt(length - 2));
            toDisplay.setCharAt(length - 1, cardno.charAt(length - 1));
            String result = new String(toDisplay);
            return result;
        }
    }

    /*
     * 身份证显示函数，显示规则：前3后2
     */
    public static String display4identityNumber(String identityNumber) {
        if (identityNumber.length() < 6) {
            // 如果输入长度小于6， 则报错
            return null;
        } else {
            StringBuilder toDisplay = new StringBuilder(identityNumber);
            int length  = identityNumber.length();
            
            toDisplay.setCharAt(0, identityNumber.charAt(0));
            toDisplay.setCharAt(1, identityNumber.charAt(1));
            toDisplay.setCharAt(2, identityNumber.charAt(2));
            
            for (int i = 3; i < length - 2; i++) {
                toDisplay.setCharAt(i, '*');
            }
            
            toDisplay.setCharAt(length - 2, identityNumber.charAt(length - 2));
            toDisplay.setCharAt(length - 1, identityNumber.charAt(length - 1));
            String result = new String(toDisplay);
            return result;
        }
    }
    
    public static String display4mobilephone(String mobilephone) {
        String result = mobilephone;
        
        return result;
    }
    
    public static String display4email(String email) {
        String result = email;
        
        return result;
    }
    
    public static String display4name(String name) {
        String result = name;
        
        return result;
    }

}
