package com.ppdai.ac.sms.api.provider.techown.configuration;

import feign.MethodMetadata;
import org.springframework.cloud.netflix.feign.AnnotatedParameterProcessor;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.util.Collection;

import static feign.Util.emptyToNull;

/**
 * 自定义 RequestParamParameterProcessor
 * @RequestParam map 型参数不再进行urlEncode
 * author cash
 * create 2017-07-12-19:08
 **/


public class CustomRequestParamParameterProcessor implements AnnotatedParameterProcessor {
    private static final Class<RequestParam> ANNOTATION = RequestParam.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context,
                                   Annotation annotation) {
        RequestParam requestParam = ANNOTATION.cast(annotation);
        String name = requestParam.value();
        if (emptyToNull(name) != null) {
            context.setParameterName(name);

            MethodMetadata data = context.getMethodMetadata();
            Collection<String> query = context.setTemplateParameter(name,
                    data.template().queries().get(name));
            data.template().query(name, query);
        } else {
            // supports `Map` types
            MethodMetadata data = context.getMethodMetadata();
            data.queryMapIndex(context.getParameterIndex());
            //queryMapEncoded 默认值false,是否已经对map型参数进行urlEncode
            data.queryMapEncoded(true);

        }
        return true;
    }
}
