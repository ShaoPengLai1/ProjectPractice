package com.bawei.shaopenglai.ui.shopping;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.PopupAddrAdapter;
import com.bawei.shaopenglai.adapter.order.OrderAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.mine.addr.AddAddrBean;
import com.bawei.shaopenglai.bean.mine.addr.AddressListBean;
import com.bawei.shaopenglai.bean.shopping.ShoppingCarBean;
import com.bawei.shaopenglai.bean.shopping.ShowShoppingBean;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.ui.mineui.CityListActivity;
import com.bawei.shaopenglai.view.IView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TJDDActivity extends AppCompatActivity implements IView {



    @BindView(R.id.addAddress)
    Button addAddress;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.addr)
    TextView addr;
    @BindView(R.id.popup_two)
    LinearLayout popupTwo;
    @BindView(R.id.saddAddress)
    LinearLayout saddAddress;
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
    @BindView(R.id.tjdd)
    TextView tjdd;
    private OrderAdapter adapter;
    private ShowShoppingBean list;
    private IPresenterImpl persenter;
    private PopupWindow popupWindows;
    private TextView add;
    private RecyclerView recyclePopup;
    private PopupAddrAdapter addrAdapter;
    private List<AddressListBean.ResuleBean> result;
    private AddressListBean.ResuleBean resuleBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjdd);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        addrAdapter = new PopupAddrAdapter(this);
        persenter = new IPresenterImpl(this);
        persenter.startRequestGet(Apis.URL_RECEIVE_ADDRESS_GET,null, AddressListBean.class);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        allordersRecycle.setLayoutManager(manager);
        adapter = new OrderAdapter(TJDDActivity.this);
        allordersRecycle.setAdapter(adapter);
        adapter.setShopCarListener(new OrderAdapter.ShopCarListener() {
            @Override
            public void callBack(List<ShowShoppingBean.ResuleBean> mlist) {
                totalMoney();
            }
        });
        popupTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persenter.startRequestGet(Apis.URL_RECEIVE_ADDRESS_GET,null, AddressListBean.class);
                popupWindows.showAsDropDown(view);
            }
        });
        View view = View.inflate(TJDDActivity.this, R.layout.popup_addr, null);
        popupWindows = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindows.setFocusable(true);
        popupWindows.setTouchable(true);
        add = view.findViewById(R.id.addAddress);
        recyclePopup = view.findViewById(R.id.recycle);
        popupWindows.setBackgroundDrawable(new BitmapDrawable());
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclePopup.setLayoutManager(manager1);
        recyclePopup.setAdapter(addrAdapter);
        addrAdapter.setAddrClickListener(new PopupAddrAdapter.AddrClickListener() {
            @Override
            public void callBack(int i) {
                resuleBean = result.get(i);
                Map<String,String> map=new HashMap<>();
                map.put("id",resuleBean.getId());
                persenter.startRequestPost(Apis.URL_SET_DEFAULT_RECEIVE_ADDRESS_POST,map,AddAddrBean.class);
                popupWindows.dismiss();
            }
        });
        popupClick();
    }

    private void popupClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindows.dismiss();
                startActivity(new Intent(TJDDActivity.this, CityListActivity.class));
            }
        });
    }

    private void totalMoney() {
        int money = 0;
        int count = 0;
        for (int i = 0; i < list.getResult().size(); i++) {
            if (list.getResult().get(i).isItem_check()) {
                count += list.getResult().get(i).getCount();
                money += list.getResult().get(i).getPrice() * list.getResult().get(i).getCount();
            }
        }
        allordersPrice.setText("" + money + "");
        allordersCount.setText(count + "");
    }

    @OnClick({R.id.addAddress, R.id.tjdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addAddress:
                persenter.startRequestGet(Apis.URL_RECEIVE_ADDRESS_GET,null, AddressListBean.class);
                popupWindows.showAsDropDown(view);
                break;
            case R.id.tjdd:
                Map<String,String> map=new HashMap<>();
                List<ShoppingCarBean> lists=new ArrayList<>();
                for (int i = 0; i < list.getResult().size(); i++) {
                    if (list.getResult().get(i).isItem_check()){
                        int commodityId = list.getResult().get(i).getCommodityId();
                        int count = list.getResult().get(i).getCount();
                        lists.add(new ShoppingCarBean(commodityId,count));
                    }
                }
                String s = new Gson().toJson(lists);
                map.put("orderInfo",s);
                map.put("totalPrice",allordersPrice.getText().toString());
                map.put("addressId",resuleBean.getId());
                persenter.startRequestPost(Apis.URL_CREATE_ORDER_POST,map,AddAddrBean.class);
                break;
                default:
                    break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(EventBean evBean) {
        if (evBean.getName().equals("list")) {
            list = (ShowShoppingBean) evBean.getClazz();
            adapter.setList(list.getResult());
            totalMoney();
        }
    }

    private void setaddr(AddressListBean.ResuleBean resuleBean) {
        name.setText(resuleBean.getRealName());
        phone.setText(resuleBean.getPhone() + "");
        addr.setText(resuleBean.getAddress());
        saddAddress.setVisibility(View.VISIBLE);
        addAddress.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof AddressListBean) {
            AddressListBean bean = (AddressListBean) data;
            result = bean.getResult();
            for (int i = 0; i < result.size(); i++) {
                String whetherDefault = result.get(i).getWhetherDefault();
                if (Integer.parseInt(whetherDefault)==1){
                    resuleBean = result.get(i);
                    setaddr(resuleBean);
                }
            }
            addrAdapter.setList(result);
        }
        if (data instanceof AddAddrBean){
            AddAddrBean regBean= (AddAddrBean) data;
            if (regBean.getStatus().equals("0000")){
                setaddr(resuleBean);
            }
            Toast.makeText(TJDDActivity.this,regBean.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(TJDDActivity.this,error,Toast.LENGTH_LONG).show();
    }
}
