package com.ppdai.ac.sms.api.provider.jslt.protocal.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

@XmlRootElement(name = "returnsms")
public class Delivers {

    private List<DeliverDetail> delivers;

    private List<ErrorDeliverDetail> errorDelivers;

    @XmlElements(value={@XmlElement(name="callbox", type=DeliverDetail.class)})
    public List<DeliverDetail> getDelivers() {
        return delivers;
    }

    public void setDelivers(List<DeliverDetail> delivers) {
        this.delivers = delivers;
    }

    @XmlElements(value={@XmlElement(name="errorstatus", type=ErrorDeliverDetail.class)})
    public List<ErrorDeliverDetail> getErrorDelivers() {
        return errorDelivers;
    }

    public void setErrorDelivers(List<ErrorDeliverDetail> errorDelivers) {
        this.errorDelivers = errorDelivers;
    }
}
