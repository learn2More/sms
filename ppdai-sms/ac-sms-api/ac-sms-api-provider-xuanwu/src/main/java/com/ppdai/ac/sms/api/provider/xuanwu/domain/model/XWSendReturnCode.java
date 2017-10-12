package com.ppdai.ac.sms.api.provider.xuanwu.domain.model;

/**
 * 玄武短信发送返回枚举
 * author cash
 * create 2017-05-15-11:31
 **/

public enum XWSendReturnCode {

/*    0	成功
    -1	账号无效
    -2	参数无效
    -3	连接不上服务器
    -5	无效的短信数据,号码格式不对
    -6	用户名密码错误
    -7	旧密码不正确
    -9	资金账户不存在
    -11	包号码数量超过最大限制
    -12	余额不足
    -13	账号没有发送权限
    -99	系统内部错误
    -100	其它错误*/


    SUCCESS(0,"成功"),
    INVALID_ACCOUNT(-1,"账号无效"),
    INVALID_PARAMETERS(-2,"参数无效"),
    FAIL_TO_CONNECT_WITH_SERVER(-3,"连接不上服务器"),
    INVALID_SMS_DATA(-5,"无效的短信数据,号码格式不对"),
    BALANCE_IS_NOT_ENOUGH(-12,"余额不足"),
    HAVE_NO_RIGHT_TO_SEND(-13,"账号没有发送权限"),
    SYSTEM_ERROR(-99,"系统内部错误");

    private int code;
    private String describe;

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }


    XWSendReturnCode(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }
}
