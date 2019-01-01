package com.bawei.shaopenglai.presenter;

import java.util.Map;

/**
 * @author Peng
 */
public interface IPresenter {
    void startRequestGet(String url, String params, Class clazz);
    void startRequestPost(String url, Map<String, String> params, Class clazz);
}
