package com.bawei.shaopenglai.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.BottomHomeAdapter;
import com.bawei.shaopenglai.adapter.TopHomeAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.BottomTasBean;
import com.bawei.shaopenglai.bean.TopLasBean;
import com.bawei.shaopenglai.bean.XBannerBeans;
import com.bawei.shaopenglai.custom.AppinfoiItemDecoration;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;
import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

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
    private PopupWindow popupWindow;
    private TopHomeAdapter adapter;
    private IPresenterImpl iPresenter;
    private BottomHomeAdapter botmViewHolder;
    private TopLasBean topLasBean;
    private List<String> mImgUrl;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        iPresenter = new IPresenterImpl(this);
        mImgUrl = new ArrayList<>();
        iPresenter.startRequestGet(Apis.URL_BANNER_SHOW_GET, null, XBannerBeans.class);
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.home_icon, R.id.home_ed, R.id.home_search, R.id.home_tv})
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
                break;
            default:
                break;
        }
    }

    private void initData() {
        View v = View.inflate(getActivity(), R.layout.popup_item_home, null);
        //加载上面的布局的RecyclerView
        RecyclerView topView = v.findViewById(R.id.recycle_top);
        adapter = new TopHomeAdapter(getActivity());
        //一级条目布局管理器
        topView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        topView.setAdapter(adapter);

        //设置条目之间的间距
        AppinfoiItemDecoration decoration = new AppinfoiItemDecoration();
        topView.addItemDecoration(decoration);
        //加载下面的布局的RecyclerView
        RecyclerView bottomView = v.findViewById(R.id.recycle_bottom);
        botmViewHolder = new BottomHomeAdapter(getActivity());
        bottomView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        bottomView.setAdapter(botmViewHolder);
        bottomView.addItemDecoration(decoration);
        //设置popupWindow
        popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置焦点
        popupWindow.setFocusable(true);
        //设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //设置可触摸
        popupWindow.setTouchable(true);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, -340);
        loadData();
        adapter.result(new TopHomeAdapter.Cicklistener() {
            @Override
            public void setonclicklisener(int index) {
                String id = topLasBean.getResult().get(index).getId();
                //Map<String,String> map=new HashMap<>();
                //map.put("firstCategoryId",id);
                iPresenter.startRequestGet(Apis.URL_FIND_SECOND_CATEGORY_GET + id, null, BottomTasBean.class);

            }
        });
        loadData();
    }

    private void loadData() {
        iPresenter.startRequestGet(Apis.URL_FIND_FIRST_CATEGORY_GET, null, TopLasBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof TopLasBean) {
            topLasBean = (TopLasBean) data;
            if (topLasBean == null || !topLasBean.isSucess()) {
                Toast.makeText(getActivity(), topLasBean.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                adapter.setData(topLasBean.getResult());
            }
        } else if (data instanceof BottomTasBean) {
            BottomTasBean bottomTasBean = (BottomTasBean) data;
            if (bottomTasBean == null || !bottomTasBean.isSuccess()) {
                Toast.makeText(getActivity(), bottomTasBean.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                botmViewHolder.setData(bottomTasBean.getResult());
            }
        } else if (data instanceof XBannerBeans) {
            XBannerBeans xBannerBeans = (XBannerBeans) data;
            if (xBannerBeans == null || !xBannerBeans.isSuccess()) {
                Toast.makeText(getActivity(), xBannerBeans.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                for (int i = 0; i < xBannerBeans.getResult().size(); i++) {
                    mImgUrl.add(xBannerBeans.getResult().get(i).getImageUrl());
                    //加载图片
                    initImageData();
                }
            }
        }
    }

    private void initImageData() {
        xbannerHome.setData(mImgUrl,null);
        xbannerHome.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(mImgUrl.get(position)).into((ImageView) view);
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

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    //解绑，防止内存泄漏
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
