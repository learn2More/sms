package com.ppdai.ac.sms.contract.request.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/16.
 */
public class DepartmentRequest {

    @JsonProperty("departmentName")
    private String  departmentName;

    @JsonProperty("ownerJobId")
    private String  ownerJobId;

    @JsonProperty("ownerName")
    private String ownerName;

    @JsonProperty("ownerEmail")
    private String ownerEmail;

    @JsonProperty("parentId")
    private Integer parentId;

    @JsonProperty("level")
    private Integer level;

    @ApiModelProperty(value = "部门名称")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @ApiModelProperty(value = "审批人工号")
    public String getOwnerJobId() {
        return ownerJobId;
    }

    public void setOwnerJobId(String ownerJobId) {
        this.ownerJobId = ownerJobId;
    }

    @ApiModelProperty(value = "审批人")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @ApiModelProperty(value = "审批人邮箱")
    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @ApiModelProperty(value = "上一级部门编号")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @ApiModelProperty(value = "部门级别")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }








}
