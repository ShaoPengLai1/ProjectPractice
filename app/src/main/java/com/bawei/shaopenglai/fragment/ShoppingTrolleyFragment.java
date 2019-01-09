package com.bawei.shaopenglai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.MyShoppingAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.GoodsBean;
import com.bawei.shaopenglai.bean.ShowShoppingBean;
import com.bawei.shaopenglai.bean.ShowShoppingBean.ResuleBean;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.ui.shopping.TJDDActivity;
import com.bawei.shaopenglai.view.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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
    private IPresenterImpl iPresenter;
    private MyShoppingAdapter shoppingAdapter;
    private ShowShoppingBean showShoppingBean;


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
        iPresenter=new IPresenterImpl(this);
        shoppingAdapter=new MyShoppingAdapter(getActivity());
        recyclerView.setAdapter(shoppingAdapter);
        //1为不选中
        quanxuan.setTag(1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));


        shoppingAdapter.setUpdateListener(new MyShoppingAdapter.UpdateListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                //设置ui的改变
                totalPrice.setText("合计 :¥"+total+"元");//总价
                if (allCheck){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_selected);
                }else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_unselected);
                }
                quanxuan.setChecked(allCheck);
            }
        });
        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用adapter里面的方法 ,,把当前quanxuan状态传递过去
                int tag = (int) quanxuan.getTag();
                if(tag==1){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_selected);
                }else{
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_unselected);
                }
                shoppingAdapter.quanXuan(quanxuan.isChecked());

            }
        });
        loadData();
    }

    /*private void totalMoney() {
        int money=0;
        for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
            if (showShoppingBean.getResult().get(i).isItem_check()){
                money+=showShoppingBean.getResult().get(i).getPrice()*showShoppingBean.getResult().get(i).getCount();
            }

        }
        totalPrice.setText("￥"+money+"");
    }*/

    private void loadData() {
        iPresenter.startRequestGet(Apis.URL_FIND_SHOPPING_CART_GET,null,ShowShoppingBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof ShowShoppingBean){
            ShowShoppingBean bean= (ShowShoppingBean) data;
            if (bean==null||!bean.isSuceess()){
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_LONG).show();
            }else {
                shoppingAdapter.setList(bean.getResult());
            }
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(),"111",Toast.LENGTH_LONG).show();
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


    @OnClick({R.id.qjs})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.qjs:
                Intent intent = new Intent(getActivity(), TJDDActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }
}
