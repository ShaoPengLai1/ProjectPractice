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
import com.bawei.shaopenglai.bean.RegisterBean;
import com.bawei.shaopenglai.bean.circle.CircleBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

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
        persenter.startRequestGet(Apis.URL_FIND_CIRCLE_LIST_GET,null, CircleBean.class);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);
        adapter = new CircleAdapter(getActivity());
        recycle.setAdapter(adapter);
        getData();
        adapter.setGreatClick(new CircleAdapter.GreatClick() {
            @Override
            public void click(int circleId, boolean isGreat) {
                Map<String,String> map=new HashMap<>();
                map.put("circleId",circleId+"");
                if (isGreat){
                    persenter.startRequestPost(Apis.URL_ADD_CIRCLE_GREAT_POST,map,RegisterBean.class);
                }else {
                    persenter.sendMessageDelete(Apis.URL_CANCLE_CIRCLE_GREAT_DELETE,map,RegisterBean.class);
                }
                adapter.notifyDataSetChanged();

            }
        });
        recycle.setPullRefreshEnabled(true);
        recycle.setLoadingMoreEnabled(true);
        recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getData();
            }

            @Override
            public void onLoadMore() {
                getData();
            }
        });

    }

    private void getData() {
        persenter.startRequestGet(Apis.URL_FIND_CIRCLE_LIST_GET+"?page="+page+"&count="+"5",null,CircleBean.class);

    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof CircleBean) {
            CircleBean circleBean = (CircleBean) data;

            if (page==1){
                adapter.setList(circleBean.getResult());
            }else {
                adapter.addList(circleBean.getResult());
            }
            page++;
            recycle.refreshComplete();
            recycle.loadMoreComplete();

        }
        if (data instanceof RegisterBean){
            RegisterBean regBean= (RegisterBean) data;
            Toast.makeText(getActivity(),regBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
