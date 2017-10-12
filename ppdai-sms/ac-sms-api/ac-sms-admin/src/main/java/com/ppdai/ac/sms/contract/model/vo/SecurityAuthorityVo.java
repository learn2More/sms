package com.ppdai.ac.sms.contract.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/11.
 */
public class SecurityAuthorityVo {
    @JsonProperty("isAdmin")
    private  boolean isAdmin;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("jobNumber")
    private String jobNumber;
    @JsonProperty("loginName")
    private String loginName;
    @JsonProperty("realName")
    private String realName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("rightIds")
    private List<String> rightIds;

    @ApiModelProperty(value = "是否管理员 ")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @ApiModelProperty(value = "标题")
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    @ApiModelProperty(value = "工号")
    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    @ApiModelProperty(value = "用户名 ")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    @ApiModelProperty(value = "姓名")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    @ApiModelProperty(value = "权限集合 ")
    public List<String> getRightIds() {
        return rightIds;
    }

    public void setRightIds(List<String> rightIds) {
        this.rightIds = rightIds;
    }
    @ApiModelProperty(value = "当前登录用户的ID ")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
