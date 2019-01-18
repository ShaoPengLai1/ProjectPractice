package com.bawei.shaopenglai.ui.orderform;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.order.StringAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.order.AlldorInfoBean;
import com.bawei.shaopenglai.bean.shopping.AddShopping;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Obligation extends Fragment implements IView {
    @BindView(R.id.allordersRecycle)
    RecyclerView allordersRecycle;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private StringAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allorders, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter=new IPresenterImpl(this);
        allordersRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));
        adapter=new StringAdapter(getActivity(),1);
        loadData();
        allordersRecycle.setAdapter(adapter);
        adapter.setShopCarListener(new StringAdapter.ShopCarListener() {
            @Override
            public void callBack(AlldorInfoBean.OrderListBean list) {
                Map<String,String> map=new HashMap<>();
                map.put("orderId",list.getOrderId());
                map.put("payType",1+"");
                iPresenter.startRequestPost(Apis.URL_PAY_POST,map,AddShopping.class);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
    private void loadData() {
        iPresenter.startRequestGet(Apis.URL_FIND_ORDER_LIST_BYSTATUS_GET
                +"?status=1&page=1&count=5",null,AlldorInfoBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof AlldorInfoBean){
            AlldorInfoBean bean= (AlldorInfoBean) data;
            if (bean==null){
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_SHORT).show();
                adapter.setData(bean.getOrderList());
            }
        }
        if (data instanceof AddShopping){
            AddShopping regBean= (AddShopping) data;
            Toast.makeText(getActivity(),regBean.getMessage(),Toast.LENGTH_SHORT).show();
            loadData();
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
    }
}
