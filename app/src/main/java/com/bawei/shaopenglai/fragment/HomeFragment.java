package com.bawei.shaopenglai.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.home.BottomHomeAdapter;
import com.bawei.shaopenglai.adapter.home.ByIdAdapter;
import com.bawei.shaopenglai.adapter.home.ByNameAdapter;
import com.bawei.shaopenglai.adapter.home.HotAdapter;
import com.bawei.shaopenglai.adapter.home.MoAdapter;
import com.bawei.shaopenglai.adapter.home.PinAdapter;
import com.bawei.shaopenglai.adapter.home.TopHomeAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.home.BottomTasBean;
import com.bawei.shaopenglai.bean.home.ByIdBean;
import com.bawei.shaopenglai.bean.home.ByName;
import com.bawei.shaopenglai.bean.shopping.GoodsBean;
import com.bawei.shaopenglai.bean.home.HomeBean;
import com.bawei.shaopenglai.bean.home.TopLasBean;
import com.bawei.shaopenglai.bean.home.XBannerBeans;
import com.bawei.shaopenglai.custom.AppinfoiItemDecoration;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.fragment.lb.SecondActivity;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.ui.GoodsActivity;
import com.bawei.shaopenglai.view.IView;
import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Peng 首页页面
 */
public class HomeFragment extends Fragment implements IView {

    @BindView(R.id.home_icon)
    ImageView homeIcon;
    @BindView(R.id.home_ed)
    EditText homeEd;
    @BindView(R.id.home_search)
    ImageView homeSearch;
    @BindView(R.id.home_tv)
    TextView homeTv;
    Unbinder unbinder;
    @BindView(R.id.xbanner_home)
    XBanner xbannerHome;
    @BindView(R.id.con_tv)
    TextView conTv;
    @BindView(R.id.recy_re)
    RecyclerView recyRe;
    @BindView(R.id.con_tv2)
    TextView conTv2;
    @BindView(R.id.recy_mo)
    RecyclerView recyMo;
    @BindView(R.id.con_tv3)
    TextView conTv3;
    @BindView(R.id.recy_pin)
    RecyclerView recyPin;
    @BindView(R.id.byName)
    RecyclerView byName;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.res)
    TextView res;
    @BindView(R.id.mos)
    TextView mos;
    @BindView(R.id.pins)
    TextView pins;
    @BindView(R.id.point_re)
    ImageView pointRe;
    @BindView(R.id.point_mo)
    ImageView pointMo;
    @BindView(R.id.point_pin)
    ImageView pointPin;
    private PopupWindow popupWindow;
    private TopHomeAdapter adapter;
    private IPresenterImpl iPresenter;
    private BottomHomeAdapter botmViewHolder;
    private TopLasBean topLasBean;
    private List<String> mImgUrl;
    private HotAdapter hotAdapter;
    private MoAdapter moAdapter;
    private PinAdapter pinAdapter;
    private ByNameAdapter byNameAdapter;
    private BottomTasBean bottomTasBean;
    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        iPresenter = new IPresenterImpl(this);
        mImgUrl = new ArrayList<>();
        iPresenter.startRequestGet(Apis.URL_BANNER_SHOW_GET, null, XBannerBeans.class);
        initRecy();
        return view;

    }

    private void initRecy() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        byName.setLayoutManager(manager);
        byNameAdapter = new ByNameAdapter(getActivity());
        byName.setAdapter(byNameAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyRe.setLayoutManager(gridLayoutManager);
        hotAdapter = new HotAdapter(getActivity());
        recyRe.setAdapter(hotAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyMo.setLayoutManager(layoutManager);
        moAdapter = new MoAdapter(getActivity());
        recyMo.setAdapter(moAdapter);
        GridLayoutManager gridLayoutManagers = new GridLayoutManager(getActivity(), 2);
        gridLayoutManagers.setOrientation(LinearLayoutManager.VERTICAL);
        recyPin.setLayoutManager(gridLayoutManagers);
        pinAdapter = new PinAdapter(getActivity());
        recyPin.setAdapter(pinAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.home_icon, R.id.home_ed, R.id.home_search, R.id.home_tv,R.id.point_re, R.id.point_mo, R.id.point_pin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_icon:
                initData();
                break;
            case R.id.home_search:
                homeEd.setVisibility(View.VISIBLE);
                homeTv.setVisibility(View.VISIBLE);
                homeSearch.setVisibility(View.INVISIBLE);
                break;
            case R.id.home_tv:
                if (homeEd.getText().toString().equals("")) {
                    homeSearch.setVisibility(View.VISIBLE);
                    homeTv.setVisibility(View.GONE);
                    homeEd.setVisibility(View.INVISIBLE);
                } else {
                    iPresenter.startRequestGet(Apis.URL_FIND_COMMODITY_BYKEYWORD_GET
                                    + "?keyword=" + homeEd.getText().toString() + "&page=" + "1" + "&count=5",
                            null, ByName.class);
                }
                break;
            case R.id.point_re:
                res.setVisibility(View.VISIBLE);
                id="1002";
                getData(id);
                backPage();
                break;
            case R.id.point_mo:
                mos.setVisibility(View.VISIBLE);
                id = "1003";
                getData(id);
                backPage();
                break;
            case R.id.point_pin:
                pins.setVisibility(View.VISIBLE);
                id="1004";
                getData(id);
                backPage();
                break;
            default:
                break;
        }
    }

    private void getData(String id) {
        iPresenter.startRequestGet(Apis.URL_FIND_COMMODITY_LIST_BYLABEL_GET
                        +"?labelId="+id+"&page=1&count=8",null, ByName.class);
    }

    /**
     * 左上角按钮显示数据
     */
    private void initData() {
        View v = View.inflate(getActivity(), R.layout.popup_item_home, null);
        //加载上面的布局的RecyclerView
        RecyclerView topView = v.findViewById(R.id.recycle_top);
        adapter = new TopHomeAdapter(getActivity());
        //一级条目布局管理器
        topView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        topView.setAdapter(adapter);
        //设置条目之间的间距
        AppinfoiItemDecoration decoration = new AppinfoiItemDecoration();
        topView.addItemDecoration(decoration);
        //加载下面的布局的RecyclerView
        RecyclerView bottomView = v.findViewById(R.id.recycle_bottom);
        botmViewHolder = new BottomHomeAdapter(getActivity());
        bottomView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        bottomView.setAdapter(botmViewHolder);
        bottomView.addItemDecoration(decoration);
        //设置popupWindow
        popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置焦点
        popupWindow.setFocusable(true);
        //设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置可触摸
        popupWindow.setTouchable(true);
        //设置位置
        popupWindow.showAsDropDown(v,0,0);
        //popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, -340);
        loadData();

        adapter.result(new TopHomeAdapter.Cicklistener() {
            @Override
            public void setonclicklisener(int index) {
                String id = topLasBean.getResult().get(index).getId();
                iPresenter.startRequestGet(Apis.URL_FIND_SECOND_CATEGORY_GET + id,
                        null, BottomTasBean.class);
                botmViewHolder.result(new TopHomeAdapter.Cicklistener() {
                    @Override
                    public void setonclicklisener(int index) {
                        String id1 = bottomTasBean.getResult().get(index).getId();
                        iPresenter.startRequestGet(Apis.URL_FIND_COMMODITY_BYCATEGORY_GET
                                + "?categoryId=" + id1 + "&page=1&count=10", null, ByIdBean.class);
                    }
                });
            }
        });
        loadData();
    }

    private void loadData() {
        iPresenter.startRequestGet(Apis.URL_FIND_FIRST_CATEGORY_GET, null, TopLasBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        /**
         * 一级列表
         */
        if (data instanceof TopLasBean) {
            topLasBean = (TopLasBean) data;
            if (topLasBean == null || !topLasBean.isSucess()) {
                Toast.makeText(getActivity(), topLasBean.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                adapter.setData(topLasBean.getResult());
            }
            /**
             * 二级列表
             */
        } else if (data instanceof BottomTasBean) {
            bottomTasBean = (BottomTasBean) data;
            if (bottomTasBean == null || !bottomTasBean.isSuccess()) {
                Toast.makeText(getActivity(), bottomTasBean.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                botmViewHolder.setData(bottomTasBean.getResult());
            }
//            botmViewHolder.result(new TopHomeAdapter.Cicklistener() {
//                @Override
//                public void setonclicklisener(int index) {
//                    String id = bottomTasBean.getResult().get(index).getId();
//                    getGoods(Integer.parseInt(id));
//                }
//            });
            /**
             * 轮播图
             */
        } else if (data instanceof XBannerBeans) {
            XBannerBeans xBannerBeans = (XBannerBeans) data;
            if (xBannerBeans == null || !xBannerBeans.isSuccess()) {
                Toast.makeText(getActivity(), xBannerBeans.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                for (int i = 0; i < xBannerBeans.getResult().size(); i++) {
                    mImgUrl.add(xBannerBeans.getResult().get(i).getImageUrl());
                    //加载图片
                    initImageData();
                    iPresenter.startRequestGet(Apis.URL_COMMODITY_LIST_GET, null, HomeBean.class);
                }
            }
        }
        if (data instanceof TopLasBean) {
            TopLasBean topLasBean = (TopLasBean) data;
            adapter.setData(topLasBean.getResult());
        }
        if (data instanceof HomeBean) {
            final HomeBean homeBean = (HomeBean) data;
            hotAdapter.setData(homeBean.getResult().getRxxp().get(0).getCommodityList());
            moAdapter.setData(homeBean.getResult().getMlss().get(0).getCommodityList());
            pinAdapter.setData(homeBean.getResult().getPzsh().get(0).getCommodityList());
            hotAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    int commodityId = homeBean.getResult().getRxxp().get(0).
                            getCommodityList().get(index).getCommodityId();
                    getGoods(commodityId);
                }
            });
            moAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    int commodityId = homeBean.getResult().getMlss().get(0).
                            getCommodityList().get(index).getCommodityId();
                    getGoods(commodityId);
                }
            });
            pinAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    int commodityId = homeBean.getResult().getPzsh().get(0).
                            getCommodityList().get(index).getCommodityId();
                    getGoods(commodityId);
                }
            });


        }
        /**
         * 根据类型商品名称查询
         */
        if (data instanceof ByName) {
            final ByName byNames = (ByName) data;
            scroll.setVisibility(View.GONE);
            byName.setVisibility(View.VISIBLE);
            byNameAdapter.setData(byNames.getResult());
            byNameAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    getGoods(byNames.getResult().get(index).getCommodityId());
                }
            });
            if (id=="1002"){
                res.setVisibility(View.VISIBLE);
            }else if (id=="1003"){
                mos.setVisibility(View.VISIBLE);
            }else if (id=="1004") {
                pins.setVisibility(View.VISIBLE);
            }
            backPage();
        }

        if (data instanceof ByIdBean) {
            final ByIdBean byIdBean = (ByIdBean) data;
            scroll.setVisibility(View.GONE);
            byName.setVisibility(View.VISIBLE);
            ByIdAdapter byIdAdapter = new ByIdAdapter(getActivity());
            byName.setAdapter(byIdAdapter);
            List<ByIdBean.ResultBean> result = byIdBean.getResult();
            byIdAdapter.setData(result);
            byIdAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    getGoods(byIdBean.getResult().get(index).getCommodityId());
                }
            });

        }
        if (data instanceof GoodsBean) {
            EventBus.getDefault().postSticky(new EventBean("goods", data));
            startActivity(new Intent(getActivity(), GoodsActivity.class));
        }
    }

    private void initImageData() {
        xbannerHome.setData(mImgUrl, null);
        xbannerHome.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(mImgUrl.get(position)).into((ImageView) view);
            }
        });
        xbannerHome.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Intent intent=new Intent(getActivity(),SecondActivity.class);
                startActivity(intent);
            }
        });
        xbannerHome.setPageTransformer(Transformer.Default);
        xbannerHome.setPageTransformer(Transformer.Alpha);
        xbannerHome.setPageTransformer(Transformer.ZoomFade);
        xbannerHome.setPageTransformer(Transformer.ZoomCenter);
        xbannerHome.setPageTransformer(Transformer.ZoomStack);
        xbannerHome.setPageTransformer(Transformer.Stack);
        xbannerHome.setPageTransformer(Transformer.Depth);
        xbannerHome.setPageTransformer(Transformer.Zoom);
        xbannerHome.setPageChangeDuration(0);
    }

    /**
     * 失败方法吐司
     * @param error
     */
    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    /**
     * 商品接口请求
     * @param id
     */
    private void getGoods(int id) {
        iPresenter.startRequestGet(Apis.URL_FIND_COMMODITY_DETAILS_BYID_GET
                + "?commodityId=" + id, null, GoodsBean.class);
    }

    //解绑，防止内存泄漏
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

    /**
     * 设置返回的监听
     */
    private long exitTime = 0;

    private void backPage() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                scroll.setVisibility(View.VISIBLE);
                byName.setVisibility(View.GONE);

                if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    scroll.setVisibility(View.VISIBLE);
                    byName.setVisibility(View.GONE);
                    res.setVisibility(View.GONE);
                    mos.setVisibility(View.GONE);
                    pins.setVisibility(View.GONE);
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        exitTime = System.currentTimeMillis();
                    } else {
                        //启动一个意图,回到桌面
                        Intent backHome = new Intent(Intent.ACTION_MAIN);
                        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        backHome.addCategory(Intent.CATEGORY_HOME);
                        startActivity(backHome);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


}
