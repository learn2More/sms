package com.ppdai.ac.sms.api.contract.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml 工具类
 * author cash
 * create 2017-04-26-10:33
 **/
@SuppressWarnings("all")
public class XmlUtil {

    private final static Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    public static <T> T desrialize(String xml, Class<T> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            logger.error("xml解析错误",e);
            return null;
        }
    }

    public static <T> String serialize(T t) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(t.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(t, writer);
        return writer.toString();
    }
}
