package com.chinadrtv.util;


public enum ConfFileEnum {

    Test("test", "test.properties"), 
    Dev("dev", "dev.properties"), 
    Prod("prod", "prod.properties"),
    Pre("pre", "pre.properties");

    private String confName;
    private String fileName;

    private ConfFileEnum(String confName, String fileName) {
        this.fileName = fileName;
        this.confName = confName;
    }

    public String getConfName() {
        return confName;
    }

    public String getFileName() {
        return fileName;
    }

    public static String getFileName(String confName) {
        ConfFileEnum[] enums = ConfFileEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getConfName().equals(confName)) {
                return enums[i].getFileName();
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        String isDev = "${test}";
        System.out.println(ConfFileEnum.getFileName(isDev));
    }

}
