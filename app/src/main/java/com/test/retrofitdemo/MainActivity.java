package com.test.retrofitdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RetroiftInterface retroiftInterface;
    private Button retrofitGetNoParme;
    private Button retrofitGetHasParme;
    private Button retrofitPostFrom;
    private Button retrofitPostJSON;
    private Button retrofitDownLoad;
    private Button retrofitUpLoad;
    private static final String TAG = "RetrofitDemoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retroiftInterface =RetrofitHleperUtil. getRetorfitHleperInstance().getRetrofit();
        initView();
    }

    private void initView() {
        retrofitGetNoParme = findViewById(R.id.retrofitGetNoParme);
        retrofitGetHasParme = findViewById(R.id.retrofitGetHasParme);
        retrofitPostFrom = findViewById(R.id.retrofitPostFrom);
        retrofitPostJSON = findViewById(R.id.retrofitPostJSON);
        retrofitDownLoad = findViewById(R.id.retrofitDownLoad);
        retrofitUpLoad=findViewById(R.id.retrofitUpLoad);
        onClick();
    }

    private void onClick() {
        retrofitGetNoParme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNoParam();
            }
        });

        retrofitGetHasParme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHasParam();
            }
        });

        retrofitPostFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFrom();
            }
        });

        retrofitPostJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJson();
            }
        });

        retrofitDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDownLoad();
            }
        });

        retrofitUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadFile();
            }
        });
    }


    /**
     * 无参数get请求
     */
    private void getNoParam() {
        retroiftInterface.getNoParam().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onNext: " + response.code());
                    Log.d(TAG, "onNext: " + response.message());
                    Log.d(TAG, "onNext: " + response.body().string());
                } catch (IOException e) {
                    Log.e(TAG, "onNext: "+e.toString() );
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onNext: "+t.toString() );
            }
        });
    }

    /**
     * 有参数请求
     */
    private void getHasParam() {
        retroiftInterface.getHasParam(1, 294).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onNext: " + response.code());
                    Log.d(TAG, "onNext: " + response.message());
                    Log.d(TAG, "onNext: " + response.body().string());
                } catch (IOException e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 普通表单post请求
     */
    private void postFrom() {
        retroiftInterface.postFrom("victory", "123456").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onNext: " + response.code());
                    Log.d(TAG, "onNext: " + response.message());
                    Log.d(TAG, "onNext: " + response.body().string());
                } catch (IOException e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * post提交Json
     */
    private void postJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MethodName", "MachineInfo");
            jsonObject.put("UserAccount", "你的账号");
            jsonObject.put("PhoneModel", android.os.Build.MODEL);
            jsonObject.put("Manufacturer", android.os.Build.MANUFACTURER);
            jsonObject.put("System", "Android");
            jsonObject.put("SystemVersion", android.os.Build.VERSION.SDK);
            jsonObject.put("AppVersion", "8.1");
        } catch (Exception e) {

        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        retroiftInterface.postJSON(requestBody)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.d(TAG, "onNext: " + response.code());
                            Log.d(TAG, "onNext: " + response.message());
                            Log.d(TAG, "onNext: " + response.body().string());
                        } catch (IOException e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    private void postDownLoad() {
        retroiftInterface.getFileDwon("")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d(TAG, "onNext: " + response.code());
                        Log.d(TAG, "onNext: " + response.message());
                        Log.d(TAG, "onNext: " + response.body().contentLength());
                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/123456.apk");
                        try {
                            //常用方法
//                            BufferedSource source = response.body().source();
//                            BufferedSink sink = Okio.buffer(Okio.sink(file));
//                            sink.writeAll(source);
//                            sink.flush();
//                            带进度方法
                            OutputStream os =new FileOutputStream(file);
                            InputStream is=response.body().byteStream();
                            int len;
                            long currentLength=0;
                            double con=response.body().contentLength();
                            byte [] buff = new byte[1024];
                            while((len=is.read(buff))!=-1){
                                currentLength+=len;
                                Log.d(TAG, "onNext:len " +len);
                                Log.d(TAG, "onNext:cur " +currentLength);
                                Log.d(TAG, "onNext:con " +con);
                                Log.d(TAG, "onNext:pro " +String.format("%.2f",currentLength/con));
                                os.write(buff,0,len);
                            }
                            os.close();
                            is.close();
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    private void upLoadFile(){
        MediaType mediaType = MediaType.parse("file/*");
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/aa.jpg");
        RequestBody requestBody = RequestBody.create(mediaType, file);
        retroiftInterface.postUpLoad("https://www.baidu.com",requestBody)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d(TAG, "onNext: " + response.code());
                        Log.d(TAG, "onNext: " + response.message());
//                        Log.d(TAG, "onNext: " + response.body().contentLength());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
