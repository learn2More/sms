package com.ppdai.ac.sms.contract.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by zqj on 2016/8/23.
 */
public class WebContext {
    private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

    /**
     * 放入web请求.
     *
     * @param r
     * @author
     * @date 2012-10-8 下午04:22:36
     */
    public static void setRequest(HttpServletRequest r) {
        request.set(r);
    }

    /**
     * 获取当前request.
     *
     * @return
     * @author
     * @date 2012-10-18 上午10:50:32
     */
    public static HttpServletRequest getRequest() {
        return request.get();
    }

    /**
     * 删除web请求.
     *
     * @param
     * @author
     * @date 2012-10-8 下午04:22:49
     */
    public static void remove() {
        request.remove();
        response.remove();
    }

    /**
     * 得到当前线程response.
     *
     * @return
     * @author
     * @date 2012-10-13 下午06:00:37
     */
    public static HttpServletResponse getResponse() {
        if (null == response.get()) {
            return null;
        }
        return response.get();
    }

    /**
     * 放入当前线程response.
     *
     * @param r
     * @author
     * @date 2012-10-13 下午06:00:40
     */
    public static void setResponse(HttpServletResponse r) {
        r.setContentType("text/html;charset=UTF-8");
        response.set(r);
    }

    /**
     * 放入request.
     *
     * @param key
     * @param value
     * @author
     * @date 2012-10-8 下午04:23:33
     */
    public static void putRequestAttribute(String key, Object value) {
        request.get().setAttribute(key, value);
    }

    /**
     * 删除Session值.
     *
     * @param key
     * @author
     * @date 2012-10-30 下午02:54:43
     */
    public static void removeRequestAttribute(String key) {
        request.get().removeAttribute(key);
    }

    /**
     * 取request值.
     *
     * @param key
     * @return
     * @author
     * @date 2012-10-8 下午04:23:40
     */
    public static Object getRequestAttribute(String key) {
        if (null == request.get()) {
            return null;
        }
        return request.get().getAttribute(key);
    }

    /**
     * 取出请求参数.
     *
     * @param key
     * @return
     * @author
     * @date 2012-10-11 下午05:45:19
     */
    public static Object getRequestParameter(String key) {
        return request.get().getParameter(key);
    }

    /**
     * 往session里面放值.
     *
     * @param key
     * @param value
     * @author
     * @date 2013-4-8 下午3:31:09
     */
    public static void setSessionAttribute(String key, Object value) {
        request.get().getSession().setAttribute(key, value);
    }

    /**
     * 取session值.
     *
     * @param key
     * @author
     * @date 2013-4-8 下午3:31:52
     */
    public static Object getSessionAttribute(String key) {
        return request.get().getSession().getAttribute(key);
    }

    /**
     * 获取导出excel的response.
     *
     * @return
     * @author
     * @date 2013-4-17 下午12:19:23
     */
    public static HttpServletResponse getExcelRespose() {
        return getExcelRespose(UUID.randomUUID().toString());
    }

    /**
     * 传入文件名,获取导出excel的response.
     *
     * @param filename
     *            文件名
     * @return response
     * @author yangl
     * @version 2013-9-26 下午4:13:40
     */
    @SuppressWarnings("deprecation")
    public static HttpServletResponse getExcelRespose(String filename) {
        HttpServletResponse res = response.get();
        res.setContentType("application/x-xls");
        res.setCharacterEncoding("gbk");// excel必须是这样.
        res.setHeader("Content-disposition", "attachment;filename="
                + URLEncoder.encode(filename) + ".xls");
        return res;
    }
}
