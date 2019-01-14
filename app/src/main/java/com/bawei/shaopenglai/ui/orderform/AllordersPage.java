package com.bawei.shaopenglai.ui.orderform;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.order.OrderAdapter;
import com.bawei.shaopenglai.bean.order.AlldorInfoByStatusBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AllordersPage extends Fragment implements IView {

    @BindView(R.id.dingdan)
    TextView dingdan;
    @BindView(R.id.dingdanhao)
    TextView dingdanhao;
    @BindView(R.id.allordersDate)
    TextView allordersDate;
    @BindView(R.id.allordersRecycle)
    RecyclerView allordersRecycle;
    @BindView(R.id.relativeTv4)
    TextView relativeTv4;
    @BindView(R.id.allordersCount)
    TextView allordersCount;
    @BindView(R.id.relativeTv5)
    TextView relativeTv5;
    @BindView(R.id.allordersPrice)
    TextView allordersPrice;
    @BindView(R.id.yuan)
    TextView yuan;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private OrderAdapter adapter;
    private SharedPreferences sharedPreferences;

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
        adapter=new OrderAdapter(getActivity());
        allordersRecycle.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
//        iPresenter.startRequestGet(Apis.URL_FIND_ORDER_LIST_BYSTATUS_GET
//                +"?status="+"0"+"&page="+"1"+"&count=5",null,AlldorInfoByStatusBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof AlldorInfoByStatusBean){
            AlldorInfoByStatusBean bean= (AlldorInfoByStatusBean) data;

            if (bean==null){
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_LONG).show();
            }else {
                //adapter.setmList(bean.getResult().getDetailList());
            }
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
    }
}
