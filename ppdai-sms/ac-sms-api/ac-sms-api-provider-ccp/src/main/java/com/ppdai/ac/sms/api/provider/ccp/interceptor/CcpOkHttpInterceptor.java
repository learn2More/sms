package com.ppdai.ac.sms.api.provider.ccp.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * author cash
 * create 2017-05-15-19:27
 **/

public class CcpOkHttpInterceptor implements Interceptor {
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response=chain.proceed(request);
        return response;
    }
}
