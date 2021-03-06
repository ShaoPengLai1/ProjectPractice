package com.bawei.shaopenglai.network;

import com.bawei.shaopenglai.callback.ICallBack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Peng Retrofit网络请求类http://mobile.bwstudent.com/small/
 */
public class RetrofitManager <T>{

    private final String BASE_URL="http://172.17.8.100/small/";
    private static RetrofitManager mRetrofitManager;
    private final OkHttpClient client;

    public static synchronized RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }
    private BaseApis mBaseApis;

    private RetrofitManager() {
        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(new CustomIntercept());
        builder.retryOnConnectionFailure(true);
        client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        mBaseApis = retrofit.create(BaseApis.class);
    }


    /**
     * 可以这样生成Map<String, RequestBody> requestBodyMap
     * Map<String, String> requestDataMap这里面放置上传数据的键值对。
     */
    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }
    /**
     * get请求
     */
    public void get(String url,HttpListener listener) {

        mBaseApis.get(url)
                //后台执行在哪个线程中
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置我们的rxJava
                .subscribe(getObserver(listener));
    }

    /**
     * 表单post请求
     */
    public void postFormBody(String url, Map<String, RequestBody> map,HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.postFormBody(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    /**
     * 普通post请求
     */
    public void post(String url, Map<String, String> map,HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    /**
     * put 请求
     */
    public void put(String url,Map<String,String> map,HttpListener listener){
        if (map == null){
            map =new HashMap<>();
        }
        mBaseApis.put(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    /**
     * delete请求
     * @param quxiao
     * @param map
     * @param listener
     */
    public void delete(String quxiao, Map<String, String> map, HttpListener listener) {
        if(map==null){
            map=new HashMap<>();
        }
        mBaseApis.delete(quxiao,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    private Observer getObserver(final HttpListener listener) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if (listener != null) {
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }
        };

        return observer;
    }



    private HttpListener listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }

    public interface HttpListener {
        void onSuccess(String data);

        void onFail(String error);
    }
}
