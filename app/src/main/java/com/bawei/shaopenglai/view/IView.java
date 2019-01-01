package com.bawei.shaopenglai.view;

/**
 * @author Peng
 */
public interface IView<T> {
    void getDataSuccess(T data);
    void getDataFail(String error);
}
