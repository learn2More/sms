package com.ppdai.ac.sms.api.provider.baiwu;


import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.Delivers;
import com.ppdai.ac.sms.api.contract.utils.XmlUtil;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.DeliverDetail;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import java.util.List;

/**
 * author cash
 * create 2017-04-26-11:29
 **/

public class XmlUtilsTest {

    @Test
    public void test(){
        //String xml="<?xml version=\"1.0\" encoding=\"gbk\"?><delivers><code>1</code><deliver><corp_id>test</corp_id><mobile>13810000000</mobile><ext>8888</ext><time>2010-07-02 00:00:00</time><content>收到</content></deliver></delivers>";
        String xml="<?xml version=\"1.0\" encoding=\"gbk\"?><delivers><code>1</code></delivers>";
        Delivers delivers= XmlUtil.desrialize(xml,Delivers.class);
        if(null!=delivers){
            System.out.println(delivers.getCode());
            List<DeliverDetail> list=delivers.getDelivers();
            if(CollectionUtils.isNotEmpty(list)){
                for(DeliverDetail d:list){
                    System.out.println(d.toString());
                }
            }

        }
    }



}
