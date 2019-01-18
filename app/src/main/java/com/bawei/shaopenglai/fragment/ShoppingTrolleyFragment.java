package com.bawei.shaopenglai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.MyShoppingAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.mine.addr.AddAddrBean;
import com.bawei.shaopenglai.bean.shopping.GoodsBean;
import com.bawei.shaopenglai.bean.shopping.ShowShoppingBean;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.ui.shopping.TJDDActivity;
import com.bawei.shaopenglai.view.IView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @BindView(R.id.delAll)
    Button delAll;

    private GoodsBean goodsBean;
    private IPresenterImpl iPresenter;
    private MyShoppingAdapter shoppingAdapter;
    private ShowShoppingBean showShoppingBean;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shaopping_car, container, false);
        unbinder = ButterKnife.bind(this, view);
        iPresenter = new IPresenterImpl(this);
        loadData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shoppingAdapter = new MyShoppingAdapter(getActivity());
        recyclerView.setAdapter(shoppingAdapter);
        showShopping();
        shoppingAdapter.setShopCarListener(new MyShoppingAdapter.ShopCarListener() {
            @Override
            public void callBack(List<ShowShoppingBean.ResuleBean> mlist) {
                showShoppingBean.getResult().clear();
                String s = new Gson().toJson(mlist);
                Map<String, String> map = new HashMap<>();
                map.put("data", s);
                iPresenter.startRequestPut(Apis.URL_SYNC_SHOPPING_CART_PUT, map, AddAddrBean.class);
                showShoppingBean.getResult().addAll(mlist);
                totalMoney();
            }
        });

    }

    private void totalMoney() {
        int money = 0;
        boolean ischeck = true;
        for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
            if (showShoppingBean.getResult().get(i).isItem_check()) {
                money += showShoppingBean.getResult().get(i).getPrice() * showShoppingBean.getResult().get(i).getCount();
            } else {
                ischeck = false;
            }
        }
        totalPrice.setText("￥" + money + "");
        quanxuan.setChecked(ischeck);
        back();
    }

    private void showShopping() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        iPresenter.startRequestGet(Apis.URL_FIND_SHOPPING_CART_GET, null, ShowShoppingBean.class);
    }

    private void back() {
        if (quanxuan.isChecked()) {
            quanxuan.setBackgroundResource(R.mipmap.ic_action_selected);
        } else {
            quanxuan.setBackgroundResource(R.mipmap.ic_action_unselected);
        }
    }

    private void quanxuan() {
        back();
        for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
            showShoppingBean.getResult().get(i).setItem_check(quanxuan.isChecked());
        }
        shoppingAdapter.setList(showShoppingBean.getResult());
    }

    private void loadData() {
        iPresenter.startRequestGet(Apis.URL_FIND_SHOPPING_CART_GET, null, ShowShoppingBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof ShowShoppingBean) {
            showShoppingBean = (ShowShoppingBean) data;
            shoppingAdapter.setList(showShoppingBean.getResult());
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(), "111", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(EventBean evBean) {
        if (evBean.getName().equals("myDialog")) {
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
                quanxuan();
                break;
            case R.id.qjs:
                ShowShoppingBean lists = new ShowShoppingBean();
                for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
                    if (showShoppingBean.getResult().get(i).isItem_check()) {
                        lists.addResult(showShoppingBean.getResult().get(i));
                    }
                }
                EventBus.getDefault().postSticky(new EventBean("list", lists));
                Intent intent = new Intent(getActivity(), TJDDActivity.class);
                startActivity(intent);
                break;
            case R.id.delAll:
                ShowShoppingBean shoplists = new ShowShoppingBean();
                for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
                    if (showShoppingBean.getResult().get(i).isItem_check()) {
                        shoppingAdapter.notifyDataSetChanged();
                    }
                }

            default:
                break;
        }
    }

    private void delAll() {
        back();
        for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
            showShoppingBean.getResult().get(i).setItem_check(quanxuan.isChecked());
        }
    }
}
