package com.ppdai.ac.sms.contract.utils;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.URL;

/**
 * Created by zqj on 2016/8/23.
 */
public class IpUtil {

    public static InetAddress getInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getHostIp(InetAddress netAddress) {
        if (null == netAddress) {
            return null;
        }
        String ip = netAddress.getHostAddress(); //get the ip address
        return ip;
    }

    public static String getHostName(InetAddress netAddress) {
        if (null == netAddress) {
            return null;
        }
        String name = netAddress.getHostName(); //get the host address
        return name;
    }

    public static String getRequest() {
        String strBackUrl = "";
        HttpServletRequest request = WebContext.getRequest();
        if (request != null) {
            strBackUrl = "http://" + request.getServerName() //服务器地址
                    + request.getContextPath()      //项目名称
                    + request.getServletPath()      //请求页面或其他地址
                    + (request.getQueryString() == null ? "" : "?" + StringEscapeUtils.escapeHtml4(request.getQueryString())); //参数
        }
        return strBackUrl;
    }


    public static String getDirectory() {
        URL url=Thread.currentThread().getContextClassLoader().getResource("");
        String DirectoryUrl="";
        if(null!=url){
            DirectoryUrl=url.toString();
        }
        return DirectoryUrl;
    }
}


