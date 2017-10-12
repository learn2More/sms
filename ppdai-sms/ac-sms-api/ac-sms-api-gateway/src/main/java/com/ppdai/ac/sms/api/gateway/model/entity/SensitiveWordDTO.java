package com.ppdai.ac.sms.api.gateway.model.entity;

import java.sql.Date;

/**
 * Created by kiekiyang on 2017/4/26.
 */
public class SensitiveWordDTO {
    private int wordId;
    private String word;
    private boolean isActive;
    private Date insertTime;
    private Date updateTime;

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensitiveWordDTO that = (SensitiveWordDTO) o;

        if (wordId != that.wordId) return false;
        if (isActive != that.isActive) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;
        if (insertTime != null ? !insertTime.equals(that.insertTime) : that.insertTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = wordId;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (insertTime != null ? insertTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SensitiveWordDTO{" +
                "wordId=" + wordId +
                ", word='" + word + '\'' +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
