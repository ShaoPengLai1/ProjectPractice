package com.bawei.shaopenglai.ui.mineui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.mine.MineFootAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.mine.addr.MineFootBean;
import com.bawei.shaopenglai.bean.shopping.GoodsBean;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.ui.GoodsActivity;
import com.bawei.shaopenglai.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFootActivity extends AppCompatActivity implements IView {

    @BindView(R.id.xrecycle)
    XRecyclerView xrecycle;

    private MineFootAdapter footAdapter;
    private IPresenterImpl iPresenter;
    private int mPage;
    private MineFootBean footBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_foot);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mPage = 1;
        iPresenter = new IPresenterImpl(this);
        footAdapter = new MineFootAdapter(this);
        xrecycle.setLayoutManager(new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL));
        xrecycle.setAdapter(footAdapter);
        xrecycle.setLoadingMoreEnabled(true);
        xrecycle.setPullRefreshEnabled(true);
        xrecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        loadData();
        footAdapter.result(new MineFootAdapter.Cicklistener() {
            @Override
            public void setonclicklisener(int index) {
                int commodityId = footBean.getResult().get(index).getCommodityId();
                getGoods(commodityId);
            }
        });
    }

    //
    private void loadData() {
        iPresenter.startRequestGet(Apis.URL_BROWSE_LIST_GET + "?page=" + mPage + "&count=10",
                null, MineFootBean.class);
    }
    private void getGoods(int id) {
        iPresenter.startRequestGet(Apis.URL_FIND_COMMODITY_DETAILS_BYID_GET
                + "?commodityId=" + id, null, GoodsBean.class);
    }
    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof MineFootBean) {
            MineFootBean footBean1= (MineFootBean) data;
            if (footBean1 == null || !footBean1.isSuccess()) {
                Toast.makeText(MainFootActivity.this,
                        footBean1.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                if (mPage == 1) {
                    footBean=footBean1;
                    footAdapter.setList(footBean1.getResult());
                } else {
                    footBean.getResult().addAll(footBean1.getResult());
                    footAdapter.addList(footBean1.getResult());
                }
                mPage++;
                xrecycle.refreshComplete();
                xrecycle.loadMoreComplete();
            }
        }
        if (data instanceof GoodsBean) {
            EventBus.getDefault().postSticky(new EventBean("goods", data));
            startActivity(new Intent(this, GoodsActivity.class));
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(MainFootActivity.this, error, Toast.LENGTH_LONG).show();
    }
}
