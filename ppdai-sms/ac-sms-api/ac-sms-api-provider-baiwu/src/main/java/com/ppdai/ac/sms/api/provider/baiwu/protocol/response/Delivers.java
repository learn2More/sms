package com.ppdai.ac.sms.api.provider.baiwu.protocol.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 百悟上行报告
 * author cash
 * create 2017-04-26-10:33
 **/

@XmlRootElement(name = "delivers")
public class Delivers {

    private String code;

    private List<DeliverDetail> delivers;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElements(value={@XmlElement(name="deliver", type=DeliverDetail.class)})
    public List<DeliverDetail> getDelivers() {
        return delivers;
    }

    public void setDelivers(List<DeliverDetail> delivers) {
        this.delivers = delivers;
    }


}
