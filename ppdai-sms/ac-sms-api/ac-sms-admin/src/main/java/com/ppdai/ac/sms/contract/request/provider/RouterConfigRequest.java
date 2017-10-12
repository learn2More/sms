package com.ppdai.ac.sms.contract.request.provider;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;

/**
 * Created by wangxiaomei02 on 2017/6/9.
 */
public class RouterConfigRequest {



    @JsonProperty("weight")
    private HashMap<Integer,Integer> weight;

    @JsonProperty("line")
    private Integer line;

    @JsonProperty("businesstype")
    private Integer businesstype;

    @JsonProperty("templateTyp")
    private Integer templateTyp;

    @ApiModelProperty(value = "权重集合")
    public HashMap<Integer, Integer> getWeight() {
        return weight;
    }

    public void setWeight(HashMap<Integer, Integer> weight) {
        this.weight = weight;
    }

    @ApiModelProperty(value = "业务类型")
    public Integer getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(Integer businesstype) {
        this.businesstype = businesstype;
    }

    @ApiModelProperty(value = "模板类型")
    public Integer getTemplateTyp() {
        return templateTyp;
    }

    public void setTemplateTyp(Integer templateTyp) {
        this.templateTyp = templateTyp;
    }

    @ApiModelProperty(value = "线路(移动、电信、联通、小灵通)")
    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }
}
