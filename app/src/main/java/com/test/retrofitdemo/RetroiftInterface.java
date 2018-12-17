package com.test.retrofitdemo;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @Author： Victory
 * @Time： 2018/12/17
 * @QQ： 949021037
 * @Explain： com.test.androiddemo.activity.util
 */
public interface RetroiftInterface {

    /**
     * 测试无参数get请求
     *
     * @return
     */
    @GET("/wxarticle/chapters/json")
    Call<ResponseBody> getNoParam();

    /**
     * 有参数的Get 拼接
     *
     * @param index
     * @param cid
     * @return
     */
    @GET("/project/list/{index}/json")
    Call<ResponseBody> getHasParam(@Path("index") int index, @Query("cid") int cid);

    /**
     * 测试表单参数post请求
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/user/login/")
    Call<ResponseBody> postFrom(@Field("username") String username, @Field("password") String password);

    /**
     * 测试Json参数post请求
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8", "User-Agent: Retrofit-your-App"})
    @POST("/Api/Tool")
    Call<ResponseBody> postJSON(@Body RequestBody requestBody);

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    @GET
    Call<ResponseBody> getFileDwon(@Url String url);

    /**
     * 上传文件
     * @param url
     * @return
     */
    @POST
    Call<ResponseBody> postUpLoad(@Url String url,@Body RequestBody requestBody);
}
