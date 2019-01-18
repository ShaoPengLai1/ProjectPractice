package com.bawei.shaopenglai.presenter;

import com.bawei.shaopenglai.callback.MyCallBack;

import java.util.Map;

/**
 * @author Peng
 */
public interface IPresenter {
    void startRequestGet(String url, String params, Class clazz);
    void startRequestPost(String url, Map<String, String> params, Class clazz);
    void startRequestPut(String url, Map<String, String> params, Class clazz);
    void sendMessageDelete(String quxiao, Map<String, String> map, Class clazz);
}
