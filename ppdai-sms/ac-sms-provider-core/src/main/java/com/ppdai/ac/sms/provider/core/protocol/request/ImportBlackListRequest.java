package com.ppdai.ac.sms.provider.core.protocol.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kiekiyang on 2017/5/18.
 */
public class ImportBlackListRequest {
    @JsonProperty("BlackList")
    private List<BlackList> blackLists;

    public List<BlackList> getBlackLists() {
        return blackLists;
    }

    public void setBlackLists(List<BlackList> blackLists) {
        this.blackLists = blackLists;
    }
}
