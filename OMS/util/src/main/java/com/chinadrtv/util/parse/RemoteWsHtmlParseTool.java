
package com.chinadrtv.util.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chinadrtv.util.parse.bean.WsAddressParseBean;


public class RemoteWsHtmlParseTool {
    public static final String SERVICES = "services";
    public static final String PROTOCOL = "http://";

    public static List<WsAddressParseBean> parseRemoteHtml(String hostName, String port,
                                                           String applicationName)
                                                                                  throws IOException {
        String remoteWsHtml = PROTOCOL + hostName + ":" + port + "/" + applicationName + "/"
                              + SERVICES;
        List<WsAddressParseBean> result = new ArrayList<WsAddressParseBean>();

        Document doc = Jsoup.connect(remoteWsHtml).get();
        Elements e = doc.select("a");
        ListIterator<Element> li = e.listIterator();
        //角析wsdlURL与remoteURL
        while (li.hasNext()) {
            WsAddressParseBean wspb = new WsAddressParseBean();
            Element et = li.next();
            String _url = et.attr("href");
            wspb.setWsdlUrl(_url);
            wspb.setRemoteUrl(_url.substring(0, _url.length() - 5));
            //解析包名
            String str = et.text();
            String packageUrl = StringUtils.substringBetween(str, "{", "}");
            String reversePackage = packageUrl.substring(7, packageUrl.length() - 1);
            String[] packageArr = reversePackage.split("\\.");
            ArrayUtils.reverse(packageArr);
            StringBuffer sb = new StringBuffer();
            for (String temp : packageArr) {
                sb.append(temp).append(".");
            }
            String packageName = sb.toString().substring(0, sb.length() - 1);
            wspb.setPackageName(packageName);
            result.add(wspb);
        }

        Elements span = doc.select("span[class$=porttypename]");
        ListIterator<Element> spanList = span.listIterator();
        int i = 0;
        while (spanList.hasNext()) {
            Element et = spanList.next();
            String interfaceClassName = et.text();
            result.get(i).setInterfaceName(interfaceClassName);
            i++;
        }

        return result;

    }

    /**
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // Document doc = Jsoup.connect("http://192.168.0.112:8082/maintenance/services").get();
        List<WsAddressParseBean> list = parseRemoteHtml("192.168.0.112", "8082", "maintenance");
        for (WsAddressParseBean b : list) {
            System.out.println(ToStringBuilder.reflectionToString(b));
        }
    }

}
