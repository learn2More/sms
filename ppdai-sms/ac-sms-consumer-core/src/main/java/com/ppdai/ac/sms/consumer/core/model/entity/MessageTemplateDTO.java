package com.ppdai.ac.sms.consumer.core.model.entity;

import java.sql.Date;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public class MessageTemplateDTO {
    private int templateId;

    private int businessId;

    private int departmentId;

    private String templateName;

    private String templateAlias;

    private int messageKind;

    private String content;

    private int intervalTime;

    private int maxCount;

    private int totalMaxCount;

    private int filterRule;

    private boolean isActive;

    private Date insertTime;

    private Date updateTime;

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateAlias() {
        return templateAlias;
    }

    public void setTemplateAlias(String templateAlias) {
        this.templateAlias = templateAlias;
    }

    public int getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(int messageKind) {
        this.messageKind = messageKind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFilterRule() {
        return filterRule;
    }

    public void setFilterRule(int filterRule) {
        this.filterRule = filterRule;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getTotalMaxCount() {
        return totalMaxCount;
    }

    public void setTotalMaxCount(int totalMaxCount) {
        this.totalMaxCount = totalMaxCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageTemplateDTO that = (MessageTemplateDTO) o;

        if (templateId != that.templateId) return false;
        if (businessId != that.businessId) return false;
        if (departmentId != that.departmentId) return false;
        if (messageKind != that.messageKind) return false;
        if (filterRule != that.filterRule) return false;
        if (intervalTime != that.intervalTime) return false;
        if (maxCount != that.maxCount) return false;
        if (totalMaxCount != that.totalMaxCount) return false;
        if (isActive != that.isActive) return false;
        if (templateName != null ? !templateName.equals(that.templateName) : that.templateName != null) return false;
        if (templateAlias != null ? !templateAlias.equals(that.templateAlias) : that.templateAlias != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (insertTime != null ? !insertTime.equals(that.insertTime) : that.insertTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = templateId;
        result = 31 * result + businessId;
        result = 31 * result + departmentId;
        result = 31 * result + (templateName != null ? templateName.hashCode() : 0);
        result = 31 * result + (templateAlias != null ? templateAlias.hashCode() : 0);
        result = 31 * result + messageKind;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + intervalTime;
        result = 31 * result + maxCount;
        result = 31 * result + totalMaxCount;
        result = 31 * result + filterRule;
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (insertTime != null ? insertTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageTemplateDTO{" +
                "templateId=" + templateId +
                ", businessId=" + businessId +
                ", departmentId=" + departmentId +
                ", templateName='" + templateName + '\'' +
                ", templateAlias='" + templateAlias + '\'' +
                ", messageKind=" + messageKind +
                ", content='" + content + '\'' +
                ", intervalTime=" + intervalTime +
                ", maxCount=" + maxCount +
                ", totalMaxCount=" + totalMaxCount +
                ", filterRule=" + filterRule +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
