package com.bawei.shaopenglai.model;

import com.bawei.shaopenglai.callback.ICallBack;
import com.bawei.shaopenglai.callback.MyCallBack;
import com.bawei.shaopenglai.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

public class IModelImpl implements IModel {
    @Override
    public void requestDataGet(String url, String params, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.onSuccess(o);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    if (myCallBack != null) {
                        myCallBack.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.onFail(error);
                }
            }
        });
    }

    @Override
    public void requestDataPost(String url, Map<String, String> params, final Class clazz, final MyCallBack myCallBack) {

        RetrofitManager.getInstance().post(url, params, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.onSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.onFail(error);
                }
            }
        });
    }

    @Override
    public void requestDataPut(String url, Map<String, String> params, final Class clazz, final MyCallBack myCallBack) {
//        Map<String, RequestBody> bodyMap=RetrofitManager.getInstance().generateRequestBody(params);
        RetrofitManager.getInstance().put(url, params, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (myCallBack!=null){
                        myCallBack.onSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if (myCallBack!=null){
                        myCallBack.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.onFail(error);
                }
            }
        });
    }

    @Override
    public void requestDelete(String quxiao, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().delete(quxiao, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.onSuccess(o);
            }

            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.onFail(error);
                }
            }
        });
    }


}
