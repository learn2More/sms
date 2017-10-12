package com.ppdai.ac.sms.contract.utils;

/**
 * Created by wangxiaomei02 on 2017/6/23.
 */
public class Page {
    private Integer pageNum;
    private Integer pageSize;

    public Page(Integer pageNum,Integer pageSize){
        this.pageNum = pageNum>1?((pageNum-1)*pageSize):0;
        this.pageSize=pageSize;
    }
    public Integer getPageNum() {
        return pageNum ;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum=pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
