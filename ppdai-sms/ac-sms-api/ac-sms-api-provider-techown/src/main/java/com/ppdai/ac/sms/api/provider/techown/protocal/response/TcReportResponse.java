package com.ppdai.ac.sms.api.provider.techown.protocal.response;

/**
 * author cash
 * create 2017-07-13-13:57
 **/

public class TcReportResponse {

    private String op;//报告类型 mo上行  dr回执
    private Integer dc;// 消息类型
    private Integer pi;// protocol_id
    private Integer ec;
    private String sa;//手机号
    private String da;//扩展码
    private String mu;//模块名
    private String sm;//编码之消息内容
    private String id;//消息编号
    private Integer ex;//外部编码
    private String su;//状态说明
    private String sd;//提交时间
    private String dd;//完成时间
    private Integer rp;//错误码
    private Integer bi;//拆分编号
    private Integer di;//群发时号码的位置

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Integer getDc() {
        return dc;
    }

    public void setDc(Integer dc) {
        this.dc = dc;
    }

    public Integer getPi() {
        return pi;
    }

    public void setPi(Integer pi) {
        this.pi = pi;
    }

    public Integer getEc() {
        return ec;
    }

    public void setEc(Integer ec) {
        this.ec = ec;
    }

    public String getSa() {
        return sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public String getMu() {
        return mu;
    }

    public void setMu(String mu) {
        this.mu = mu;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEx() {
        return ex;
    }

    public void setEx(Integer ex) {
        this.ex = ex;
    }

    public String getSu() {
        return su;
    }

    public void setSu(String su) {
        this.su = su;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public Integer getRp() {
        return rp;
    }

    public void setRp(Integer rp) {
        this.rp = rp;
    }

    public Integer getBi() {
        return bi;
    }

    public void setBi(Integer bi) {
        this.bi = bi;
    }

    public Integer getDi() {
        return di;
    }

    public void setDi(Integer di) {
        this.di = di;
    }

    @Override
    public String toString() {
        return "TcReportResponse{" +
                "op='" + op + '\'' +
                ", dc=" + dc +
                ", pi=" + pi +
                ", ec=" + ec +
                ", sa='" + sa + '\'' +
                ", da='" + da + '\'' +
                ", mu='" + mu + '\'' +
                ", sm='" + sm + '\'' +
                ", id='" + id + '\'' +
                ", ex=" + ex +
                ", su='" + su + '\'' +
                ", sd='" + sd + '\'' +
                ", dd='" + dd + '\'' +
                ", rp=" + rp +
                ", bi=" + bi +
                ", di=" + di +
                '}';
    }
}
