package com.ppdai.ac.sms.api.provider.techown.domain.model;

/**
 * techown异常码枚举
 * author cash
 * create 2017-07-12-14:49
 **/

public enum TechownReturnCode {

    UNKNOWN_COMMAND(9002,"未知命令"),
    ERROR_MSG_CONTENT(9012,"短信消息内容错误"),
    ERROR_DESTINATION(9013,"目标地址错误"),
    TOO_LONG_CONTENT(9014,"短信内容太长"),
    ERROR_ROUTE(9015,"路由错误"),
    HAVE_NO_GATEWAY(9016,"没有下发网关"),
    ERROR_FIXED_TIME(9017,"定时时间错误"),
    ERROR_VALID_TIME(9018,"有效时间错误"),
    SPLIT_ERROR(9019,"无法拆分或者拆分错误"),
    NUM_SEGMENT_ERROR(9020,"号码段错误"),
    MSG_NO_ERROR(9021,"消息编号错误"),
    CAN_NOT_SEND_LONG_MSG(9022,"用户不能发长短信"),
    PROTOCOL_ID_ERROR(9023,"ProtocolID 错误"),
    CONSTRUCT_ERROR(9024,"结构错误"),
    ENCODE_ERROR(9025,"短信编码错误"),
    IS_NOT_LONG_MSG(9026,"内容不是长短信"),
    SIGN_ERROR(9027,"签名不对"),
    GATEWAY_NOT_SUPPORT_LONG_MSG(9028,"目标网关不支持长短信"),
    ROUTE_INTERCEPT(9029,"路由拦截"),
    TOO_MANY_MOBILE(9030,"目标地址(手机号)太多"),
    TOO_LITTLE_MOBILE(9031,"目标地址(手机号)太少"),
    TOO_FAST_TO_SEND(9032,"发送速度太快"),
    VALIDATE_ERROR(9101,"验证失败，一般和用户名/密码/IP 地址相关"),
    HAVE_NO_USERNAME(9102,"没有填写用户名"),
    INVALID(9107," 帐号无效,比如过期/禁用"),
    ILLEGAL_CONTENT(9402,"非法内容"),
    BLACK_LIST(9403,"黑名单"),
    CAN_NOT_CONNECT_SERVER(9802,"无法连接到服务器");


    private Integer code;
    private String describe;

    TechownReturnCode(Integer code,String describe) {
        this.describe = describe;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }


    public String getDescribe() {
        return describe;
    }

    public static String getDescribet(Integer code){
        String describe="";
        for(TechownReturnCode e:TechownReturnCode.values()){
            if(e.getCode().equals(code)){
                describe=e.getDescribe();
            }
        }
        return describe;
    }
}
