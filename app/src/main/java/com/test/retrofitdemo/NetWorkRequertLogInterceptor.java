package com.test.retrofitdemo;

import android.util.Log;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author： Victory
 * @Time： 2018/12/17
 * @QQ： 949021037
 * @Explain： com.test.networkrequestmodule.httphelper
 */
public class NetWorkRequertLogInterceptor implements Interceptor {

    private static final String TAG = "NetWorkRequertLogInterc";

    @Override
    public Response intercept(Chain chain) {
        /**
         * request 请求体
         * response 返回体
         * mediaType 媒介类型
         * content 返回body
         * stringparam 请求参数
         */
        Request request = null;
        Response response = null;
        MediaType mediaType = null;
        String content = "";
        StringBuffer stringparam;
        try {
            request = chain.request();
            response = chain.proceed(chain.request());

            /**
             * 添加单个头部信息
             *  request.header()
             *  添加多个头部信息
             *   request.headers();
             */
            stringparam = new StringBuffer();
            content = response.body().string();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    stringparam.append(body.encodedName(i) + ":" + body.encodedValue(i) + ",");
                }
            }
            //打印请求地址，请求参数，返回数据
            Log.d(TAG,  "-----" + "\nurl:" + request.url() + "\nparam:" + stringparam + "\nbody:" + content + "\n-----");
        } catch (Exception e) {
            //返回错误信息
            Log.d(TAG,e.toString());
        }
        /**
         * okhttp.body.string只会调用一次，调用一次后会自动close，所以新建了一个
         */
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
