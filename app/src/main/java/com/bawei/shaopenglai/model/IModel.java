package com.bawei.shaopenglai.model;

import com.bawei.shaopenglai.callback.MyCallBack;

import java.util.Map;

/**
 * @author Peng
 */
public interface IModel {
    void requestDataGet(String url, String params, Class clazz, MyCallBack myCallBack);
    void requestDataPost(String url, Map<String, String> params, Class clazz, MyCallBack myCallBack);
    void requestDataPut(String url,Map<String,String> params,Class clazz,MyCallBack myCallBack);
    void requestDelete(String quxiao, Map<String, String> map, Class clazz, MyCallBack myCallBack);
}
