package com.bawei.shaopenglai.ui.mineui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.adapter.MineFootAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.MineFootBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFootActivity extends AppCompatActivity implements IView {

    @BindView(R.id.xrecycle)
    XRecyclerView xrecycle;
    private MineFootAdapter footAdapter;
    private IPresenterImpl iPresenter;
    private int mPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_foot);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mPage=1;
        iPresenter=new IPresenterImpl(this);
        footAdapter=new MineFootAdapter(this);
        xrecycle.setLayoutManager(new StaggeredGridLayoutManager(2,GridLayoutManager.VERTICAL));
        xrecycle.setAdapter(footAdapter);
        xrecycle.setLoadingMoreEnabled(true);
        xrecycle.setPullRefreshEnabled(true);
        xrecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        loadData();
    }
    //
    private void loadData() {
        iPresenter.startRequestGet(Apis.URL_BROWSE_LIST_GET+"?page="+mPage+"&count=10",
                null,MineFootBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof MineFootBean){
            MineFootBean footBean= (MineFootBean) data;
            if (footBean==null||!footBean.isSuccess()){
                Toast.makeText(MainFootActivity.this,
                        footBean.getMessage(),Toast.LENGTH_LONG).show();
            }else {
                if (mPage==1){
                    footAdapter.setList(footBean.getResult());
                }else {
                    footAdapter.addList(footBean.getResult());
                }
                mPage++;
                xrecycle.refreshComplete();
                xrecycle.loadMoreComplete();
            }
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(MainFootActivity.this,error,Toast.LENGTH_LONG).show();
    }
}
