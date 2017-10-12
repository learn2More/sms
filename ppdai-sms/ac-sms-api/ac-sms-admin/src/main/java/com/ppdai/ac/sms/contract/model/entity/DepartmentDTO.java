package com.ppdai.ac.sms.contract.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 */
public class DepartmentDTO {
    @JsonProperty("departmentId")
    private int departmentId;

    @JsonProperty("departmentName")
    private String departmentName;

    @JsonProperty("parentId")
    private int parentId;

    @JsonProperty("level")
    private int level;

    @JsonProperty("ownerName")
    private String ownerName;

    @JsonProperty("ownerEmail")
    private String ownerEmail;

    @JsonProperty("insertTime")
    private Timestamp insertTime;

    @JsonProperty("updateTime")
    private Timestamp updateTime;

    @JsonProperty("ownerJobId")
    private String  ownerJobId;


    @ApiModelProperty(value = "审批人工号")
    public String getOwnerJobId() {
        return ownerJobId;
    }

    public void setOwnerJobId(String ownerJobId) {
        this.ownerJobId = ownerJobId;
    }

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @ApiModelProperty(value = "部门编号")
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
    @ApiModelProperty(value = "部门级别")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    @ApiModelProperty(value = "负责人")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    @ApiModelProperty(value = "负责人邮箱")
    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
    @ApiModelProperty(value = "上一级部门编号")

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
