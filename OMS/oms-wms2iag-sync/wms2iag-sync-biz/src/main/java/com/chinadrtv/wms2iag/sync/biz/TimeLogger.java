package com.chinadrtv.wms2iag.sync.biz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-13
 * Time: 下午11:26
 * To change this template use File | Settings | File Templates.
 */
public class TimeLogger {

    @Value("${env_strTimeLogPath}")
    private String strTimeLogPath;//="wms2agentorderstatussync.data";

    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TimeLogger() {
        //strTimeLogPath="wms2agentorderstatussync.data";
    }

    public void setStrTimeLogPath(String str) {
        strTimeLogPath = str;
    }

    public void WriteToFile(Date date) throws Exception {
        FileWriter writer = new FileWriter(strTimeLogPath, false);
        writer.write(simpleDateFormat.format(date));
        writer.close();
    }

    public Date ReadFromFile() {
        File file = new File(strTimeLogPath);
        if (!file.exists()) {
            return null;
        }
        try {
            StringBuilder strBld = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(strTimeLogPath));

            String strTemp = br.readLine();
            while (strTemp != null) {
                strBld.append(strTemp);
                strTemp = br.readLine();
            }
            br.close();

            return simpleDateFormat.parse(strBld.toString());
        } catch (Exception ioExp) {

        }

        return null;
    }
}
