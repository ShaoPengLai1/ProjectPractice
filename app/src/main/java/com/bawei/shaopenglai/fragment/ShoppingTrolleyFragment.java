package com.bawei.shaopenglai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.GoodsBean;
import com.bawei.shaopenglai.bean.Loginbean;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.ui.shopping.TJDDActivity;
import com.bawei.shaopenglai.view.IView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Peng 购物车页面
 */
public class ShoppingTrolleyFragment extends Fragment implements IView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.quanxuan)
    CheckBox quanxuan;
    @BindView(R.id.total_price)
    TextView totalPrice;
    Unbinder unbinder;
    @BindView(R.id.qjs)
    TextView qjs;
    private GoodsBean goodsBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shaopping_car, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void getDataSuccess(Object data) {

    }

    @Override
    public void getDataFail(String error) {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(EventBean evBean) {
        if (evBean.getName().equals("myDialog")){
            goodsBean = (GoodsBean) evBean.getClazz();

        }
    }
    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.quanxuan, R.id.qjs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quanxuan:
                break;
            case R.id.qjs:
                Intent intent = new Intent(getActivity(), TJDDActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }
}
