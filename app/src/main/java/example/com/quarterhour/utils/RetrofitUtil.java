package example.com.quarterhour.utils;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import example.com.quarterhour.MyApp;
import example.com.quarterhour.api.APIFunction;
import example.com.quarterhour.api.BaseURL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhangjunyou
 * @date 2018/6/21
 * @description
 * @Copyright 版权所有, 未经授权不得转载其他 .
 */

public class RetrofitUtil {
    private static RetrofitUtil retrofitUtil;
    private static APIFunction apiFunction;

    private RetrofitUtil() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                //添加日志拦截器
                .addInterceptor(new MyInterceptor())
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BaseURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        apiFunction = mRetrofit.create(APIFunction.class);
    }

    public static RetrofitUtil getInstence() {
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null)
                    retrofitUtil = new RetrofitUtil();
            }

        }
        return retrofitUtil;
    }


    public APIFunction API() {
        return apiFunction;
    }

    /**
     * 自定义拦截器.使用的是okhttp来定义的拦截器
     */
    public static class MyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl httpUrl = request
                    .url()
                    .newBuilder()
                    .addQueryParameter("token", SharedPreferencesUtil.getPreference(MyApp.context, "user").getString("token"))
                    .addQueryParameter("source", "android")
                    .addQueryParameter("appVersion", "101")
                    .build();
            Request requestNew = request
                    .newBuilder()
                    .url(httpUrl)
                    .build();
            return chain.proceed(requestNew);
        }
    }

    private static class CommonInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取原先的请求
            Request originalRequest = chain.request();
            //重新构建url
            HttpUrl.Builder builder = originalRequest.url().newBuilder();
            //如果是post请求的话就把参数重新拼接一下，get请求的话就可以直接加入公共参数了
            if (originalRequest.method().equals("POST")) {
                if (originalRequest.body() instanceof FormBody) {
                    FormBody body = (FormBody) originalRequest.body();
                    for (int i = 0; i < body.size(); i++) {
                        Log.i("RequestFatory", body.name(i) + "---" + body.value(i));
                        builder.addQueryParameter(body.name(i), body.value(i));
                    }
                }
            }
            //这里是我的3个公共参数
            builder.addQueryParameter("token", SharedPreferencesUtil.getPreference(MyApp.context, "user").getString("token"))
                    .addQueryParameter("source", "android")
                    .addQueryParameter("appVersion", "101");
            //新的url
            HttpUrl httpUrl = builder.build();
            Request request = originalRequest.newBuilder()
                    .method(originalRequest.method(), originalRequest.body())
                    .url(httpUrl).build();
            return chain.proceed(request);
        }
    }

    /**
     * post请求上传文件
     * 参数1 url
     * 参数2 回调Callback
     */
    public static void upLoadFile(String path, Map<String, Object> params, Callback callback) {

        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设设置类型 以表单的形式提交
        builder.setType(MultipartBody.FORM);

        for (Map.Entry<String, Object> entry : params.entrySet()) {

            Object object = entry.getValue();
            if (!(object instanceof File)) {
                builder.addFormDataPart(entry.getKey(), object.toString());
            } else {
                File file = (File) object;
                builder.addFormDataPart(entry.getKey(), file.getName().trim(),
                        RequestBody.create(MediaType.parse("img/png"), file));

                //img/png -> application/octet-stream
            }
        }

        Request request = new Request.Builder()
                .post(builder.build())
                .url(path)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}
