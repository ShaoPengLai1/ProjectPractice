package com.bawei.shaopenglai.ui.mineui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Peng
 * @time 2018/12/30 8:14
 */

public class PersonalInformationActivity extends AppCompatActivity {


    @BindView(R.id.icon_view)
    View iconView;
    @BindView(R.id.myName)
    TextView myName;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.viewTwo)
    View viewTwo;
    @BindView(R.id.myPassword)
    TextView myPassword;
    @BindView(R.id.viewThree)
    View viewThree;
    @BindView(R.id.userPicture)
    SimpleDraweeView userPicture;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        ButterKnife.bind(this);
        initView();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        String headIcon = intent.getStringExtra("headIcon");
        Uri uri = Uri.parse(headIcon);
        userPicture.setImageURI(uri);
        nickname.setText(Name);
    }
}
