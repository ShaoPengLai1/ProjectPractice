package com.bawei.shaopenglai.presenter;

import com.bawei.shaopenglai.bean.mine.IconHear;
import com.bawei.shaopenglai.callback.MyCallBack;
import com.bawei.shaopenglai.model.IModelImpl;
import com.bawei.shaopenglai.view.IView;

import java.util.Map;

/**
 * @author Peng
 */
public class IPresenterImpl implements IPresenter {

    private IModelImpl model;
    private IView iView;
    public IPresenterImpl(IView iView) {
        this.iView = iView;
        model = new IModelImpl();
    }
    @Override
    public void startRequestGet(String url, String params, Class clazz) {
        model.requestDataGet(url, params, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.getDataSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iView.getDataFail(error);
            }
        });
    }

    @Override
    public void startRequestPost(String url, Map<String, String> params, Class clazz) {
        model.requestDataPost(url, params, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.getDataSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iView.getDataFail(error);
            }
        });
    }

    @Override
    public void startRequestPut(String url, Map<String, String> params, Class clazz) {
        model.requestDataPut(url, params, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.getDataSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iView.getDataFail(error);
            }
        });
    }

    @Override
    public void sendMessageDelete(String quxiao, Map<String, String> map, Class clazz) {
        model.requestDelete(quxiao,map,clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.getDataSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iView.getDataFail(error);
            }


        });
    }


    /**
     * 解绑，解决内存泄漏
     */
    public void onDetach() {
        if (model != null) {
            model = null;
        }
        if (iView != null) {
            iView = null;
        }
    }
}
