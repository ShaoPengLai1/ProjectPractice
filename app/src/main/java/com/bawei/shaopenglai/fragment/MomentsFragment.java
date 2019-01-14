package com.bawei.shaopenglai.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.circle.CircleAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.circle.CircleBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Peng 圈子页面
 */
public class MomentsFragment extends Fragment implements IView {
    @BindView(R.id.recycle)
    XRecyclerView recycle;
    Unbinder unbinder;
    private IPresenterImpl persenter;
    int page;
    private CircleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanzi, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        page=1;
        persenter = new IPresenterImpl(this);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);
        adapter = new CircleAdapter(getActivity());
        recycle.setAdapter(adapter);
        persenter.startRequestGet(Apis.URL_FIND_CIRCLE_LIST_GET+"?page="+page+"&count="+"5",null,CircleBean.class);

    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof CircleBean) {
            CircleBean circleBean = (CircleBean) data;
            adapter.setList(circleBean.getResult());
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
    }
}
