package com.bawei.shaopenglai.ui.mineui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.bawei.shaopenglai.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineMoneyActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_money);
        ButterKnife.bind(this);


    }
}
