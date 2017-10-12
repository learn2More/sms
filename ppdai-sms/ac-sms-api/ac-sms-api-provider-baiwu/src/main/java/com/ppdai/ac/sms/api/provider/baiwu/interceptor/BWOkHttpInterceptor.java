package com.ppdai.ac.sms.api.provider.baiwu.interceptor;

import okhttp3.*;
import okio.Buffer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

/**
 * 百悟okhttp拦截器
 * author cash
 * create 2017-05-03-10:48
 **/

public class BWOkHttpInterceptor implements Interceptor {

    private final static Logger logger = LoggerFactory.getLogger(BWOkHttpInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
       /* FormBody.Builder newFormBody = new FormBody.Builder();
        RequestBody requestBody=request.body();

        final Buffer inputBuffer = new Buffer();

        requestBody.writeTo(inputBuffer);
        InputStream is=inputBuffer.inputStream();
        inputBuffer.close();

        String inStr = IOUtils.toString(is,"UTF-8");

        logger.info("bwOkHttpInterceptor, 拦截方法："+request.url()+" 入参 ："+inStr);

        JSONObject jsonObject = new JSONObject(inStr);
        Iterator iterator = jsonObject.keys();
        while(iterator.hasNext()){
            String  key = (String) iterator.next();
            String  value = jsonObject.getString(key);
            //GBK编码
            newFormBody.addEncoded(key, URLEncoder.encode(value,"GBK"));
        }

        Request request2 = new Request.Builder()
                .url(request.url())
                .post(newFormBody.build())
                .build();
        Response response=chain.proceed(request2);*/

        RequestBody requestBody=request.body();

        final Buffer inputBuffer = new Buffer();

        requestBody.writeTo(inputBuffer);
        InputStream is=inputBuffer.inputStream();
        inputBuffer.close();

        String inStr = IOUtils.toString(is,"UTF-8");

        logger.info("bwOkHttpInterceptor, 拦截方法："+request.url()+" 入参 ："+inStr);
        Response response=chain.proceed(request);
        return response;

    }
}
