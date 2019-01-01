package com.bawei.shaopenglai.callback;

/**
 * @author Peng
 */
public interface MyCallBack<T> {
    void onSuccess(T data);
    void onFail(String error);
}
