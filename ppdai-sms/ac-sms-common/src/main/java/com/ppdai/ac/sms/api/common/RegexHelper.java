package com.ppdai.ac.sms.api.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public class RegexHelper {
    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        //String regExp = "^((13[0-9])|(15[^4])|(18[0,1,2,3,4,5-9])|(17[0-8])|(14[5,7]))\\d{8}$";
        String regExp = "^(1[3,4,5,7,8])\\d{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     *邮箱regex
     *author cash
     *create 2017/8/1-14:47
    **/
    public static boolean isEmail(String email){
        String regExp="^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
