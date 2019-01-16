package com.bawei.shaopenglai.ui.mineui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bawei.shaopenglai.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Peng
 * @time 2018/12/30 10:29
 */

public class MyGroupActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);
        ButterKnife.bind(this);

    }
}
