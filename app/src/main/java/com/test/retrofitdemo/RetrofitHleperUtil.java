package com.test.retrofitdemo;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * @Author： Victory
 * @Time： 2018/12/17
 * @QQ： 949021037
 * @Explain： com.test.androiddemo.activity.util
 */
public class RetrofitHleperUtil {
    /**
     *
     */
    private static RetrofitHleperUtil retorfitHleperInstance;
    private OkHttpClient okHttpClient;
    private Retrofit mRetrofit;
    private NetWorkRequertLogInterceptor netWorkRequertLogInterceptor;

    /**
     * 写一个单例用于管理RetrofitHleperUtil类，报证只会被创建一次
     *
     * @return
     */
    public static RetrofitHleperUtil getRetorfitHleperInstance() {
        if (retorfitHleperInstance == null) {
            retorfitHleperInstance = new RetrofitHleperUtil();
        }
        return retorfitHleperInstance;
    }

    /**
     * OkHttpClient 配置
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    //设置连接超时时间
                    .connectTimeout(10, TimeUnit.SECONDS)
                    //设置写入超时时间
                    .writeTimeout(10, TimeUnit.SECONDS)
                    //设置读取超时时间
                    .readTimeout(10, TimeUnit.SECONDS)
                    //添加拦截器
                    .addInterceptor(getNetWorkRequertLogInterceptor())
                    .build();
        }
        return okHttpClient;
    }

    /**
     * 获取拦截器
     * @return
     */
    private NetWorkRequertLogInterceptor getNetWorkRequertLogInterceptor() {
        if (netWorkRequertLogInterceptor == null) {
            netWorkRequertLogInterceptor = new NetWorkRequertLogInterceptor();
        }
        return netWorkRequertLogInterceptor;
    }


    /**
     * 配置Retrofit
     *
     * @return
     */
    public RetroiftInterface getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    //设置请求路径，结尾必须以'/'结尾，不然会报错
                    .baseUrl("http://wanandroid.com/")
                    //加入OkHttpClient配置
                    .client(getOkHttpClient())
                    .build();
        }
        return mRetrofit.create(RetroiftInterface.class);
    }

}
