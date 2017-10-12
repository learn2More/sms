package com.ppdai.ac.sms.contract.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/5/12.
 * 部门Vo
 */
public class DepartmentVo {
    @JsonProperty("departmentId")
    private int departmentId;

    @JsonProperty("departmentName")
    private String departmentName;

    @JsonProperty("parentId")
    private int parentId;

    @JsonProperty("level")
    private int level;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("ownerEmail")
    private String ownerEmail;

    @JsonProperty("insertTime")
    private Timestamp insertTime;

    @JsonProperty("updateTime")
    private Timestamp updateTime;

    @ApiModelProperty(value = "部门id ")
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    @ApiModelProperty(value = "部门名称")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    @ApiModelProperty(value = "创建时间")
    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }
    @ApiModelProperty(value = "部门级别")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    @ApiModelProperty(value = "审批人")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    @ApiModelProperty(value = "审批人邮箱 ")
    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @ApiModelProperty(value = "上级部门id ")
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @ApiModelProperty(value = "更新时间")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
