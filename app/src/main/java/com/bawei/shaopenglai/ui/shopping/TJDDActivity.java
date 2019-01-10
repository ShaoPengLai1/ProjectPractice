package com.bawei.shaopenglai.ui.shopping;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bawei.shaopenglai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TJDDActivity extends AppCompatActivity {

    @BindView(R.id.addAddress)
    Button addAddress;
    @BindView(R.id.recycle_top)
    RecyclerView recycleTop;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjdd);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.addAddress, R.id.tjdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addAddress:
                recycleTop.setVisibility(View.VISIBLE);
                
                break;
            case R.id.tjdd:
                break;
        }
    }
}
