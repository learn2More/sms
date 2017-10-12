package com.ppdai.ac.sms.api.provider.techown.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 *author cash
 *create 2017/7/11-11:19
**/


public class TechownOkHttpInterceptor implements Interceptor {

    private final static Logger logger = LoggerFactory.getLogger(TechownOkHttpInterceptor.class);


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logger.info("-------------xx请求路径:"+request.url().toString());
        Response response=chain.proceed(request);
        return response;
    }
}
